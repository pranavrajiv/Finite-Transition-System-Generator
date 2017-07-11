package transitionStates;

public class Props 
{
	private int stateType; //Primary is 1, Secondary is 0, connection is 2 
	private String tran; //The Actions which lead to the change in state
	private String name; // name of the state
	private Boolean jump;//stores if it has a jump node

	
	Props()
	{
		tran = null;	
		name = null;
		stateType = -1;
		jump =false;
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

	public void setJump(Boolean val)
	{
		jump = val;
	}
	
	public Boolean getJump()
	{
		return jump;
	}
	
}