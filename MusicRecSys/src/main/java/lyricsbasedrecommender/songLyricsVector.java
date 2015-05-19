package lyricsbasedrecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class songLyricsVector {
	
String track_id;
double[]songLyricsVectorArray;

 public songLyricsVector(int size,String track_id){
	 
	 	 this.songLyricsVectorArray= new double [size];
	 this.track_id=track_id;
	 
	 
 }
 

 
 public static double calculateIdf(double n,double df){
	 
	 
	 
	 
	 
	 return Math.log(n/df);
	 
	 
 }
 
 public static double calculateTfIdf(double tf,double idf)
 {
	 
	 
	 return tf*idf;
	 
 }

 
	public static ResultSet queryDB (String query) throws Exception{
		
		
		 Connection c = null;
			Statement stmt=null;

			
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Information Retrieval\\mxm_dataset.db");
		      c.setAutoCommit(true);
		     
		      
				
					 
						
					      
					      stmt = c.createStatement();
					    
						   int counter=0;
					      ResultSet rs =   stmt.executeQuery(query);
					
					      return rs;
					   
					     
	}
	
public void addTfIdfToVector(int position,String word) throws Exception {
	
	int n=237662;
	
	int tf=0;
	int df=0;
	double  idf;
	double tfIdf;
	
	ResultSet rsTf=queryDB("SELECT count FROM lyrics where track_id='" + this.track_id+"' AND word='" + word +"'");
	
	if (!rsTf.next())
	{
		tf=0;
		
	}
	
	else
	{
		
			
			tf=Integer.parseInt(rsTf.getString(1));
			
			
		
	}
	
	ResultSet rsDf=queryDB("SELECT df from terms_df WHERE term='" + word +"'");
	
	while (rsDf.next())
	{
		df=Integer.parseInt(rsDf.getString(1));
		
		
	}
	
	
idf=calculateIdf(n, df);

	tfIdf=calculateTfIdf(tf, idf);
	

songLyricsVectorArray[position]=tfIdf;


}
}
