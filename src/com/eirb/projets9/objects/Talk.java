package com.eirb.projets9.objects;

import java.io.Serializable;

import com.eirb.projets9.scanner.BeaconRecord;


public class Talk implements Serializable, Comparable<Talk>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4653665694483820698L;
	private int id;
	private String title;
	private long endTs;
	private long startTs;
	private String speaker;
	private String confAbstract;
	private String body;
	
	public Talk(int id, String title, long endTs, long startTs,
			String speaker, String confAbstract, String body) {
		super();
		this.id = id;
		this.title = title;
		this.endTs = endTs;
		this.startTs = startTs;
		this.speaker = speaker;
		this.confAbstract = confAbstract;
		this.body = body;
	}
	
	public Talk() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getEndTs() {
		return endTs;
	}

	public void setEndTs(long endTs) {
		this.endTs = endTs;
	}

	public long getStartTs() {
		return startTs;
	}

	public void setStartTs(long startTs) {
		this.startTs = startTs;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public String getConfAbstract() {
		return confAbstract;
	}

	public void setConfAbstract(String confAbstract) {
		this.confAbstract = confAbstract;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Talk [id=" + id + ", title=" + title + ", endTs=" + endTs
				+ ", startTs=" + startTs + ", spearker=" + speaker
				+ ", confAbstract=" + confAbstract + ", body=" + body + "]";
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result
				+ ((confAbstract == null) ? 0 : confAbstract.hashCode());
		result = prime * result + (int) (endTs ^ (endTs >>> 32));
		result = prime * result + id;
		result = prime * result + ((speaker == null) ? 0 : speaker.hashCode());
		result = prime * result + (int) (startTs ^ (startTs >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talk other = (Talk) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (confAbstract == null) {
			if (other.confAbstract != null)
				return false;
		} else if (!confAbstract.equals(other.confAbstract))
			return false;
		if (endTs != other.endTs)
			return false;
		if (id != other.id)
			return false;
		if (speaker == null) {
			if (other.speaker != null)
				return false;
		} else if (!speaker.equals(other.speaker))
			return false;
		if (startTs != other.startTs)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(Talk another) {
		final int BEFORE = -1;
	    final int AFTER = 1;
	    
	    if(startTs <= another.startTs)
	    	return BEFORE;
	    else
	    	return AFTER;
	    
	}
	
	
	
	
	
}
