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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class lFM_similarity {
	private String track_id;
	private String path;
	private ArrayList<TopRank> ranking;

	private HashMap<String, String> track_index;
	private HashMap<String, String> reverse_track_index;

	public lFM_similarity(String path) {
		super();
		this.path = path;

		this.track_index = new HashMap<String, String>();
		this.reverse_track_index = new HashMap<String, String>();

	}

	public HashMap<String, String> getTrackIndex(String track_id) {

		// HashMap<String, String> track_index = new HashMap<String, String>();
		// HashMap<String, String> reverse_track_index = new HashMap<String,
		// String>();

		this.track_id = track_id;

		Connection c = null;
		// print 'We get all similar songs (with value) to %s' % tid
		// sql = "SELECT target FROM similars_src WHERE tid='%s'" % tid
		// Track id, with similar songs

		try {
			Statement stmt3 = null;
			// Statement 3
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt3 = c.createStatement();
			ResultSet rs_lFM_sim = stmt3
					.executeQuery("SELECT target FROM similars_src WHERE tid ='"
							+ this.track_id + "';");
			while (rs_lFM_sim.next()) {
				// String track_id = rs_lFM_sim.getString("tid");
				String similar_tracks = rs_lFM_sim.getString("target");
				if (this.track_index.containsKey(this.track_id) != true) {
					this.track_index.put(this.track_id, similar_tracks);
				}

			}
			rs_lFM_sim.close();
			stmt3.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return this.track_index;
	}

	public HashMap<String, String> getReverseTrackIndex(String track_id) {

		// HashMap<String, String> track_index = new HashMap<String, String>();
		// HashMap<String, String> reverse_track_index = new HashMap<String,
		// String>();

		this.track_id = track_id;
		Connection c = null;
		try {
			Statement stmt4 = null;
			// print 'We get all songs which consider %s as similar' % tid
			// sql = "SELECT target FROM similars_dest WHERE tid='%s'" % tid

			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt4 = c.createStatement();
			ResultSet rs_lFM_rev_sim = stmt4
			// .executeQuery("SELECT tid, target FROM similars_dest;");
					.executeQuery("SELECT target FROM similars_dest WHERE tid= '"
							+ this.track_id + "';");
			// TRCCKNV128F149573B
			while (rs_lFM_rev_sim.next()) {
				// String track_id = rs_lFM_rev_sim.getString("tid");
				String referencing_tracks = rs_lFM_rev_sim.getString("target");
				if (this.reverse_track_index.containsKey(this.track_id) != true) {
					this.reverse_track_index.put(this.track_id,
							referencing_tracks);
				}

			}
			rs_lFM_rev_sim.close();
			stmt4.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return this.reverse_track_index;
	}

	public ArrayList<TopRank> getTopk(String lFM_sim_string, int topK) {
		this.ranking = new ArrayList<TopRank>();

		if (lFM_sim_string != null) {
			List<String> input_ranking = Arrays.asList(lFM_sim_string
					.split(","));
			ArrayList<TopRank> backup_ranking = new ArrayList<TopRank>();

			for (int i = 0; i <= input_ranking.size() - 1; i = i + 2) {
				TopRank trElement = new TopRank();
				trElement.setId(input_ranking.get(i));
				float similarity = Float.parseFloat(input_ranking.get(i + 1));
				trElement.setSimilarity(similarity);
				this.ranking.add(trElement);
			}

			backup_ranking = this.ranking;

			for (int i = this.ranking.size() - 1; i >= topK; i--) {
				backup_ranking.remove(i);
			}
			this.ranking = backup_ranking;

			return this.ranking;
		} else {
			return null;
		}
	}

}
