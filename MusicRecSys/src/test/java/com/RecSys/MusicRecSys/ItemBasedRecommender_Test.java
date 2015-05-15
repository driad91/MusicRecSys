package com.RecSys.MusicRecSys;

import java.util.HashMap;

import com.RecSys.MusicRecSys.itembasedRecommender.ArtistSimilarity_Recommender;
import com.RecSys.MusicRecSys.itembasedRecommender.TermBased_Recommender;
import com.RecSys.MusicRecSys.itembasedRecommender.TrackObject;
import com.RecSys.MusicRecSys.itembasedRecommender.Track_Metadata;
import com.RecSys.MusicRecSys.itembasedRecommender.lFM_similarity;
import com.RecSys.MusicRecSys.itembasedRecommender.lFM_tag;

public class ItemBasedRecommender_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String trackmetadata_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/track_metadata.db";
		String artist_term_path ="jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_term.db";
		String artist_similarity_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/artist_similarity.db";
		
		String lFM_sim_path = "jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_similars.db";
		String lFM_tags_path ="jdbc:sqlite:C:/Users/Laz/Desktop/WM Project/Datasets/lastfm_tags.db";
		
		
		String song_id="SOVFVAK12A8C1350D9";
		
		HashMap<String, TrackObject> track_MD = new HashMap<String, TrackObject>();
		Track_Metadata tm = new Track_Metadata(trackmetadata_path, song_id);
		track_MD = tm.getMetadata();
		System.out.println(track_MD.values());
		System.out.println(track_MD.get(song_id).getArtist_name());
		
		
		String artist_id = track_MD.get(song_id).getArtist_id();
		TermBased_Recommender tbR = new TermBased_Recommender(artist_term_path);
		tbR.getArtistIndex(artist_id);
		System.out.println(tbR.getArtistIndex(artist_id));
		tbR.getTermIndex("acid jazz");
		System.out.println(tbR.getTermIndex("acid jazz"));
		System.out.println("LastFM Similarity:");
		
		
		String track_id = track_MD.get(song_id).getTrack_id();
		System.out.println("track_id: "+track_id);
		lFM_similarity lFMs = new lFM_similarity(lFM_sim_path);
		
		System.out.println("getTrackIndex: "+lFMs.getTrackIndex(track_id));
		
		System.out.println("getReverseTrackIndex: "+lFMs.getReverseTrackIndex(track_id));
		
		
		lFM_tag lFMtag = new lFM_tag(lFM_tags_path);
		
		lFMtag.getReverseTagIndex(track_id);
		System.out.println("lastFM Tag: ");
//		System.out.println("ReverseTagIndex: "+lFMtag.getReverseTagIndex(track_id)); // NO output for this track!
		System.out.println("ReverseTagIndex: "+lFMtag.getReverseTagIndex("TRCCKNV128F149573B"));
		
		System.out.println("Artist similarity: ");
		ArtistSimilarity_Recommender ASrecommender = new ArtistSimilarity_Recommender(artist_similarity_path);
		System.out.println("Similar artists: "+ASrecommender.getSimilarityIndex(artist_id));
		
		
	}

}
