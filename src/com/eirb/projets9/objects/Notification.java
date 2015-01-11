package com.eirb.projets9.objects;

import java.io.Serializable;

public class Notification implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2919283393723908181L;
	private long timestamp;
	private Talk talk;
	private String roomName;
	
	public Notification() {
		super();
	}

	public Notification(long timestamp, Talk talk, String roomName) {
		super();
		this.timestamp = timestamp;
		this.talk = talk;
		this.roomName = roomName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	@Override
	public String toString() {
		return "Notification [timestamp=" + timestamp + ", talk=" + talk
				+ ", roomName=" + roomName + "]";
	}
	
	
	

}
