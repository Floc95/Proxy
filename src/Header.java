import java.util.ArrayList;
import java.util.List;


public class Header {
	public String name;
	public List<Instruction> instructions;
	
	public Header()
	{
		instructions = new ArrayList<Instruction>();
	}
	
}
