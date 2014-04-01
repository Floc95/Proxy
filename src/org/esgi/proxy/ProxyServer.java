package org.esgi.proxy;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.esgi.proxy.config.Config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProxyServer {
	
	Config config;
	
	public ProxyServer(File jsonConfigFile) throws Exception
	{
		if (!jsonConfigFile.exists())
			throw new Exception("File not exists");
		ObjectMapper objectMapper = new ObjectMapper();
		config = objectMapper.readValue(jsonConfigFile, Config.class);
	}
	
	public void run(){
		
	}
}
