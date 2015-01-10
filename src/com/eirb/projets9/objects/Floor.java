package com.eirb.projets9.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Floor implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8274064440886093157L;
	private int id;
	private String name;
	private ArrayList<Room> list;
	
	public Floor(int id, String name, ArrayList<Room> list) {
		super();
		this.id = id;
		this.name = name;
		this.list = list;
	}
	
	public Floor() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Room> getList() {
		return list;
	}

	public void setList(ArrayList<Room> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Floor [id=" + id + ", name=" + name + ", list=" + list + "]";
	}
	
	
	

}
