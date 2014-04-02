import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.esgi.http.interfaces.IHttpHandler;
import org.esgi.http.interfaces.IRequestHttpHandler;
import org.esgi.http.interfaces.IResponseHttpHandler;
import org.esgi.http.interfaces.ISession;
import org.esgi.http.request.RequestBuilder;

public class ProxyHandler implements IHttpHandler {
	myProxy config;
	ResponseBuilder builder =  new ResponseBuilder();
	
	public ProxyHandler() {
		try {
			config = new myProxy(new File("./proxy-config.js"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	int i = 0;
	@Override
	public void execute(IRequestHttpHandler request,
			IResponseHttpHandler response, HashMap<String, ISession> sessions, Socket socket) throws IOException {
		System.out.println("In Proxy Handler");
		
		List<Host> hosts = config.config.hosts;
		for(Host host : hosts)
		{
			if (host.name.equals(request.getHostname()))
			{
				String ipServer = host.workers[i = ++i % host.workers.length];
				int port = 1234;
				String[] ipServerSplit = ipServer.split(":");
				if (ipServerSplit.length > 1)
				{
					ipServer = ipServerSplit[0];
					port = Integer.parseInt(ipServerSplit[1]);
				}
				
		        Socket server = new Socket(ipServer, port);
			    
		        OutputStreamWriter os = new OutputStreamWriter(server.getOutputStream());
		        os.write(request.getMethod() +" "+request.getUrl()+" "+ request.getHttpVersion() + "\r\n");
		        
		        for(String headerName : request.getHeaderNames())
		        	os.write(headerName + ": " + request.getHeader(headerName) + "\r\n");
		        
				// Traitement des headers incoming
				for(Header header : host.headers)
					if (header.name.equals("incoming"))
						for(Instruction instr : header.instructions)
							if (instr.Name.equals("add"))
								for (Entry<String, String> entry : instr.values.entrySet())
									os.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
				
				os.write("\r\n\r\n");
				os.flush();
				
				response.setDontWrite(true);
				try {
					builder.build(server.getInputStream(), response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Map<String, String> headers = new HashMap<String, String>();
				for(Header header : host.headers)
					if (header.name.equals("outcoming"))
						for(Instruction instr : header.instructions)
							if (instr.Name.equals("add"))
								headers = instr.values;
				
				OutputStream output = socket.getOutputStream();
				
		        response.writeHeaders(output);
		        response.writeCustomHeaders(output, headers);
		        response.writeContent(output, server.getInputStream());
				
		        output.flush();
		        //output.close();
		        
				break;
			}
		}
	}
	
	static void copyHeader(InputStream is, OutputStream os) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		OutputStreamWriter osw = new OutputStreamWriter(os); 
		
		String line;
		while((line = br.readLine()) != null)
		{
			if (line.equals(""))
				return;
			osw.write(line + "\r\n");
		}
	}
	
	static void appendHeader(OutputStream os, Host host) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(os); 
		
		for(Header header : host.headers)
			if (header.name.equals("outcoming"))
				for(Instruction instr : header.instructions)
					if (instr.Name.equals("add"))
						for (Entry<String, String> entry : instr.values.entrySet())
							osw.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
		osw.write("\r\n");
	}
	
	static void copyContent(InputStream is, OutputStream os) throws IOException {
		int i =-1;
        while (-1 !=  (i=is.read()))
            os.write(i);
    }
}
