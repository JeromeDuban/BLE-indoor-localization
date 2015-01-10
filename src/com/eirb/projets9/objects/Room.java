package com.eirb.projets9.objects;

import java.io.Serializable;

public class Room implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3916669604411843072L;
	private int id;
	private int dom_id;
	private String name;
	
	public Room(int id, int dom_id, String name) {
		super();
		this.id = id;
		this.dom_id = dom_id;
		this.name = name;
	}

	public Room() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDom_id() {
		return dom_id;
	}

	public void setDom_id(int dom_id) {
		this.dom_id = dom_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", dom_id=" + dom_id + ", name=" + name + "]";
	}

	
	

}
