
public class PositiveMessages implements Visitable{

	public void accept(Visitor visitor) {
		visitor.visit(this);		
	}
	
	
	public boolean isPositive (String msg){
		String message = msg.toLowerCase();
		
		if(message.contains("good")){
			return true;
		}
		else if(message.contains("great")){
			return true;
		}
		else if(message.contains("awesome")){
			return true;
		}
		else if(message.contains("excellent")){
			return true;
		}
		else if(message.contains("happy")){
			return true;
		}
		else
			return false;
	}
	
	public void checkMessage(String message){
		if(isPositive(message)){
			accept(TwitterStats.getInstance());
		}
	}

}
