package com.eirb.projets9.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable{

	private static final long serialVersionUID = 2929300051735046818L;
	private int id;
	private String name;
	
	private ArrayList<Coordinate> list;
	
	public Room() {
		super();
		this.list = new ArrayList<Coordinate>();
	}

	public Room(int id, String name, ArrayList<Coordinate> list) {
		super();
		this.id = id;
		this.name = name;
		this.list = list;
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

	
	public ArrayList<Coordinate> getList() {
		return list;
	}

	
	public void setList(ArrayList<Coordinate> list) {
		this.list = list;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", list=" + list + "]";
	}

	
	


}
