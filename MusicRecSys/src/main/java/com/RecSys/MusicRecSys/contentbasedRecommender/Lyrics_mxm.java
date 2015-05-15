package com.RecSys.MusicRecSys.contentbasedRecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Lyrics_mxm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Connection c = null;
			// Statement 5
			Statement stmt5 = null;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/mxm_dataset.db");
			c.setAutoCommit(false);

			stmt5 = c.createStatement();
			ResultSet rs_lyrics = stmt5
					.executeQuery("SELECT track_id, mxm_tid, word, count, is_test FROM lyrics;");
			//is_test specifies if example is from train set(0) or test set (1)
			while (rs_lyrics.next()) {
				System.out.println("Test");
				// String artist_id = rs.getString("target");
				// System.out.println( "ID = " + artist_id );
			}
			rs_lyrics.close();
			stmt5.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Operation done successfully");
		
		
	}

}
