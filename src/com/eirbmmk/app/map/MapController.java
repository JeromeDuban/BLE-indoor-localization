package com.eirbmmk.app.map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.eirbmmk.app.BaseController;
import com.eirbmmk.app.MainActivityController;
import com.eirbmmk.app.Screen;

/**
 * This class correspond to the controller part of the MVC design pattern for Map
 * functionality of the application.
 */
public class MapController extends BaseController {

    /* -------------------- Attributes -------------------- */


    /**
    * Model part of the MVC design pattern
    */
    private MapModel mModel;

    /**
    * View part of the MVC design pattern
    */
    private MapFragment mFragment;


    private Context mContext;
    public static String userLogin;


      /* -------------------- Constructors -------------------- */


    /**
     * Constructor of MapController class.
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model for the Map functionality
     * @param fragment the view of the Map functionality
     */
    public MapController(MainActivityController container, MapModel model, MapFragment fragment)
    {
        super(container);
        mModel = model;
        mFragment = fragment;
    }


    /* -------------------- Methods -------------------- */
    /**
     * public method to get the data in background
     * while a spinner is shown to the user
     */
    public void getData(){
        AsyncTask<Void, Void, Boolean> mapRequest = new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                mFragment.showLoadingSpinner();
            }

            @Override
            protected Boolean doInBackground(Void... Params) {
                userLogin = mMainActivityController.getUserLogin();
                if(!isCancelled() && userLogin != null) {
                    mModel.fetchData(true);
                }
                return true;
            }

            @Override
            protected void onPostExecute(Boolean ok) {
                if (ok.booleanValue()) {
                    mFragment.displayData();
                } else {
                    Log.d("Map","Error data");
                }
//                mFragment.hideLoadingSpinner();

                // remove the task to running tasks list
                mAsyncRunningTasks.remove(this);
            }
        };

        // add the task to running tasks list
        mAsyncRunningTasks.add(mapRequest);

        // start the async task
        mapRequest.execute();
    }

    /**
     * public method to get the context from the fragment
     * @return the application context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * public method to check the status of the user
     * @return the logged user status
     */
    public static boolean isLogged() {
        if(userLogin == null)
            return false;
        else
            return true;
    }

    /**
     * public method to set the context
     * @param context context
     */
    public void setContext(Context context) {
        this.mContext = context;
        mModel.setContext(context);
    }
}
