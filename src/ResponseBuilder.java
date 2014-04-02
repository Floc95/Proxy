import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.esgi.http.interfaces.IResponseHttpHandler;


public class ResponseBuilder {
	
	public IResponseHttpHandler build(InputStream input, IResponseHttpHandler response) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				
		buildHeaders(response, reader);
		
		return response;
	}
	
	private void buildHeaders(IResponseHttpHandler response, BufferedReader reader) throws IOException {
		String line;
		while (null != (line = reader.readLine()) && !line.isEmpty())
		{
			if (line.equals(""))
				return;
			int splitIndex = line.indexOf(':');
			if (-1 != splitIndex)
				response.addHeader(line.substring(0, splitIndex).trim(), line.substring(splitIndex + 1).trim());
		}
	}
}
