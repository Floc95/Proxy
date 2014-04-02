import org.esgi.proxy.FinalProxy;

import java.io.File;


public class Application {
	
	public static void main(String[] args) throws Exception {
		new FinalProxy(new File("./config.js")).run();
	}

}
