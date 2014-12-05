package com.eirb.projets9.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Conference implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1277639833650257412L;
	private int id;
	private String address;
	private String title;
	private String startDay;
	private String endDay;
	private String major;
	private long createdAt;
	private long updatedAt;
	private ArrayList<Track> list;
	
	
	public Conference(int id, String address, String title, String startDay,
			String endDay, String major, long createdAt, long updatedAt,
			ArrayList<Track> list) {
		super();
		this.list = new ArrayList<Track>();
		this.id = id;
		this.address = address;
		this.title = title;
		this.startDay = startDay;
		this.endDay = endDay;
		this.major = major;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.list = list;
	}
	
	public Conference() {
		super();
		this.list = new ArrayList<Track>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public ArrayList<Track> getList() {
		return list;
	}

	public void setList(ArrayList<Track> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Conference [id=" + id + ", address=" + address + ", title="
				+ title + ", startDay=" + startDay + ", endDay=" + endDay
				+ ", major=" + major + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", list=" + list + "]";
	}
	
	
	
	
	

}
