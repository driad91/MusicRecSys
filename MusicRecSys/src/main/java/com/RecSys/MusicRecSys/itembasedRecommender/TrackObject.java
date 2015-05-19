package com.RecSys.MusicRecSys.itembasedRecommender;


public class TrackObject {
	private String track_id;
	private String song_id;
	private String release;
	private String artist_id;
	private String artist_mbid;
	private String artist_name;
	private float duration;
	private float artist_familiarity;
	private float artist_hotttness;
	private int year;
	private int track_7digitalid;
	private String title;
	
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TrackObject(String artist_id) {
		super();
		this.artist_id = artist_id;
	}
	
	public TrackObject(String track_id, String song_id, String release,
			String artist_id, String artist_mbid, String artist_name,
			float duration, float artist_familiarity, float artist_hotttness,
			int year, int track_7digitalid, String title) {
		super();
		this.track_id = track_id;
		this.song_id = song_id;
		this.release = release;
		this.artist_id = artist_id;
		this.artist_mbid = artist_mbid;
		this.artist_name = artist_name;
		this.duration = duration;
		this.artist_familiarity = artist_familiarity;
		this.artist_hotttness = artist_hotttness;
		this.year = year;
		this.track_7digitalid = track_7digitalid;
		this.title = title;
	}
	public String getTrack_id() {
		return track_id;
	}
	public void setTrack_id(String track_id) {
		this.track_id = track_id;
	}
	public String getSong_id() {
		return song_id;
	}
	public void setSong_id(String song_id) {
		this.song_id = song_id;
	}
	public String getRelease() {
		return release;
	}
	public void setRelease(String release) {
		this.release = release;
	}
	public String getArtist_id() {
		return artist_id;
	}
	public void setArtist_id(String artist_id) {
		this.artist_id = artist_id;
	}
	public String getArtist_mbid() {
		return artist_mbid;
	}
	public void setArtist_mbid(String artist_mbid) {
		this.artist_mbid = artist_mbid;
	}
	public String getArtist_name() {
		return artist_name;
	}
	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	public float getArtist_familiarity() {
		return artist_familiarity;
	}
	public void setArtist_familiarity(float artist_familiarity) {
		this.artist_familiarity = artist_familiarity;
	}
	public float getArtist_hotttness() {
		return artist_hotttness;
	}
	public void setArtist_hotttness(float artist_hotttness) {
		this.artist_hotttness = artist_hotttness;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTrack_7digitalid() {
		return track_7digitalid;
	}
	public void setTrack_7digitalid(int track_7digitalid) {
		this.track_7digitalid = track_7digitalid;
	}
	
	
	
	
	
	
}
