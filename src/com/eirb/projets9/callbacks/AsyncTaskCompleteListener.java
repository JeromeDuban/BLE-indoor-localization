package com.eirb.projets9.callbacks;

/** 
 * Interface servant de callback pour les requetes effectuées en AsyncTask
 */

public interface AsyncTaskCompleteListener<T> {
	public void onTaskComplete(T result, int number);
}
