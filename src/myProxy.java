import java.io.File;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class myProxy {

	public Config config;
	ServerSocket socket;

	public myProxy(File jsonConfigFile) throws Exception {

		// JSON NODE0
		if (!jsonConfigFile.exists())
			throw new Exception("File not exists");

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode mynode = objectMapper.readTree(jsonConfigFile);

		JsonNode hosts = mynode.get("hosts");
		config = new Config();
		for (int i = 0, len = hosts.size(); i < len; i++) {
			JsonNode host = hosts.get(i);
			// HOST
			Host h = new Host();
			h.name = host.get("host").textValue();
			h.lb = host.get("lb").textValue();
			h.workers = new String[host.get("workers").size()];

			// WORKERS
			for (int j = 0; j < host.get("workers").size(); j++) {
				h.workers[j] = host.get("workers").get(j).textValue();
			}
			// HEADER
			// MAP
			Map<String, Integer> map = new HashMap<String, Integer>();

			JsonNode headersNode = host.get("headers");
			
			JsonNode incoming = headersNode.get("incoming");
			h.headers = new ArrayList<Header>();
			Header incomingHeader = new Header();
			incomingHeader.name = "incoming";
			h.headers.add(incomingHeader);
			this.getHeader(incoming, incomingHeader);
			
			JsonNode outgoing = headersNode.get("outgoing");
			Header outgoingHeader = new Header();
			outgoingHeader.name = "outgoing";
			h.headers.add(outgoingHeader);
			this.getHeader(outgoing, outgoingHeader);
			
			config.hosts.add(h);
		}
	}
	
	public void getHeader(JsonNode node, Header header)
	{
		header.instructions = new ArrayList<Instruction>();
		Iterator itr = node.fieldNames();
		while (itr.hasNext()) 
		{
			Object element = itr.next();
			JsonNode instruc = node.get(element.toString());
			
			Instruction in = new Instruction();
			in.Name = element.toString();
			
			for (int k =0; k < instruc.size(); k++) //add (instructions)
			{
				Iterator itr2 = instruc.get(k).fieldNames();						
				while (itr2.hasNext()) 
				{							
					Object element2 = itr2.next();
					String elem = element2.toString();
					String value = instruc.get(k).get(element2.toString()).toString();
					
					in.values.put(elem, value);
				}
			}
			header.instructions.add(in);
		}
	}

}
