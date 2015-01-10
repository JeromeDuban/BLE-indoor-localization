package com.eirb.projets9.objects;

import java.util.Comparator;

public class PlanningElement implements Comparable<PlanningElement>{
	
	private Talk talk;
	private int id;
	
	public PlanningElement(Talk talk, int id) {
		super();
		this.talk = talk;
		this.id = id;
	}
	
	public PlanningElement() {
		super();
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PlanningElement [talk=" + talk + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((talk == null) ? 0 : talk.hashCode());
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
		PlanningElement other = (PlanningElement) obj;
		if (id != other.id)
			return false;
		if (talk == null) {
			if (other.talk != null)
				return false;
		} else if (!talk.equals(other.talk))
			return false;
		return true;
	}



	@Override
	public int compareTo(PlanningElement another) {
		final int BEFORE = -1;
	    final int AFTER = 1;
	    
	    if(talk.getStartTs() <= another.getTalk().getStartTs())
	    	return BEFORE;
	    else
	    	return AFTER;
	}
	
	
	
	
	
	

}
