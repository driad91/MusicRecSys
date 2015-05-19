package lyricsbasedrecommender;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class calculateDf {

	
	
	public static void main (String[]args)throws Exception
	{
		ArrayList<String> words=getArrayOfWords();
	String[]wordsArray=	words.toArray(new String[words.size()]);
	Connection c = null;
	Statement stmt=null;

	
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Information Retrieval\\mxm_dataset.db");
      c.setAutoCommit(true);
     
      
		for( String word:wordsArray)
		{
			
			 
				
			      
			      stmt = c.createStatement();
			    
				   
			      ResultSet rs =   stmt.executeQuery("SELECT  COUNT (word) from lyrics where word='" + word+"' AND count>0"  );
			     
				        
			
			  while (rs.next())
				  
				  
			  {
				  
				  words.add(rs.getString(1));
				  stmt.executeUpdate("INSERT INTO terms_df VALUES('"+word+"',"+ Integer.parseInt(rs.getString(1))+")");
				  
			  }
			
			  

			  
		}

		
	}
	
	public static ArrayList<String> getArrayOfWords()
	{
		ArrayList<String> words = new ArrayList<String>();
		
		   try {
			   
			   
			 
				Connection c = null;
				Statement stmt=null;

				
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Information Retrieval\\mxm_dataset.db");
			      c.setAutoCommit(true);
			      
			      stmt = c.createStatement();
			    
				   
			      ResultSet rs =   stmt.executeQuery("SELECT * FROM words" );
				          
				        
			      
			  while (rs.next())
				  
				  
			  {
				  
				  words.add(rs.getString(1));
				  
				  
			  }
			
			  
			  String wordsArray[]=words.toArray(new String[words.size()]);
			  
			  
//				  
			  
//				         c.commit(); 
//			
				         
					   
					  
				 
		
//			      rs.close();
			      stmt.close();
			      c.close();
			      
			      
	}
		   catch (Exception e)
	    	{
			   System.out.println(e);
	    		
	    	}
	    	
		
		   return words;	
	}
	
	
	
}
