package com.RecSys.MusicRecSys;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.RecSys.MusicRecSys.itembasedRecommender.ArtistSimilarity_Recommender;
import com.RecSys.MusicRecSys.itembasedRecommender.PlaylistObject;
import com.RecSys.MusicRecSys.itembasedRecommender.TermBased_Recommender;
import com.RecSys.MusicRecSys.itembasedRecommender.TopRank;
import com.RecSys.MusicRecSys.itembasedRecommender.TrackObject;
import com.RecSys.MusicRecSys.itembasedRecommender.Track_Metadata;
import com.RecSys.MusicRecSys.itembasedRecommender.lFM_similarity;
import com.RecSys.MusicRecSys.itembasedRecommender.lFM_tag;

public class ItemBasedRecommender_Test {

	public static void main(String[] args) {

		/*
		 * Set paths to SQLite DB files
		 */

		String trackmetadata_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/track_metadata.db";
		String lFM_sim_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_similars.db";
		
		// String artist_term_path =
		// "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_term.db";
		// String artist_similarity_path =
		// "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_similarity.db";
		// String lFM_tags_path =
		// "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_tags.db";

		
		/*
		 * User Playlist
		 */
		String userTripletsPath = "C:/Users/Laz/Desktop/WM Project/Datasets/train_triplets.txt/train_triplets.txt";

		/*
		 * Part I: Load subset of dataset into PlaylistObject
		 * 
		 * 
		 */
		
		BufferedReader usersBR;
		try {
			usersBR = new BufferedReader(new FileReader(userTripletsPath));

			String line = usersBR.readLine();

			PlaylistObject PlO = new PlaylistObject();
			int subset_counter = 0;
			while (line != null) {

				line = usersBR.readLine();
				String element[] = line.split("\\s+");
				int playcount = Integer.parseInt(element[2]); // Convert to int
				String userID = element[0];
				String songID = element[1];
				PlO.setTotal_song_names(songID);
				PlO.setTotal_song_playcounts(songID, playcount);
				PlO.setTotal_user_id(userID);
				PlO.setUser_song_reference(userID, songID, playcount);
				PlO.setUser_total_counts(userID, playcount);

				// For testcase, just extracting subset of train_triple
				if (subset_counter == 5000) {
					break;
				}
				subset_counter++;

			}

			usersBR.close();

			/*
			 * Part II: Initialize Objects
			 * 
			 * 
			 * 
			 */

			HashMap<String, TrackObject> track_MD = new HashMap<String, TrackObject>();

			HashMap<String, HashMap<String, Integer>> playlist = PlO
					.getUser_song_reference();

			HashMap<String, Integer> userTotalCounts = PlO
					.getUser_total_counts();
			
//			First ID: b80344d063b5ccb3212f76538f3d9e43d87dca9e
//			Random ID :558366e5146eed39ba7b2ac9ef0302fe5f22e064
//			You need to set subset big enought, otherwise ID won´t be in getUserPlaylistWeight!
			
			String  testID = "b80344d063b5ccb3212f76538f3d9e43d87dca9e";

			ArrayList<TopRank> testUserPlaylist = PlO.getUserPlaylistWeight(
					testID, playlist, userTotalCounts);

			ArrayList<TopRank> testUserRankingList = new ArrayList<TopRank>();
			
			for (TopRank testplaylist : testUserPlaylist) {
				TopRank testUserRankingElement = new TopRank();
				
				String song_id = testplaylist.getId();
				float playlist_weight = testplaylist.getSimilarity();

				Track_Metadata tm = new Track_Metadata(trackmetadata_path,
						song_id);
				track_MD = tm.getMetadata();
				String track_id = track_MD.get(song_id).getTrack_id();

				lFM_similarity lFMs = new lFM_similarity(lFM_sim_path);

//				System.out.println("SongID: "+song_id+ "Track_ID: " + track_id+ "Playlist-Weight: "+playlist_weight);

				String lFM_similarity_AL = lFMs.getTrackIndex(track_id).get(
						track_id);

				
				if (lFM_similarity_AL != null) { 
//				if needed, because lFM database don´t cover every track_id of the playlist!
					
					for (TopRank topRank : (lFMs.getTopk(lFM_similarity_AL, 3))) {
						
					/*
					 * Computation of total_similarity =
					 * similartiy of lFM dataset * playcounts of individual track for specific user / total playcount of user
					 */
						
//						System.out.println("ID: " + topRank.getId()
//								+ " Value: " + topRank.getSimilarity()+" Total_Similarity:" + (topRank.getSimilarity()*playlist_weight));
						float total_similariy = (topRank.getSimilarity()*playlist_weight);
						testUserRankingElement.setId(topRank.getId());
						testUserRankingElement.setSimilarity(total_similariy);
						
						if(testUserRankingList.contains(testUserRankingElement)!=true)
						{
							testUserRankingList.add(testUserRankingElement);
						}
					}

				}

			} // End for -testplaylist
			
			
			
			/*
			 * Part III: Show TopK RankList of recommended songs
			 * 
			 * 
			 */
			Collections.sort(testUserRankingList);
			Collections.reverse(testUserRankingList);
			
			ArrayList<TopRank> backupRankingList = testUserRankingList;
			int topK = 15;

			for (int i = backupRankingList.size() - 1; i >= topK; i--) {
				backupRankingList.remove(i);
			}
		
			
			testUserRankingList = backupRankingList;
			System.out.println("FinalRanking for UserID :  " +testID);
			for (TopRank rank : testUserRankingList) {
				System.out.println("Track  ID: "+rank.getId() + "Similarity: " + rank.getSimilarity());
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
