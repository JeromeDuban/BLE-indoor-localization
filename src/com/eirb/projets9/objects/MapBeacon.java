package com.eirb.projets9.objects;

public class MapBeacon {
	
	private String uuid;
	private int major;
	private int minor;
	private int floorId;
	private int roomId;
	private Coordinate coordinate;
	
	public MapBeacon() {
		super();
	}
	
	public MapBeacon(String uuid, int major, int minor, int floorId,
			int roomId, Coordinate coordinate) {
		super();
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.floorId = floorId;
		this.roomId = roomId;
		this.coordinate = coordinate;
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

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

}
