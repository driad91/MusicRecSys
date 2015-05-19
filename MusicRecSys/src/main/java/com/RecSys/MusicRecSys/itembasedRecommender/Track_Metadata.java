package com.RecSys.MusicRecSys.itembasedRecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public final class Track_Metadata {

	private String path;
	private String song_id;
	private String track_id;
	private HashMap<String, TrackObject> track_metadataHM;

	// private TrackObject hm_trackobject;

	public Track_Metadata(String path, String song_id) {
		super();
		this.path = path;
		this.song_id = song_id;

		// HashMap<String, TrackObject> track_metadataHM = new
		// HashMap<String,TrackObject>();
		track_metadataHM = new HashMap<String, TrackObject>();

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT track_id, title, release, artist_id, artist_mbid, "
							+ " artist_name, duration, artist_familiarity, artist_hotttnesss,year, "
							+ " track_7digitalid FROM songs  WHERE song_id = '"
							+ song_id + "' ;");
			while (rs.next()) {
				String track_id = rs.getString("track_id");
				String title = rs.getString("title");
				String release = rs.getString("release");
				String artist_id = rs.getString("artist_id");
				String artist_mbid = rs.getString("artist_mbid");
				String artist_name = rs.getString("artist_name");
				float duration = rs.getFloat("duration");
				float artist_familiarity = rs.getFloat("artist_familiarity");
				float artist_hotttnesss = rs.getFloat("artist_hotttnesss");
				int year = rs.getInt("year");
				int track_7digitalid = rs.getInt("track_7digitalid");

				TrackObject hm_trackobject = new TrackObject(track_id, song_id,
						release, artist_id, artist_mbid, artist_name, duration,
						artist_familiarity, artist_hotttnesss, year,
						track_7digitalid, title);

				if (this.track_metadataHM.containsKey(this.song_id) == true) {
					// Just for testing
					this.track_metadataHM.put("DUPLICATE " + this.song_id,
							hm_trackobject);
				}

				if (track_metadataHM.containsKey(this.song_id) != true) {
					this.track_metadataHM.put(this.song_id, hm_trackobject);
				}

			}
			rs.close();
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public HashMap<String, TrackObject> getMetadata() {

		return this.track_metadataHM;
	}

	public String getSongId(String track_id) {
		this.track_id = track_id;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(this.path);
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery(" SELECT song_id FROM songs WHERE track_id = '"
							+ this.track_id + "' ;");
			while (rs.next()) {
				this.song_id = rs.getString("song_id");
			}
			rs.close();
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return this.song_id;
	}

}
