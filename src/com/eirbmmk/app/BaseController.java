package com.eirbmmk.app;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
    Base class for Functionality controllers. It declares asynchronous tasks management, MainActivityController
    reference to interact with the Activity MCV and Network state test method. All Functionality controller must implement it.
 */
public abstract class BaseController {

    /* -------------------- Attributes -------------------- */

    /**
     * Main Activity Controller to interact with the Activity container
     */
    protected MainActivityController mMainActivityController;

    /**
     * Asynchronous running tasks
     */
    protected ArrayList<AsyncTask> mAsyncRunningTasks = new ArrayList<AsyncTask>();


    /**
     * Constructor of BaseController class
     * @param controller the mainActivityController
     */
    public BaseController(MainActivityController controller)
    {
        mMainActivityController = controller;
    }

    /* -------------------- Public Methods -------------------- */


    /**
     * Cancel all the asynchronous running tasks.
     */
    public void stopAsyncRunningTasks()
    {
        for(AsyncTask task : mAsyncRunningTasks)
        {
            task.cancel(true);
        }
    }


    /**
     * Tests if the network is connected and if internet is accessible or not.
     *
     * @return true if connected, false otherwise
     */
//    public boolean isNetworkConnected()
//    {
//        return mMainActivityController.isNetworkConnected();
//    }
}
