package com.RecSys.MusicRecSys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class User_Triplets_Sqlite {

	
	public static void insertUserTasteToMxm (){
		
		
		   try {
			   
			   
			 
				Connection c = null;
				Statement stmt=null;
				int progress=100000-16400;
				
			      Class.forName("org.sqlite.JDBC");
			      c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Information Retrieval\\mxm_dataset.db");
			      c.setAutoCommit(false);
			      
			      stmt = c.createStatement();
			      BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Web Mining\\train_triplets.csv"));
				   String line;
				   int counter=0;
				   while ( (line=br.readLine()) != null)
				   {
					   if (counter>=16400)
					   {
				            String[] values = line.split("\t");    //your seperator

				            //Convert String to right type. Integer, double, date etc.
				            
				            
	
				         
				          stmt.executeUpdate("INSERT INTO User_taste_triplets VALUES('"+values[0]+"','"+values[1]+"',"+Integer.parseInt(values[2])+"," + counter +");" );
				          
				        
				          progress--;
				          
				          if (progress==0){
				        	  System.out.println("100 thousand entries");
				        	  
				        	progress=100000;
				        	System.exit(0);
				        	
				          }
				            //Use a PeparedStatemant, it´s easier and safer 
				          
				          
				         c.commit(); 
			
				         
					   }
					   counter++;
					System.out.println(counter);   
				   }
				   br.close();
//			      rs.close();
			      stmt.close();
			      c.close();
			      
			      
	}
		   catch (Exception e)
	    	{
			   System.out.println(e);
	    		
	    	}
	    	
	    
	}
}
