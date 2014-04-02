import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Instruction {
	
	public String Name;
	public Map<String, String> values;
	
	
	public Instruction(String name, Map<String, String> values) {
		Name = name;
		this.values = values;
	}


	public Instruction() {
		values = new HashMap<String, String>();
	} 
	
	
	
	
}
