package lyricsbasedrecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.math3.stat.regression.RegressionResults;

public class LyricsRecommender {
	
	public static String [][] sort2dArray(String [][] file){
		 Arrays.sort(file, new Comparator<String[]>() {
			 public int compare(String[] array1, String[] array2) {
				int i=0;
				
				  double x = Double.valueOf(array1[1]);
			       double j = Double.valueOf(array2[1]);
			      
			        
			       
			        return Double.compare(x, j);
			    }
		    });
		

		return file;
		
		}
	
	public static double  calculateCosineSimilarity(songLyricsVector vectorA,songLyricsVector vectorB)
	{
		int counter=0;
		double dotProduct=0;
		double euclideanLengthOfVectorA=0;
		double euclideanLengthOfVectorB=0;
		
		
		while(counter<vectorA.songLyricsVectorArray.length)
			
		{
			dotProduct+=vectorA.songLyricsVectorArray[counter]*vectorB.songLyricsVectorArray[counter];
			
			euclideanLengthOfVectorA+=Math.pow(vectorA.songLyricsVectorArray[counter],2);
			euclideanLengthOfVectorB+=Math.pow(vectorB.songLyricsVectorArray[counter],2);
			
					counter++;
					
					
			
		}
		
		euclideanLengthOfVectorA=Math.sqrt(euclideanLengthOfVectorA);
		euclideanLengthOfVectorB=Math.sqrt(euclideanLengthOfVectorB);
		
		return dotProduct/(euclideanLengthOfVectorA*euclideanLengthOfVectorB);
		
		
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
	
	 public static void main(String[]args) throws Exception 
	 {
		 
		String [][]top10SimilarTracks=getTopKSimilarTracks(10, "TRAAAAV128F421A322");
		
		
	int counter=0;
	
	
	while(counter<top10SimilarTracks.length)
	{
		
		System.out.println("Track ID: " + top10SimilarTracks[0][counter]+ "Cosine Simlarity: " + top10SimilarTracks[1][counter]);
		
		counter++;
	}
		 
		 
		 
	 }
	 
	 
	 public static String[][] getTopKSimilarTracks(int k, String track_id) throws Exception{
		
		
		
		 String[]trackIdsArray=getArrayOfTrackIds();
		 
		  String[][] allCosineSimilarities=new String[2][237661];
		  
		  int counter=0;
		 for ( String trackID :trackIdsArray)
		 {
		
			
			 if (trackID!=track_id)
			 {	 
				 
//				 long startTime =System.nanoTime();
		

			 
				 songLyricsVector[]TwoSongVectorsArray= populateSongVectors(track_id, trackID);
//				 long endTime =System.nanoTime();
//				 System.out.println((endTime-startTime)/1000000);
//				 System.exit(0);
				 double cosineSimilarity= calculateCosineSimilarity(TwoSongVectorsArray[0], TwoSongVectorsArray[1]);
				 
				 System.out.println(cosineSimilarity);
				 allCosineSimilarities[0][counter]=trackID;
				 allCosineSimilarities[1][counter]=Double.toString(cosineSimilarity);

			 }
			 
			
			 
			 
		 }
		 
		 
		 
		 
		allCosineSimilarities= sort2dArray(allCosineSimilarities);
		 
		 
		 String[][]topKSimilarSongs=new String[2][k];
		 
		 int i=0;
		 
		 while(i<k)
		 {
			 topKSimilarSongs[0][i]=allCosineSimilarities[0][i];
			 topKSimilarSongs[1][i]=allCosineSimilarities[1][i];
			 
			 i++;
			 
		 }
		 return topKSimilarSongs;
		 
		 
	 }
	 
	 
	 public static String[] getArrayOfTrackIds() throws Exception
	 {
		 ResultSet rs=queryDB("Select distinct track_id from lyrics");
		 
		 
		 ArrayList<String> trackIDs=new ArrayList();
 
 
		 
		  while (rs.next())
			  
			  
		  {
			  
			 trackIDs.add(rs.getString(1));
			  
			  
		  }
		  
		String []tracks=trackIDs.toArray(new String[trackIDs.size()]);
		return tracks;
		
		
	 }
 
	 
	 
	 public static songLyricsVector[] populateSongVectors(String track1, String track2) throws Exception {
	
		 int size=0;
		 ResultSet rs= queryDB("SELECT(SELECT COUNT(DISTINCT word) from lyrics WHERE track_id='" + track1+ "')+ (SELECT COUNT(DISTINCT word) from lyrics WHERE track_id='" + track2 + "') AS SumCount");
		 
		 while (rs.next())
		 {
			 
			 
			 size=Integer.parseInt(rs.getString(1));
		 }
		 
		 songLyricsVector track1Vector=new songLyricsVector(size,track1);
		 songLyricsVector track2Vector=new songLyricsVector(size,track2);
		 
		ResultSet rsOne= queryDB("SELECT DISTINCT word from lyrics WHERE track_id='" + track1 + "' UNION SELECT DISTINCT word from lyrics WHERE track_id='" + track2 + "'");

	
		int counter=0;
		
		
		
		while (rsOne.next() && counter<size)
		{
	
			track1Vector.addTfIdfToVector(counter, rsOne.getString(1));
			
			track2Vector.addTfIdfToVector(counter, rsOne.getString(1));
			
			counter++;
			
			
			
		}
		 
		 
		 songLyricsVector [] songsVectors=new songLyricsVector[2];
		 
		 songsVectors[0]=track1Vector;
		 songsVectors[1]=track2Vector;
		 
		 return songsVectors;
		 
	 
	 }
	 
		
}
