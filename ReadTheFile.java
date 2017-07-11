package transitionStates;

import java.util.*;
import java.io.*;

public class ReadTheFile 
{
	public String reading(File file)
	{

		// This will reference one line at a time
		String line = null;
		String wholeFile =null;

		try 
		{
			// FileReader reading the file.
			FileReader fileReader =  new FileReader(file);
	
			// Wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	
			while((line = bufferedReader.readLine()) != null) 
			{
				wholeFile += line;
				wholeFile += "\n";
				            	
			}   
				           
			// closing the file
			bufferedReader.close();         
		}
			        
		catch(FileNotFoundException ex)
		{
			
			System.out.println("Unable to open file '" + file.getName() + "'");                
		}
			        
		catch(IOException ex) 
		{
			
			System.out.println("Error reading file '" + file.getName() + "'");                  			    
		}
			        
		return wholeFile;
	}
}