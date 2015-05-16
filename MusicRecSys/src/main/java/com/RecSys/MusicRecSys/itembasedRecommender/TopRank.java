package com.RecSys.MusicRecSys.itembasedRecommender;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TopRank implements Comparable<TopRank>{
private String id;
private Float similarity;

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}
public float getSimilarity() {
	return similarity;
}
public void setSimilarity(float similarity) {
	this.similarity = similarity;
}

public int compareTo(TopRank topR) {
return this.similarity.compareTo(topR.getSimilarity());
}




}
