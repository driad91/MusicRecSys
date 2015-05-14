package com.RecSys.MusicRecSys;

/**
 * Hello world!
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.index.Term;
import org.apache.mahout.cf.taste.hadoop.als.ParallelALSFactorizationJob;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.math.als.ImplicitFeedbackAlternatingLeastSquaresSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
public class App 
{
	//create hashtable for user ID indexing
	public static Hashtable<String,String> indexUserIds (String filePath) throws Exception
	{
		final Hashtable<String,String> hashTableUserIDIndex= new Hashtable<String,String>() {};
		int counter=1;
InputStream userTripletsPath= new FileInputStream( filePath);
    	
    	BufferedReader usersBR = new BufferedReader(new InputStreamReader(userTripletsPath));	
    	String line = usersBR.readLine();

    	
    while (line!=null)
    {
    	
    	
      	if (!hashTableUserIDIndex.containsKey(line.split("\t")[0])){
		hashTableUserIDIndex.put(line.split("\t")[0], String.valueOf(counter));
		counter++;
      	}
		line=usersBR.readLine();
		
    }
return hashTableUserIDIndex;

	}
	
	
	//create hashtable for songs
	public static Hashtable<String,String> indexSongIds (String filePath) throws Exception
	{
		final Hashtable<String,String> hashTableUserIDIndex= new Hashtable<String,String>() {};
		int counter=1;
InputStream userTripletsPath= new FileInputStream( filePath);
    	
    	BufferedReader usersBR = new BufferedReader(new InputStreamReader(userTripletsPath));	
    	String line = usersBR.readLine();

    	
    while (line!=null)
    {
    	
    	
      	if (!hashTableUserIDIndex.containsKey(line.split("\t")[1])){
		hashTableUserIDIndex.put(line.split("\t")[1], String.valueOf(counter));
		counter++;
      	}
		line=usersBR.readLine();
		
    }
return hashTableUserIDIndex;

	}
	
	public static int countLineNumber() {
		  int lines = 0;
		  try {
		   
		   File file = new File("C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Web Mining\\Team Project\\EchoNest Dataset\\train_triplets.txt\\Subset 5 MB\\train_triplets001Subset.txt");
		   LineNumberReader lineNumberReader = new LineNumberReader(
		     new FileReader(file));
		   lineNumberReader.skip(Long.MAX_VALUE);
		   lines = lineNumberReader.getLineNumber();
		   lineNumberReader.close();
		   
		  } catch (FileNotFoundException e) {
		   System.out.println("FileNotFoundException Occured" 
		     + e.getMessage());
		  } catch (IOException e) {
		   System.out.println("IOException Occured" + e.getMessage());
		  }

		  return lines;

		 }
    public static void main( String[] args ) throws Exception 
    {
    	
    
    	
    	
    	String userTripletsPath="C:\\Users\\Daniel\\Desktop\\Uni-Semester 2\\Web Mining\\Team Project\\EchoNest Dataset\\train_triplets.txt\\Subset 5 MB\\train_triplets001Subset.txt";
    	
    	Hashtable <String,String> userIdMapping= indexUserIds(userTripletsPath);
    	Hashtable <String,String> songIdMapping= indexSongIds(userTripletsPath);
    InputStream inputStream=new FileInputStream(userTripletsPath);
    
    	BufferedReader usersBR = new BufferedReader(new InputStreamReader(inputStream));	
    	String line = usersBR.readLine();
    	File fout = new File("Indexed User Triplets.txt");
    	FileOutputStream fos = new FileOutputStream(fout);
     
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    	//loops on original file 
    	while(line!=null) {
    		
//    		
//    		Set entrySet = userIdMapping.entrySet();
//    		
//    		 Iterator iterator = entrySet.iterator();
//    		 Set entrySetSongs = songIdMapping.entrySet();
//     		
//    		 Iterator iteratorSongs = entrySetSongs.iterator();
    		 	 bw.write(userIdMapping.get(line.split("\t")[0]) + "," + songIdMapping.get(line.split("\t")[1]) + "," +line.split("\t")[2]);
			 
			 bw.newLine();
				
    		 line=usersBR.readLine();
    		 //loops on User IDs index
//    		 while(iterator.hasNext())
//    		  {Boolean songFound=false;
//    			
//    			 Map.Entry entry =  (Map.Entry)iterator.next();
//    			 if(entry.getValue().equals(line.split("\t")[0]))
//    			 {
//    				 //loops on songIDs index
//    				 while(iteratorSongs.hasNext())
//    	    		  {
//    	    		
//    	    			 Map.Entry SongEntry =  (Map.Entry)iteratorSongs.next();
//    	    			 if(SongEntry.getValue().equals(line.split("\t")[1]))
//    	    			 {
//    	    				 songFound=true;
//    	    				 
//    	    				 //write to file
//    			
//    				
//    			 }
//    	    		  }
//    			 }
//    		
    			 
//    		if (songFound){
//    			break;
//    			
//    		}
    			
    		//}
    	    		
    		  
    		  
    	
    		
    	}
     usersBR.close();
     
    	bw.close();

		
		

  

    	DataModel model = new FileDataModel(new File("C:\\Users\\Daniel\\Desktop\\Indexed User Triplets 1.txt"));
    	UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model);
    	UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, userSimilarity, model);
    	Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
    			
    	ALSWRFactorizer factorizer = new ALSWRFactorizer(model, 5, 0.065,1);
 
 
	
  	InputStream userTripletsFileStream= new FileInputStream( "C:\\Users\\Daniel\\Desktop\\Indexed User Triplets 1.txt");
    	//ImplicitFeedbackAlternatingLeastSquaresSolver implicitFeedbackFactorizer= new   	ImplicitFeedbackAlternatingLeastSquaresSolver(10,0.065,0.06,)
    	BufferedReader usersTripletsBR = new BufferedReader(new InputStreamReader(userTripletsFileStream));	
    	String lineOne = usersTripletsBR.readLine();
    	
    	
    	int userid=0;
    	
    while (lineOne!=null)
    {
    	
    	if(Integer.parseInt(lineOne.split(",")[0])!=userid){
    	System.out.println(recommender.recommend(Integer.parseInt(lineOne.split(",")[0]), 1));
    userid=	Integer.parseInt(lineOne.split(",")[0]);
    	}
    	
    	lineOne=usersTripletsBR.readLine();
    	 
    	
    }
    }
}
