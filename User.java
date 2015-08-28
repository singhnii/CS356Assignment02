
//****** Leaf *******

import javax.swing.tree.*;  
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class User extends Observable implements  Visitable {
	
	private String userID;
	private String userMessage;
	private List<String> followers = new ArrayList<String>();
	private List<String> followings = new ArrayList<String>();
	private List<String> newsFeed = new ArrayList<String>();

	public User(String name){
		this.userID = name;
	}
	
	public User(){
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void addFollowers(String s){
		followers.add(s);
	}
	
	public List<String> getFollowers(){
		return followers;
	}
	
	public void add(DefaultMutableTreeNode node, String id){
		this.setUserID(id);
		node.add(new DefaultMutableTreeNode(id));
	}

	public List<String> getFollowings() {
		return followings;
	}

	public void addFollowings(String s) {
		followings.add(s);
	}

	public List<String> getNewsFeed() {
		return newsFeed;
	}

	public void addNewsFeed(String n) {
		newsFeed.add(n);
	}
	
	public void setFeed(String msg){
		this.userMessage = userID + ": " + msg;
		setChanged();
		notifyObservers(userMessage);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
	
