import java.util.ArrayList;
import java.util.List;

public class Test{
	
	private String name;
	private List <Boolean> flag = new ArrayList<Boolean>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Boolean> getFlag() {
		return flag;
	}
	public void setFlag(List<Boolean> flag) {
		this.flag = flag;
	}
	public Test(String name, List<Boolean> flag) {
		super();
		this.name = name;
		this.flag = flag;
	}

}


