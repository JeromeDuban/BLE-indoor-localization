package com.eirb.projets9.objects;


public class Talk {

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
	
	
	
	
	
}
