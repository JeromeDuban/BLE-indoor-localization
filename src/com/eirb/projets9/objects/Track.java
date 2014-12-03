package com.eirb.projets9.objects;

import java.util.ArrayList;

public class Track {
	
	public int id;
	public String title;
	public ArrayList<Session> list;
	
	public Track(int id, String title, ArrayList<Session> list) {
		super();
		
		this.list = new ArrayList<Session>();
		this.id = id;
		this.title = title;
		this.list = list;
	}
	
	public Track() {
		super();
		this.list = new ArrayList<Session>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Session> getList() {
		return list;
	}

	public void setList(ArrayList<Session> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", title=" + title + ", list=" + list + "]";
	}
	
	

}
