import java.text.DecimalFormat;

//************** Singleton ******************

public class TwitterStats implements Visitor {

private static TwitterStats instance = new TwitterStats();
	
	public static TwitterStats getInstance(){
		return instance;
	}
	
	//constructor made private
	private TwitterStats(){
	}
	
	private int userTotal = 0;
	private int groupTotal = 0;
	private int messageTotal = 0;
	private int positiveMessages = 0;
	private String positivePercentage;
	
	
	//Increment on groupTotal
	public void visit(AdminControlUI admin) {		
		groupTotal++;
	}

	//Increment on userTotal
	public void visit(User user) {
		userTotal++;
	}

	//Increment  on messageTotal
	public void visit(UserUI user) {
		messageTotal++;
	}

	
	//Increment on positiveMessages
	public void visit(PositiveMessages posMsg) {
		positiveMessages++;		
	}
	

	/**
	 * @return the userTotal
	 */
	public int getUserTotal() {
		return userTotal;
	}


	/**
	 * @return the groupTotal
	 */
	public int getGroupTotal() {
		return groupTotal;
	}

	public int getMessageTotal() {
		return messageTotal;
	}

	//method that calcualtes the percentage of postive messages
	public void calculatePositivePercentage(){
		double posMsg = (double)getPositiveMessages();
		double totalMsg = (double)getMessageTotal();
		positivePercentage = new DecimalFormat("##.##").format((posMsg/totalMsg) * 100.0);
	}
	
	public String getPositivePercentage() {
		calculatePositivePercentage();
		return positivePercentage;
	}
	
	/**
	 * @return the positiveMessages
	 */
	public int getPositiveMessages() {
		return positiveMessages;
	}

}
