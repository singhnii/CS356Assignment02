import javax.swing.tree.*;
//Composite
public class Group{
	
	private String groupID;
	
	public Group() {
	}
		
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	public void add(DefaultTreeModel treeModel, DefaultMutableTreeNode root,DefaultMutableTreeNode subroot){
		treeModel.insertNodeInto(subroot,root, 0);
	}
}
