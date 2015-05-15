package com.RecSys.MusicRecSys.itembasedRecommender;

/*
 * 		WebMining Project: MSD		
 * 
 *  	Term-based Recommender
 * 		Java class creates two indexes ( term_index & artist_index)

 *		Structure of term_index:
 *		term : ArrayList of artist_ids
 *		
 *		Structure of artist_index
 *		artist_id: ArrayList or terms
 * 
 * 		Code written by Daniel Riad, Hanna Farag, Amina Kadry, �mit Tepe
 * 
 * 		To-Do's:
 * 		- Comparison methods (like artist_0ids with the most-frequent similar tags should be recommended
 *  	- TOP 10 
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class TermBased_Recommender {

	public static void main(String[] args) throws ClassNotFoundException {

		HashMap<String, ArrayList<String>> term_index = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> artist_index = new HashMap<String, ArrayList<String>>();

		// artist_mbtag dataset
		Connection c = null;
		Statement stmt3 = null;
		try {
			Class.forName("org.sqlite.JDBC");

			c = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_term.db");

			c.setAutoCommit(false);

			stmt3 = c.createStatement();
			ResultSet rs_artist_mbtag = stmt3
					.executeQuery("SELECT artist_id, mbtag FROM artist_mbtag;");
			while (rs_artist_mbtag.next()) {
				String term = rs_artist_mbtag.getString("mbtag");
				String artist_id = rs_artist_mbtag.getString("artist_id");

				if (term_index.containsKey(term) == true) {
					ArrayList<String> check_terms = term_index.get(term);
					if (check_terms.contains(artist_id) != true) {
						check_terms.add(artist_id);
					}
				} else if (term_index.containsKey(term) != true) {
					ArrayList<String> initial_term = new ArrayList<String>();
					initial_term.add(artist_id);
					term_index.put(term, initial_term);
				}

				if (artist_index.containsKey(artist_id) == true) {
					ArrayList<String> check_artists = artist_index
							.get(artist_id);
					if (check_artists.contains(term) != true) {
						check_artists.add(term);
					}

				} else if (artist_index.containsKey(artist_id) != true) {
					ArrayList<String> initial_artist = new ArrayList<String>();
					initial_artist.add(term);
					artist_index.put(artist_id, initial_artist);
				}

			}

			rs_artist_mbtag.close();
			stmt3.close();
			c.close();

			// artist_term dataset
			Statement stmt4 = null;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_term.db");
			c.setAutoCommit(false);

			stmt4 = c.createStatement();
			ResultSet rs_artist_term = stmt4
					.executeQuery("SELECT artist_id, term FROM artist_term;");
			while (rs_artist_term.next()) {

				String term = rs_artist_term.getString("term");
				String artist_id = rs_artist_term.getString("artist_id");

				if (term_index.containsKey(term) == true) {
					ArrayList<String> check_terms = term_index.get(term);
					if (check_terms.contains(artist_id) != true) {
						check_terms.add(artist_id);
					}
				} else if (term_index.containsKey(term) != true) {
					ArrayList<String> initial_term = new ArrayList<String>();
					initial_term.add(artist_id);
					term_index.put(term, initial_term);
				}

				if (artist_index.containsKey(artist_id) == true) {
					ArrayList<String> check_artists = artist_index
							.get(artist_id);
					if (check_artists.contains(term) != true) {
						check_artists.add(term);
					}

				} else if (artist_index.containsKey(artist_id) != true) {
					ArrayList<String> initial_artist = new ArrayList<String>();
					initial_artist.add(term);
					artist_index.put(artist_id, initial_artist);
				}

			}
			rs_artist_term.close();
			stmt4.close();
			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("term_index: ");
		System.out.println(term_index.get("european"));
		System.out.println("artist_index:");
		System.out.println("ARSD1PL1187FB3BD81");

		// Output looks like:
		// term_index:
		// [AR00B1I1187FB433EB, AR07MCL1187B9B2DCA, AR0B6OD1187B9ABED2,
		// AR0G6AY1187B990CD8]
		// artist_index:
		// ARSD1PL1187FB3BD81

	}

}
