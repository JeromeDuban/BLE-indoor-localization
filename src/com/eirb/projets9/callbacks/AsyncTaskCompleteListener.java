package com.eirb.projets9.callbacks;

public interface AsyncTaskCompleteListener<T> {
	public void onTaskComplete(T result, int number);
}
