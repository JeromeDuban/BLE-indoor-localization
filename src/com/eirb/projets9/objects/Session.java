package com.eirb.projets9.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2929300051735046818L;
	private int id;
	private long startTs;
	private long endTs;
	private int room_id;
	private ArrayList<Talk> list;
	
	public Session(int id, long startTs, long endTs, int room_id,
			ArrayList<Talk> list) {
		super();
		this.id = id;
		this.startTs = startTs;
		this.endTs = endTs;
		this.room_id = room_id;
		this.list = list;
	}
	
	public Session() {
		super();
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

	public int getRoom_id() {
		return room_id;
	}

	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}

	public ArrayList<Talk> getList() {
		return list;
	}

	public void setList(ArrayList<Talk> list) {
		this.list = list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", startTs=" + startTs + ", endTs="
				+ endTs + ", room_id=" + room_id + ", list=" + list + "]";
	}
	
	
	
	
	
	

}
