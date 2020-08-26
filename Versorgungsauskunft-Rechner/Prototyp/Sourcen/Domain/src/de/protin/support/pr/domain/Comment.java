package de.protin.support.pr.domain;

import java.util.HashSet;
import java.util.Set;

public class Comment {

	private String comment;
	private Set<String> weblinks;  //Gesetzestext, Rechtsprechung
	
	public Comment(String comment) {
		this.comment = comment;
		this.weblinks = new HashSet<String>();
	}
	
	public Comment(String comment, Set<String> weblinks) {
		this.comment = comment;
		this.weblinks = new HashSet<String>();
	}
	
	
	public void addWebLink(String link) {
		this.weblinks.add(link);
	}
	
	public void removeWebLink(String link) {
		this.weblinks.remove(link);
	}
	
	public void resetWebLinks() {
		this.weblinks.clear();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
}
