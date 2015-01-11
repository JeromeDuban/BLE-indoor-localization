package com.eirb.projets9.objects;

import java.io.Serializable;

public class Beacon implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4179817088073784012L;
	private int id;
	private String uuid;
	private int major;
	private int minor;
	private int floor_id;
	private int room_id;
	private Coordinate coordinates;
	
	public Beacon(int id, String uuid, int major, int minor, int floor_id,
			int room_id, Coordinate coordinates) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.floor_id = floor_id;
		this.room_id = room_id;
		this.coordinates = coordinates;
	}
	public Beacon() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public int getMinor() {
		return minor;
	}
	public void setMinor(int minor) {
		this.minor = minor;
	}
	public int getFloor_id() {
		return floor_id;
	}
	public void setFloor_id(int floor_id) {
		this.floor_id = floor_id;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public Coordinate getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}
	@Override
	public String toString() {
		return "Beacon [id=" + id + ", uuid=" + uuid + ", major=" + major
				+ ", minor=" + minor + ", floor_id=" + floor_id + ", room_id="
				+ room_id + ", coordinates=" + coordinates + "]";
	}
	
	

}
