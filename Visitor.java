
public interface Visitor {
	public void visit(AdminControlUI admin);
	public void visit(User user);
	public void visit(UserUI user);
	public void visit(PositiveMessages posMsg);
}
