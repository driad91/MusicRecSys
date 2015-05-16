package com.RecSys.MusicRecSys;

import java.util.ArrayList;
import java.util.HashMap;

import com.RecSys.MusicRecSys.itembasedRecommender.ArtistSimilarity_Recommender;
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
		String artist_term_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_term.db";
		String artist_similarity_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_similarity.db";
		String lFM_sim_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_similars.db";
		String lFM_tags_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_tags.db";

		// Example song_id
		String song_id = "SOVFVAK12A8C1350D9";
		System.out.println("Example Song ID: " + song_id);

		// Get metadata of song
		HashMap<String, TrackObject> track_MD = new HashMap<String, TrackObject>();

		Track_Metadata tm = new Track_Metadata(trackmetadata_path, song_id);

		track_MD = tm.getMetadata();

		String artist_id = track_MD.get(song_id).getArtist_id();
		System.out.println("Metadata:    Artist ID: " + artist_id +  "   Artist Name: "+ track_MD.get(song_id).getArtist_name() + " Track_ID: " + track_MD.get(song_id).getTrack_id() );
		
		
		// Get similar artists (ALL!)
		ArtistSimilarity_Recommender ASrecommender = new ArtistSimilarity_Recommender(
				artist_similarity_path);

		ArrayList<String> RawSimilarArtists = ASrecommender.getSimilarityIndex(
				artist_id).get(artist_id);
		TermBased_Recommender tbR = new TermBased_Recommender(artist_term_path);
		tbR.getArtistIndex(artist_id);

		HashMap<String, Float> measure_map = tbR.computeArtistsSimilarity(
				RawSimilarArtists, artist_id);

//		ArrayList<TopRank> mostSimilarArtists = tbR.getTopk(measure_map,
//				RawSimilarArtists, 3);
		System.out.println("Similiar artists: ");

		for (TopRank tr : tbR.getTopk(measure_map, RawSimilarArtists, 3)) {

			System.out.println("ID:" + tr.getId() + "  Value: "
					+ tr.getSimilarity());

		}

		/*
		 * Test Examples!
		 * 
		 * System.out.println("Similar artists: " +
		 * ASrecommender.getSimilarityIndex(artist_id));
		 * 
		 * 
		 * tbR.getArtistIndex(artist_id);
		 * System.out.println(tbR.getArtistIndex(artist_id));
		 * 
		 * tbR.getTermIndex("acid jazz");
		 * System.out.println(tbR.getTermIndex("acid jazz"));
		 */
		System.out.println();
		System.out.println("LastFM Similarity:");

		String track_id = track_MD.get(song_id).getTrack_id();
		lFM_similarity lFMs = new lFM_similarity(lFM_sim_path);
		
		/* System.out.println("getTrackIndex: " + lFMs.getTrackIndex(track_id));
		 * 
		 * Gives a HashMap back, which contains similar tracks (already ranked!)
		 * 
		 * OUTPUT: 
		 *  getTrackIndex: {TRMMMKD128F425225D=TRSJKBK128F4252256,1,TRABGDV128F4252252,0.984607,TRFGYZK128F4282D22,
		 *  0.0689561,TRPHGYN128F4236105,0.0618609,TRFZAAP128F9328AB1,0.0595669}
		 */
		
		String lFM_similarity_AL = lFMs.getTrackIndex(track_id).get(track_id);

		/*	lFMs.getTopk(lFM_similarity_AL, 3);
		 * 	Gives back an ArrayList<TopRank> of the topK elements	
		 * 
		 */

		
		System.out.println("TopK lastFM similar Tracks for k = 3: ");
		for (TopRank topRank : (lFMs.getTopk(lFM_similarity_AL, 3))) {
			System.out.println("ID: " + topRank.getId() + " Value: "
					+ topRank.getSimilarity());

		}

/*		System.out.println("getReverseTrackIndex: "+ lFMs.getReverseTrackIndex(track_id));
 * 		Can be used to get an index, which shows which tracks consider 'track_id' as similar
 * 
 */

		

		lFM_tag lFMtag = new lFM_tag(lFM_tags_path);
		lFMtag.getReverseTagIndex(track_id);
		System.out.println("lastFM Tag: ");

		/* For example song_id no lastFM-tags available! 
		 * 	System.out.println("ReverseTagIndex: "+ lFMtag.getReverseTagIndex(track_id));
		 *  
		 */
		
		System.out.println("ReverseTagIndex with other track_id: "
				+ lFMtag.getReverseTagIndex("TRCCKNV128F149573B"));

		lFMtag.getTagIndex("love songs");

		System.out.println("Tag count: " + lFMtag.getTagCount("pop"));
	}

}
