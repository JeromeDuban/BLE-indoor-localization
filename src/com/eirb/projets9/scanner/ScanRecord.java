package com.eirb.projets9.scanner;

public class ScanRecord {

	private double distance;
	private int rssi;
	private long timestamp;
	
	public ScanRecord(double distance, int rssi, long timestamp) {
		super();
		this.distance = distance;
		this.rssi = rssi;
		this.timestamp = timestamp;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ScanRecord [distance=" + distance + ", rssi=" + rssi
				+ ", timestamp=" + timestamp + "]";
	}
	
	
	
}
