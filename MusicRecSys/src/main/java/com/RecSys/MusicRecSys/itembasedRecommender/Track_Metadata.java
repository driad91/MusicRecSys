package com.RecSys.MusicRecSys.itembasedRecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public final class Track_Metadata {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, TrackObject> track_metadataHM = new HashMap<String, TrackObject>();

		Connection c = null;

		// Getting track_metadata
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/track_metadata.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT track_id, title, song_id, release,  artist_id, artist_mbid, "
							+ "artist_name, duration, artist_familiarity, artist_hotttnesss,year,"
							+ " track_7digitalid    FROM songs;");
			while (rs.next()) {
				String track_id = rs.getString("track_id");
				String title = rs.getString("title");
				String song_id = rs.getString("song_id");
				String release = rs.getString("release");
				String artist_id = rs.getString("artist_id");
				String artist_mbid = rs.getString("artist_mbid");
				String artist_name = rs.getString("artist_name");
				float duration = rs.getFloat("duration");
				float artist_familiarity = rs.getFloat("artist_familiarity");
				float artist_hotttnesss = rs.getFloat("artist_hotttnesss");
				int year = rs.getInt("year");
				int track_7digitalid = rs.getInt("track_7digitalid");

				if (track_metadataHM.containsKey(artist_id) != true) {
					TrackObject hm_trackobject = new TrackObject(artist_name,
							artist_name, artist_name, artist_name, artist_name,
							artist_name, artist_hotttnesss, artist_hotttnesss,
							artist_hotttnesss, track_7digitalid,
							track_7digitalid);
					track_metadataHM.put(artist_id, hm_trackobject);
				}

			}
			rs.close();
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");

		System.out.println(track_metadataHM);

	}

}
