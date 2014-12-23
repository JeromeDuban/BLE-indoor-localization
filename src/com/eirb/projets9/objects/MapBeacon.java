package com.eirb.projets9.objects;

public class MapBeacon {
	
	private String uuid;
	private String major;
	private String minor;
	private int floorId;
	private int roomId;
	private Coordinate coordinate;
	
	public MapBeacon() {
		super();
	}
	
	public MapBeacon(String uuid, String major, String minor, int floorId,
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

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
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
