package com.eirb.projets9.scanner;

import java.util.ArrayList;

public class BeaconRecord implements Comparable<BeaconRecord>{
	
	private String uuid;
	private String major;
	private String minor;
	private ArrayList<ScanRecord> list;
	private boolean notified = false;
	
	
	public BeaconRecord() {
		super();
		list = new ArrayList<ScanRecord>();
	}


	public BeaconRecord(String uuid, String major, String minor,
			ArrayList<ScanRecord> list) {
		super();
		this.list = new ArrayList<ScanRecord>();
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		this.list = list;
	}
	
	
	public BeaconRecord(String uuid, String major, String minor,
			ScanRecord sr) {
		super();
		this.list = new ArrayList<ScanRecord>();
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
		list.add(sr);
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


	public ArrayList<ScanRecord> getList() {
		return list;
	}


	public void setList(ArrayList<ScanRecord> list) {
		this.list = list;
	}
	
	public void addToList(ScanRecord scanRecord){
		list.add(scanRecord);
	}
	
	public ScanRecord getFromList(int index){
		return list.get(index);
	}
	
	public boolean isNotified() {
		return notified;
	}


	public void setNotified(boolean notified) {
		this.notified = notified;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
//		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + ((minor == null) ? 0 : minor.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		BeaconRecord other = (BeaconRecord) obj;
//		if (list == null) {
//			if (other.list != null)
//				return false;
//		} else if (!list.equals(other.list))
//			return false;
		if (major == null) {
			if (other.major != null)
				return false;
		} else if (!major.equals(other.major))
			return false;
		if (minor == null) {
			if (other.minor != null)
				return false;
		} else if (!minor.equals(other.minor))
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "BeaconRecord [uuid=" + uuid + ", major=" + major + ", minor="
				+ minor + ", list=" + list + ", notified=" + notified + "]";
	}


	@Override
	public int compareTo(BeaconRecord another) {
		final int BEFORE = -1;
	    final int EQUAL = 0;
	    final int AFTER = 1;
		
		if (list.get(list.size()-1).getTimestamp() > another.getList().get(another.getList().size()-1).getTimestamp()){
			return BEFORE;
		}
		else if (list.get(list.size()-1).getTimestamp() == another.getList().get(another.getList().size()-1).getTimestamp()){
			
			if(list.get(list.size()-1).getDistance() <= another.getList().get(another.getList().size()-1).getDistance()){
				return BEFORE;
			}
			else{
				return AFTER;
			}
		}
		else{
			return AFTER;
		}
	}
}
