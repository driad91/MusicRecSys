package com.RecSys.MusicRecSys.itembasedRecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class lFM_tag {
	private String path;
	private String track_id;
	private String tag;
	private Integer tag_count;
	
	private HashMap<String, ArrayList<String>> tag_index;
	private HashMap<String, ArrayList<String>> reverse_tag_index;

	public lFM_tag(String path) {
		super();
		this.path = path;
		this.tag_index = new HashMap<String, ArrayList<String>>();
		this.reverse_tag_index = new HashMap<String, ArrayList<String>>();
	}

	public HashMap<String, ArrayList<String>> getTagIndex(String tag) {
		this.tag = tag;

		Connection c = null;

		try {
			Statement stmt3 = null;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt3 = c.createStatement();

			ResultSet rs_lFM_tag = stmt3.executeQuery("SELECT "
					+ " tids.tid AS track_id, tags.tag AS term, "
					+ " tid_tag.val AS weigth  FROM tid_tag "
					+ " JOIN tids ON tid_tag.tid = tids.rowid "
					+ " JOIN tags ON tid_tag.tag = tags.rowid "
					+ " WHERE term = '" + this.tag + "' ;");

			while (rs_lFM_tag.next()) {
				String track_id = rs_lFM_tag.getString("track_id");

				if (this.tag_index.containsKey(this.tag) == true) {
					ArrayList<String> check_track = this.tag_index.get(tag);
					if (check_track.contains(track_id) != true) {
						check_track.add(track_id);
					}
				} else if (this.tag_index.containsKey(this.tag) != true) {
					ArrayList<String> initial_track = new ArrayList<String>();
					initial_track.add(track_id);
					this.tag_index.put(this.tag, initial_track);
				}
			}
			rs_lFM_tag.close();
			stmt3.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return this.tag_index;
	}

	public HashMap<String, ArrayList<String>> getReverseTagIndex(String track_id) {

		this.track_id = track_id;
		Connection c = null;

		try {
			Statement stmt3 = null;
			// Statement 3
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt3 = c.createStatement();

			ResultSet rs_lFM_tag = stmt3.executeQuery("SELECT "
					+ " tids.tid AS track_id," + " tags.tag AS tag, "
					+ " tid_tag.val AS weigth  FROM tid_tag "
					+ " JOIN tids ON tid_tag.tid = tids.rowid "
					+ " JOIN tags ON tid_tag.tag = tags.rowid "
					+ " WHERE track_ID = '" + this.track_id + "';");

			while (rs_lFM_tag.next()) {
				String tag = rs_lFM_tag.getString("tag");

				// reverse_tag_index
				if (this.reverse_tag_index.containsKey(track_id) == true) {
					ArrayList<String> check_tag = this.reverse_tag_index
							.get(this.track_id);
					if (check_tag.contains(tag) != true) {
						check_tag.add(tag);
					}
				} else if (this.reverse_tag_index.containsKey(this.track_id) != true) {
					ArrayList<String> initial_tag = new ArrayList<String>();
					initial_tag.add(tag);
					this.reverse_tag_index.put(this.track_id, initial_tag);
				}

			}
			rs_lFM_tag.close();
			stmt3.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return this.reverse_tag_index;
	}

	public int getTagCount(String tag) {
		this.tag = tag;
		this.tag_count = 0;
		
		Connection c = null;

		try {
			Statement stmt3 = null;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt3 = c.createStatement();

			ResultSet rs_lFM_tag = stmt3.executeQuery("SELECT "
					+ " tags.tag AS tag, "
					+ " tid_tag.val AS weigth  FROM tid_tag "
					+ " JOIN tags ON tid_tag.tag = tags.rowid "
					+ " WHERE tags.tag = '" + this.tag + "' ;");

			while (rs_lFM_tag.next()) {
				int weight = rs_lFM_tag.getInt("weigth");
				this.tag_count = this.tag_count + 1;

			}
			rs_lFM_tag.close();
			stmt3.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return this.tag_count;
	}

}
