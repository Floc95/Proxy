package org.esgi.proxy;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.esgi.proxy.config.Config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FinalProxy {

    Config config;
    ServerSocket socket;
    int port;
    String remoteserverAdress = "127.0.0.1";
    int remoteServerPort = 6666;

    public FinalProxy(File jsonConfigFile) throws Exception {
        if (!jsonConfigFile.exists())
            throw new Exception("File not found");
        System.out.println("Fichier trouvé");
        //config = objectMapper.readValue(jsonConfigFile, Config.class);
        setConfig(jsonConfigFile);

        //System.out.println(config);
        //System.out.println("Proxy port : " + config.port);
        //socket = new ServerSocket(config.port);
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

    private void setConfig(File confile)throws Exception{
        ObjectMapper om = new ObjectMapper();
        System.out.println("SetConfig");

        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(confile);

        while (jParser.nextToken() != JsonToken.END_ARRAY){
            System.out.println(jParser.getCurrentName());
        }
    }

}