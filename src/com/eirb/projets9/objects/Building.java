package com.eirb.projets9.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Building implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3797581091046600968L;
	private int major;
	private String name;
	private String link;
	private ArrayList<Floor> list;
	
	public Building(int major, String name, String link, ArrayList<Floor> list) {
		super();
		this.major = major;
		this.name = name;
		this.link = link;
		this.list = list;
	}
	
	public Building() {
		super();
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public ArrayList<Floor> getList() {
		return list;
	}

	public void setList(ArrayList<Floor> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Building [major=" + major + ", name=" + name + ", link=" + link
				+ ", list=" + list + "]";
	}
	
	
	
	
	

}
