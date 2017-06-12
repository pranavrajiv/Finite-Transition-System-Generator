package transitionStates;

public class Props 
{
	private int stateType; //Primary is 1, Secondary is 0, connection is 2 
	private String tran; //The Actions which lead to the change in state
	private String name; // name of the state

	
	Props()
	{
		tran = "none";	
		name = "none";
		stateType = -1;
	}
	
	public void setName(String val)
	{
		name = val;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setType(int val)
	{
		stateType = val;
	}
	
	public int getType()
	{
		return stateType;
	}
	
	public void setTran(String val)
	{
		tran = val;
	}
	
	public String getTran()
	{
		return tran;
	}
	
}
