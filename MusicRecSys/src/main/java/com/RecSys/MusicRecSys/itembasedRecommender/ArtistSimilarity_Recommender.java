package com.RecSys.MusicRecSys.itembasedRecommender;

/*
 * 		WebMining Project: MSD		
 * 
 *  	Artist similarity class
 * 		Java class creates one index : similarity_index

 *		Structure of term_index:
 *		artist_id : similar artists
 *		
 * 
 * 		Code written by Daniel Riad, Hanna Farag, Amina Kadry, �mit Tepe
 * 
 * 		To-Do's:
 * 		- Comparison methods
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ArtistSimilarity_Recommender {
	private String path;
	private String artist_id;
	private HashMap<String, ArrayList<String>> similarity_index;
	

	
	public ArtistSimilarity_Recommender(String path) {
		super();
		this.path = path;
		this.similarity_index = new HashMap<String, ArrayList<String>>();
	}

	public HashMap<String, ArrayList<String>> getSimilarityIndex (String artist_id){
		this.artist_id = artist_id;
		

		Connection c = null;
		try {
			Statement stmt2 = null;

			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection(this.path);
			c.setAutoCommit(false);
			stmt2 = c.createStatement();
			ResultSet rs_sim = stmt2
					.executeQuery("SELECT similar FROM similarity WHERE target = '"+ this.artist_id +"' ;");
			while (rs_sim.next()) {

				String similar_artist = rs_sim.getString("similar");

				// similarity_index
				if (this.similarity_index.containsKey(this.artist_id) == true) {
					ArrayList<String> check_similarity = this.similarity_index
							.get(this.artist_id);
					if (check_similarity.contains(similar_artist) != true) {
						check_similarity.add(similar_artist);
					}
				} else {
					ArrayList<String> initial_value = new ArrayList<String>();
					initial_value.add(similar_artist);
					this.similarity_index.put(this.artist_id, initial_value);
				}
			}
			rs_sim.close();
			stmt2.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return this.similarity_index;
	}


}
