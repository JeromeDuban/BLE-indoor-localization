package com.eirb.projets9.objects;

import java.util.ArrayList;

public class Session {
	
	private int id;
	private long startTs;
	private long endTs;
	private ArrayList<Talk> list;
	
	public Session(int id, long startTs, long endTs, ArrayList<Talk> list) {
		super();
		this.list = new ArrayList<Talk>();
		this.id = id;
		this.startTs = startTs;
		this.endTs = endTs;
		this.list = list;
	}
	
	public Session() {
		super();
		this.list = new ArrayList<Talk>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getStartTs() {
		return startTs;
	}

	public void setStartTs(long startTs) {
		this.startTs = startTs;
	}

	public long getEndTs() {
		return endTs;
	}

	public void setEndTs(long endTs) {
		this.endTs = endTs;
	}

	public ArrayList<Talk> getList() {
		return list;
	}

	public void setList(ArrayList<Talk> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", startTs=" + startTs + ", endTs="
				+ endTs + ", list=" + list + "]";
	}
	
	
	
	

}
