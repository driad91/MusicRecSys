package com.RecSys.MusicRecSys.itembasedRecommender;

/*
 * 		WebMining Project: MSD		
 * 
 *  	lastFM similarity 
 * 		Java class creates two indexes ( track_index & reverse_track_index)
 * 
 * 
 * 		Code written by Daniel Riad, Hanna Farag, Amina Kadry, �mit Tepe
 * 
 * 		To-Do's:
 * 		- Creation of methods to look up in SQLite DB
 * 		- Current status: OutOfMemory Error (just look up for one specific ID!)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class lFM_similarity {

	public static void main(String[] args) {

		HashMap<String, String> track_index = new HashMap<String, String>();

		HashMap<String, String> reverse_track_index = new HashMap<String, String>();

		Connection c = null;

		// print 'We get all similar songs (with value) to %s' % tid
		// sql = "SELECT target FROM similars_src WHERE tid='%s'" % tid
		// Track id, with similar songs

		try {
			Statement stmt3 = null;
			// Statement 3
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_similars.db");
			c.setAutoCommit(false);

			stmt3 = c.createStatement();
			ResultSet rs_lFM_sim = stmt3
//					.executeQuery("SELECT tid, target FROM similars_src;");
					.executeQuery("SELECT target FROM similars_src WHERE tid ='TRCCKNV128F149573B';");
			while (rs_lFM_sim.next()) {
				// String artist_id = rs.getString("target");
				// System.out.println( "ID = " + artist_id );
//				String track_id = rs_lFM_sim.getString("tid");
				String similar_tracks = rs_lFM_sim.getString("target");
				System.out.println(similar_tracks);
//				if (track_index.containsKey(track_id) != true) {
//					track_index.put(track_id, similar_tracks);
//				}

			}
			rs_lFM_sim.close();
			stmt3.close();
			c.close();

			Statement stmt4 = null;
			// Statement 4
			// print 'We get all songs which consider %s as similar' % tid
			// sql = "SELECT target FROM similars_dest WHERE tid='%s'" % tid

			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_similars.db");
			c.setAutoCommit(false);

			stmt4 = c.createStatement();
			ResultSet rs_lFM_rev_sim = stmt4
//					.executeQuery("SELECT tid, target FROM similars_dest;");
					.executeQuery("SELECT target FROM similars_dest WHERE tid= 'TRCCKNV128F149573B';");
//			TRCCKNV128F149573B
			while (rs_lFM_rev_sim.next()) {
				// String artist_id = rs.getString("target");
				// System.out.println( "ID = " + artist_id );
//				String track_id = rs_lFM_rev_sim.getString("tid");
				String referencing_tracks = rs_lFM_rev_sim.getString("target");
				System.out.println(referencing_tracks);
//				if (reverse_track_index.containsKey(track_id) != true) {
//					reverse_track_index.put(track_id, referencing_tracks);
//				}

			}
			rs_lFM_rev_sim.close();
			stmt4.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

}
