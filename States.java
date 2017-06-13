package transitionStates;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import javax.swing.*;

public class States extends JFrame  
{	
	//stores the name of the work-flow
	public static String nameOfFile;
	
	//check if a node is with a particular name is present . If yes returns the position of the node . If not present then returns -1
	public static int isPresent(List<List<Props>> x,String nam)
	{
		for(int j =0;j<x.size();j++)
		{
			if(x.get(j).get(0).getName().equals(nam))
			{
				return j;
			}
		}
		return -1;
		
	}
	
	
	public static void writing(List<List<Props>> x)
	{
		try
		{
			String fileName = JOptionPane.showInputDialog("DOT File created\nWhat would you like to name the file?");
			PrintWriter write = new PrintWriter(System.getProperty("user.home")+"/Desktop/"+fileName+".dot", "UTF-8");
			write.println("digraph ");
			write.println("{");
			write.println("labelloc=\"t\";");
			write.println("label=\""+nameOfFile + " Flow Diagram\"");
			write.println("node [style=filled,fillcolor=\"yellow\"];");
			write.println("\tedge [style=\"solid\",color=\"blue\"];");
			write.println("\tgraph [splines=\"true\", overlap=\"false\"];");
			write.println("\tsubgraph cluster_4");
			write.println("\t{");
			write.println(" \t\tgraph [style=\"invis\"");
			write.println("\t\t];");
			write.println();
			
			write.println("\t\tINTRO");
			write.println("\t\t[");
			write.println("\t\tshape=none;");
			write.println("\t\tfillcolor=\"white\";");
			write.println("\t\tfontcolor=\"brown\";");
			write.println("\t\tlabel=\"Red Elipses are Primary States\nYellow Elipses are Secondary States\";");
			write.println("\t\t];");
			write.println("}");
			
			
			write.println("\tsubgraph cluster_4417343888");
			write.println("\t{");
			write.println(" \t\tgraph [style=\"invis\"");
			write.println("\t\t];");
			write.println();
			
			for(int i =0;i<x.size();i++)
			{
				write.println("\t\t"+x.get(i).get(0).getName().replaceAll(" ", "_"));
				write.println("\t\t[");
				write.println("\t\tshape=\"ellipse\"");
				write.println("\t\tid=\""+x.get(i).get(0).getName()+"\"");
				write.println("\t\ttooltip=\""+x.get(i).get(0).getName()+"\"");
				if(x.get(i).get(0).getType()==1)
					write.println("\t\tfillcolor=\"red\"");
				write.println("\t\tlabel=\""+x.get(i).get(0).getName()+"\"");
				write.println("\t\t];");
				write.println();
				
			}
				
			for(int i =0;i<x.size();i++)
			{
				for(int j =1;j<x.get(i).size();j++)
				{
					write.println("\t\t"+x.get(i).get(0).getName().replaceAll(" ", "_") + " -> " + x.get(i).get(j).getName().replaceAll(" ", "_"));
					write.println("\t\t[");
					write.println("\t\tarrowhead=\"normal\"");
					write.println("\t\tlabel=\""+x.get(i).get(j).getTran()+"\"");
					write.println("\t\t];");
					write.println();
				}
				
				for(int j =0;j<x.size();j++)	
				{
					if((x.get(j).get(0).getType()==1))
					{
						write.println("\t\t"+x.get(i).get(0).getName().replaceAll(" ", "_") + " -> " + x.get(j).get(0).getName().replaceAll(" ", "_"));
						write.println("\t\t[");
						write.println("\t\tarrowhead=\"normal\"");
						write.println("\t\tlabel=\""+x.get(j).get(0).getTran()+"\"");
						write.println("\t\t];");
						write.println();
					}
				}
						
			}
				
			write.println("\t}");
			write.println("}");
			write.close();
			JOptionPane.showMessageDialog(null,fileName+".dot has been saved on the Desktop", "Name", JOptionPane.INFORMATION_MESSAGE);

		}
		
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,"Dot file could not be created", "Name", JOptionPane.INFORMATION_MESSAGE);
		}
			
	}
	
	
	public static void main(String[] args)
	{
		//creating a frame
		JFrame frame = new JFrame("JFileChoose");
		
		//creating a search fox for the file
		JFileChooser fc = new JFileChooser();
		frame.setVisible(true);
		int returnVal = fc.showOpenDialog(frame);																	 
		
		//stores the file
		File file= null;
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			file = fc.getSelectedFile();
			
			//This is where a real application would open the file.
			System.out.println("Opening: " + file.getName());
			
		}
		else
		{
			System.out.println("User Cancelled the operation.");
		}
		
		String theFile = null;
		
		//Reads the file
		ReadTheFile re = new ReadTheFile();
		if(file == null)
			System.exit(0);
		else
			theFile=re.reading(file); 
	
		//array of link-list
		List<List<Props>> x = new ArrayList<List<Props>>();
		
		//flag variable used to store the number of nodes
		int k =0;
		
		
		//find the name of the file
		Pattern p = Pattern.compile("\\{\"name\":\"(\\s*\\w*\\d*)*\"");
		Matcher m = p.matcher( theFile );
		
		if(m.find())
			nameOfFile = m.group().substring(9,m.group().length()-1);

		
		p = Pattern.compile("\\{\"go_to\".*}],");
		m = p.matcher( theFile );
		//end index
		int ei=0; 	
		
		//find the start and end of the whole array of nodes
		if (m.find()) 
			ei=m.end();
		
		//pattern to determine how many nodes are there
		p = Pattern.compile("\\{\"go_to\"");
		m = p.matcher( theFile );
		
		//loop which finds the number of nodes
		while (m.find()) 	
			k++;
		
		m = p.matcher(theFile);
	
		//array which stores the start of all the nodes 
		int nodStarArr[]=new int[k];
		
		//populate the array with the start of all the nodes 
		for(int i =0;m.find();i++)
		{
			nodStarArr[i]=m.start();				
		}
		
		//patterns which has response conditions
		p = Pattern.compile("\\{\"type\":\"response_condition\"");
		m = p.matcher( theFile );
		
		if(m.find())
		{
			ei = m.start()+1;
		}
		
			
		//array which stores the contents of the nodes as strings
		String nodStringsArr[]=new String[k];
		
		//populate the array with the contents of the nodes as strings and initialize the nodes with those contents
		for(int i =0;i<k;i++)
		{	
			//stores the node string in arrays and the if condition checks it is the last node string
			if(i!=k-1)
				nodStringsArr[i]= theFile.substring(nodStarArr[i],nodStarArr[i+1]-1);		
			else
				nodStringsArr[i]= theFile.substring(nodStarArr[i],ei-2);	
	
			//pattern for the name of the node
			p = Pattern.compile("\"dialog_node\":\"\\w*(\\s*\\w*\\d*)*\",");
			m = p.matcher(nodStringsArr[i]);
			
			//stores the name of the dialog node
			String child= "none";
			
			if (m.find()) 
			{	
				child=  m.group().substring(15,m.group().length()-2);
				
				//creates a new node if it is not yet created
				if(isPresent(x,child)==-1)
					{
						List<Props> y = new LinkedList<Props>();
						x.add(y);
						Props z= new Props();
						y.add(z);
						z.setName(child);	
								
					}
					
			}
			
			//pattern to check if the node has a parent
			Pattern pat = Pattern.compile("\"parent\":\"(\\w*\\s*\\d*)+\"");
			Matcher mat = pat.matcher(nodStringsArr[i]);
			
			//stores the name of the parent
			String parent  = "none";
			
			//pattern for the transition condition
			pat = Pattern.compile("\"conditions\":\"(\\w*\\s*\\d*#*@*-*)+\"");
			Matcher un = pat.matcher(nodStringsArr[i]);
			String tra = "none";
			
			//check if it has a parent node
			if (mat.find()) 
			{	
				parent = mat.group().substring(10,mat.group().length()-1);
				x.get(isPresent(x,child)).get(0).setType(0);			
				
				//see if the parent node is already created
				if(isPresent(x,parent)!=-1)
				 {
							
					Props pr= new Props();
					x.get(isPresent(x,parent)).add(pr);
					pr.setType(2);
					pr.setName(child);
					
					//checks if there is a transition to that state
					if(un.find())
					{
						tra= un.group().substring(14,un.group().length()-1);
						pr.setTran(tra);
					}
				 }	 
				else
				 {
					
					//parent node not created so create a parent node and add the child node to it
					List<Props> yf = new LinkedList<Props>();
					x.add(yf);
					Props ra= new Props();
					yf.add(ra);
					//ra.setType(-1);
					ra.setName(parent);				
						
					Props pr= new Props();
					x.get(isPresent(x,parent)).add(pr);
					pr.setType(2);
					pr.setName(child);
						
					//checks if there is a transition to that state
					if(un.find())
					{
						tra= un.group().substring(14,un.group().length()-1);
						pr.setTran(tra);
					}
					
				 }		
					
			}
			else
			{
				x.get(isPresent(x,child)).get(0).setType(1);
				
				//checks if there is a transition to that state
				if(un.find())
				{
					tra= un.group().substring(14,un.group().length()-1);
					x.get(isPresent(x,child)).get(0).setTran(tra);
				}
				
			}
				
			
		}
		
		
		writing(x);
		frame.dispose();
		
		/*
		//printing the array of link-list
		for(int i =0;i<x.size();i++)
		{
			//System.out.println(nodStringsArr[i]);
		
			for(int j =0;j<x.get(i).size();j++)
			{
				System.out.print(x.get(i).get(j).getName() +" "+ x.get(i).get(j).getType()+" "+ x.get(i).get(j).getTran()+" ----> ");
			}
			System.out.println();
		}
		*/
			
	}
	
}