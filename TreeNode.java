import java.util.List;
import java.util.ArrayList;

public class TreeNode {
	
	private String name;
	private List<User> users;
	
	//Constructor
	public TreeNode(String name){
		this.setName(name);
		users = new ArrayList<User>();
	}
	
	public void add(User user){
		users.add(user);
	}
	
	public List<User> getUsers(){
		return users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
