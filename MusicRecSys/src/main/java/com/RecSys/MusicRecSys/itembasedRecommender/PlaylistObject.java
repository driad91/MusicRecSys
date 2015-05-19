package com.RecSys.MusicRecSys.itembasedRecommender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PlaylistObject {

	private int counter;
	private ArrayList<String> total_song_names;
	private ArrayList<String> total_user_id;
	private HashMap<String, Integer> total_song_playcounts;
	private HashMap<String, Integer> user_total_counts;
	private HashMap<String, HashMap<String, Integer>> user_song_reference;
	private HashMap<String, ArrayList<TopRank>> all_user_playlist_weight;
	private ArrayList<TopRank> user_playlist_weight;

	
	
	public PlaylistObject() {
		super();
		this.counter = 0;
		this.total_song_names = new ArrayList<String>();
		this.total_song_playcounts = new HashMap<String, Integer>();
		this.total_user_id = new ArrayList<String>();
		this.user_song_reference = new HashMap<String, HashMap<String, Integer>>();
		this.user_total_counts = new HashMap<String, Integer>();
		this.all_user_playlist_weight = new HashMap<String, ArrayList<TopRank>>();
		this.user_playlist_weight = new ArrayList<TopRank>();
	}

	public HashMap<String, Integer> getUser_total_counts() {
		return user_total_counts;
	}

	public void setUser_total_counts(String input_UserID, int input_Playcounts) {

		// this.user_total_counts = user_total_counts;

		if (this.user_total_counts.containsKey(input_UserID) == true) {
			Integer count = this.user_total_counts.get(input_UserID);
			count = count + input_Playcounts;
			this.user_total_counts.put(input_UserID, count);

		} else if (this.user_total_counts.containsKey(input_UserID) == false) {
			this.user_total_counts.put(input_UserID, input_Playcounts);
		}

	}

	public void setCounter(int counter) {
		this.counter = counter++;
	}

	public void setTotal_song_names(String input_SongID) {
		if (this.total_song_names.contains(input_SongID) != true) {
			this.total_song_names.add(input_SongID);
		}
	}

	public void setTotal_song_playcounts(String input_songId,
			int input_Playcount) {

		if (this.total_song_playcounts.containsKey(input_songId) == true) {
			Integer getPlaycount = this.total_song_playcounts.get(input_songId);
			getPlaycount = getPlaycount + input_Playcount;
			this.total_song_playcounts.put(input_songId, getPlaycount);
		} else if (this.total_song_playcounts.containsKey(input_songId) == false) {
			this.total_song_playcounts.put(input_songId, input_Playcount);
		}

	}

	public ArrayList<String> getTotal_user_id() {
		return total_user_id;
	}

	public void setTotal_user_id(String input_UserID) {

		if (this.total_user_id.contains(input_UserID) == false) {
			if (input_UserID != null) {
				this.total_user_id.add(input_UserID);
			}
		}
	}

	public HashMap<String, HashMap<String, Integer>> getUser_song_reference() {
		return user_song_reference;
	}

	public void setUser_song_reference(String input_UserId,
			String input_SongId, int input_Playcount) {

		if (this.user_song_reference.containsKey(input_UserId) == true) {
			HashMap<String, Integer> getUSR_HM = this.user_song_reference
					.get(input_UserId);
			if (getUSR_HM.containsKey(input_SongId) == true) {
				Integer getCounter = getUSR_HM.get(input_SongId);
				getCounter = getCounter + input_Playcount;
				getUSR_HM.put(input_SongId, getCounter);

			} else if (getUSR_HM.containsKey(input_SongId) == false) {
				getUSR_HM.put(input_SongId, input_Playcount);

			}

		} else if (this.user_song_reference.containsKey(input_UserId) != true) {
			HashMap<String, Integer> user_song_playcount = new HashMap<String, Integer>();

			user_song_playcount.put(input_SongId, input_Playcount);

			this.user_song_reference.put(input_UserId, user_song_playcount);
			// this.user_song_playcounts.put(input_SongId, input_Playcount);
		}

	}

	public ArrayList<String> getTotal_song_names() {
		return total_song_names;
	}

	public int getCounter() {
		return counter;
	}

	public HashMap<String, Integer> getTotal_song_playcounts() {
		return total_song_playcounts;
	}

	public HashMap<String, ArrayList<TopRank>> getAllUserPlaylistWeight(
			ArrayList<String> inputTotalUserID,
			HashMap<String, HashMap<String, Integer>> inputPlaylist,
			HashMap<String, Integer> inputUserTotalCounts) {

		int first_counter = 0;
		 int second_counter = 0;

		for (String userid : inputTotalUserID) {
			ArrayList<TopRank> user_playlist_ranked = new ArrayList<TopRank>();
			HashMap<String, Integer> user_playlist = inputPlaylist.get(userid);
			Set<String> songs = user_playlist.keySet();
			for (String songID : songs) {
				TopRank trElement = new TopRank();
				Integer total_count = inputUserTotalCounts.get(userid);
				Integer playcount = user_playlist.get(songID);
				float f_total_count = total_count;
				float f_playcount = playcount;
				float weighting = f_playcount / f_total_count;

//				if (weighting >= 0.01) {
					trElement.setId(songID);
					trElement.setSimilarity(weighting);
					user_playlist_ranked.add(trElement);
//				}

//				 if (second_counter>=10){break;}
//				 second_counter++;
			} // Inner-For
			Collections.sort(user_playlist_ranked);
			Collections.reverse(user_playlist_ranked);

			this.all_user_playlist_weight.put(userid, user_playlist_ranked);

//			if (first_counter >= 10) {
//				break;
//			}
//			first_counter++;
		} // Outer-For

		return this.all_user_playlist_weight;
	}

	
	public ArrayList<TopRank> getUserPlaylistWeight(String input_UserID,
			HashMap<String, HashMap<String, Integer>> inputPlaylist,
			HashMap<String, Integer> inputUserTotalCounts) {


		String userid = input_UserID;
			ArrayList<TopRank> user_playlist_ranked = new ArrayList<TopRank>();
			
			
			HashMap<String, Integer> user_playlist = inputPlaylist.get(userid);
			Set<String> songs = user_playlist.keySet();
			
			for (String songID : songs) {
				TopRank trElement = new TopRank();
				Integer total_count = inputUserTotalCounts.get(userid);
				Integer playcount = user_playlist.get(songID);
				float f_total_count = total_count;
				float f_playcount = playcount;
				float weighting = f_playcount / f_total_count;

					trElement.setId(songID);
					trElement.setSimilarity(weighting);
					user_playlist_ranked.add(trElement);

			} // Inner-For
			Collections.sort(user_playlist_ranked);
			Collections.reverse(user_playlist_ranked);
			
			this.user_playlist_weight = user_playlist_ranked;


		return this.user_playlist_weight;
	}

	
}
