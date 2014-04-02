package org.esgi.proxy;

import org.esgi.proxy.interfaces.ISession;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class ProxyServer {

    ServerSocket socket;
    HashMap<String, ISession> sessions = new HashMap<String, ISession>();
    int port;
    String remoteserverAdress = "127.0.0.1";
    int remoteServerPort = 6666;

    public ProxyServer() throws Exception {
        socket = new ServerSocket(1234);
    }


	public void run() throws Exception{
        System.out.println("Serveur lancé");
        Socket browser = socket.accept();
        Socket http= new Socket("127.0.0.1", 1234);
        try {
            copy(browser.getInputStream(),http.getOutputStream());
            copy(http.getInputStream(), browser.getOutputStream());
        }
        catch (Exception e) {
            browser.close();
        }
	}

    static void  copy(InputStream is, OutputStream os) throws IOException {
        int i =-1;
        while (-1 !=  (i=is.read()))
            os.write(i);
    }

}