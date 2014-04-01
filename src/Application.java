import java.io.File;

import org.esgi.proxy.ProxyServer;


public class Application {
	
	public static void main(String[] args) {
		new ProxyServer(new File("./config.js")).run();
	}

}
