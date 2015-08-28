//******************************************************************
//   Author:   Jasmeet Kaur
//   Class:    CS356 @ CAL POlY POMONA
//   Date:     August 27, 2015
//   Project:  Mini Twitter
//   OO Designs Used: Singleton, Composite, Observer, and Visitor
//******************************************************************

import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


//********** This is a singleton class **********

public class AdminControlUI implements ActionListener, Visitable {
	
	private static AdminControlUI instance = new AdminControlUI();
	
	public static AdminControlUI getInstance(){
		return instance;
	}
	
	//Constructor made private
	private AdminControlUI(){
		initialize();
	}

	private JFrame frame;
	private JTextField userID;
	private JTextField groupID;
	private static Group group = new Group();
	private static  List<String> userList = new ArrayList<String>();
	private  List<String> groupList = new ArrayList<String>();
	private Hashtable<String, User> hash = new Hashtable<String, User>();
	private static Hashtable<String, UserUI> hashUI = new Hashtable<String, UserUI>();
	
    /**
     *  Leaving extra space at the end of the string so that the 
	 * tree view is visible when output is shown.
     */	
	public DefaultMutableTreeNode root = new DefaultMutableTreeNode("Twitter Users                ");
	DefaultTreeModel treeModel = new DefaultTreeModel(root);
	JTree jtree = new JTree(treeModel);
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminControlUI window = new AdminControlUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame("Mini Twitter Admin");
		frame.setBounds(100, 100, 500, 400);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create JPanels for organizing the frame view
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(200,350));
		leftPanel.setOpaque(true);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(260,350));
		rightPanel.setOpaque(true);
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
	    //Create swing components
		userID = new JTextField(10);
        JButton addUser = new JButton("Add User");
        groupID = new JTextField(10);
        JButton addGroup = new JButton("Add Group");
        JButton userView = new JButton("Open User View");
        JButton userTotal = new JButton("Show User Total");
        JButton groupTotal = new JButton("Show Group Total");
        JButton msgTotal = new JButton("Show Message Total");
        JButton posPercentage = new JButton("Show Positive Percentage");
        
        addUser.setActionCommand("Add User");
        addGroup.setActionCommand("Add Group");
        userView.setActionCommand("Open User View");
        userTotal.setActionCommand("Show User Total");
        groupTotal.setActionCommand("Show Group Total");
        msgTotal.setActionCommand("Show Message Total");
        posPercentage.setActionCommand("Show Positive Percentage");
        
        //Action Listeners
        addUser.addActionListener(this);
        userID.addActionListener(this);                 
        groupID.addActionListener(this);
        addGroup.addActionListener(this);        
        userView.addActionListener(this);
        userTotal.addActionListener(this);
        groupTotal.addActionListener(this);
        msgTotal.addActionListener(this);
        posPercentage.addActionListener(this);
        
        
        jtree.setEditable(true);
        //Set the tree selection mode to single selection.
        TreeSelectionModel tsm = jtree.getSelectionModel();
        tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        //Wrap the tree in a scroll pane.
        JScrollPane jscrlp = new JScrollPane(jtree);

        leftPanel.add(jscrlp,BorderLayout.CENTER);        
        rightPanel.add(userID);
        rightPanel.add(addUser);
        rightPanel.add(groupID);
        rightPanel.add(addGroup);
        rightPanel.add(userView);
        rightPanel.add(userTotal);
        rightPanel.add(groupTotal);
        rightPanel.add(msgTotal);
        rightPanel.add(posPercentage);
       
        //Add panels to frame       
        frame.add(leftPanel);
        frame.add(rightPanel);
        
        treeModel.reload(root);        
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Add User")){
	        
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
	        String id = userID.getText();
	        
	        if(id.equals("") || node == null){
	        	JOptionPane.showMessageDialog(frame, "Please enter a User ID or select a parent node!");
	        }
	        
	        else if(doesUserExist(id)){
	        	JOptionPane.showMessageDialog(frame, "This ID already exists");
	        }
	        
	        else if(doesUserExist(node.toString())){
	        	JOptionPane.showMessageDialog(frame, "This is not a Group. Select a GroupID");
	        }
	        
	        else{
	        	if(node != root){    	        	
	        		root = node;	        			  
	        	}
	        	User user = new User();
	        	user.add(root, id);
	        	hash.put(id, user); //Add the user instance created to the hashtable	        	
	        	userList.add(id);
	        	user.accept(TwitterStats.getInstance()); //Calling the visitor class to add this to user count
        		userID.setText(""); //clear the textbox
	        }	        	      	
	     	        
			treeModel.reload(root);
		}
		 
		else if(e.getActionCommand().equals("Add Group")){
			 
			 DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
			 String id = groupID.getText();
			 groupList.add(root.toString());
			 
			 if(id.equals("") || node == null){
				JOptionPane.showMessageDialog(frame, "Please enter a Group ID or select a parent node!");
			 }
			 
			 else if(doesGroupExist(id) || doesUserExist(id)){
		        JOptionPane.showMessageDialog(frame, "This ID already exists");
		     }
			 else if(doesUserExist(node.toString())){
				JOptionPane.showMessageDialog(frame, "You have selected a user. Please select a parent node or the root");
			 }
			 else{
				 if(node != root){
		        	root = node;
		         }
				 
				 DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(id);
				 group.add(treeModel, root, subroot);
				 groupID.setText("");
				 accept(TwitterStats.getInstance()); //Using visitor pattern
				 treeModel.reload(root);
		     }
		 }
		 
		 else if(e.getActionCommand().equals("Open User View")) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
			if(node == null){
				JOptionPane.showMessageDialog(frame, "Please select a User!");
			}
			else if(doesUserExist(node.toString()) == false){
				JOptionPane.showMessageDialog(frame, "This is a GroupID. Please select a User!");
			}
			else{
			UserUI userUI = new UserUI(node.toString(), hash);
			hashUI.put(node.toString(), userUI);
			}			
		 }
		 
		 else if(e.getActionCommand().equals("Show User Total")){
			 int total = TwitterStats.getInstance().getUserTotal();
			 JOptionPane.showMessageDialog(frame, "User Total : " + total);
		 }
		
		 else if(e.getActionCommand().equals("Show Group Total")){
			 int total = TwitterStats.getInstance().getGroupTotal();
			 JOptionPane.showMessageDialog(frame, "Group Total : " + total);
		 }
		
		 else if(e.getActionCommand().equals("Show Message Total")){
			 int total = TwitterStats.getInstance().getMessageTotal();
			 JOptionPane.showMessageDialog(frame, "Message Total : " + total);
		 }
		
		 else if(e.getActionCommand().equals("Show Positive Percentage")){
			 String percentage = TwitterStats.getInstance().getPositivePercentage();
			 JOptionPane.showMessageDialog(frame, "A positive message contains the words: " +
					 							  "\n good, great, excellent, awesome, happy." +
					 							  "\n Positive Percentage : " + percentage + "%.");
		 }
	}
	
	public List<String> getUserList(){
		return userList;
	}
	
	public List<String> getGroupList(){
		return groupList;
	}
	
	public static boolean doesUserExist (String id){
		if(userList.contains(id)){
			return true;
		}
		else 
			return false;
	}
	
	public boolean doesGroupExist (String id){
		if(groupList.contains(id)){
			return true;
		}
		else 
			return false;
	}
	
	public User getHashValue(String key){
		return hash.get(key);
	}
	
	public static UserUI getHashUIValue(String key){
		return hashUI.get(key);
	}
	
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
}	