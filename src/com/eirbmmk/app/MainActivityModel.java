package com.eirbmmk.app;


import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Model of the Activity containing all the information the different functionality models need.
 * It includes more or less the "global" information.
 */
public class MainActivityModel {

    /* -------------------- Attributes -------------------- */

    /**
     * Preferences table where all persistent data are stored.
     */
    private SharedPreferences mSharedPreferences;

    /**
     * List of the different left menu items displayed
     */
    private ArrayList<Screen> mLeftMenuItems;


    /* -------------------- Constructors -------------------- */


    /**
     *
     * Constructor of the MainActivityModel class.
     *
     * @param preferences the SharedPreferences table to use
     */
    public MainActivityModel(SharedPreferences preferences)
    {
        mSharedPreferences = preferences;
    }


    /* -------------------- Methods -------------------- */




    /**
     * Get the login of the connected user.
     *
     * @return return the login of the connected user or null if the user is not connected.
     */
    public String getLogin()
    {
        return mSharedPreferences.getString("login", null);
    }

    /**
     * Save the login given in parameter in the sharedPreferences
     *
     * @param login the login to save
     */
    public void setLogin(String login)
    {
        mSharedPreferences.edit().putString("login", login).commit();
    }

    /**
     * Get the access token to use for eirb.fr web-services.
     *
     * @return the access token or null if the is no access token already saved
     */
    public String getAccessToken()
    {
        return mSharedPreferences.getString("accessToken", null);
    }

    /**
     * Save the access token given in parameter in the sharedPreferences
     *
     * @param accessToken the access token got during the connection
     */
    public void setAccessToken(String accessToken)
    {
        mSharedPreferences.edit().putString("accessToken", accessToken).commit();
    }

    /**
     * Get the refresh token to use for eirb.fr web-services.
     *
     * @return the refresh token or null if the is no refresh token already saved
     */
    public String getRefreshToken()
    {
        return mSharedPreferences.getString("refreshToken", null);
    }

    /**
     * Save the refresh token given in parameter in the sharedPreferences
     *
     * @param refreshToken the refresh token got during the connection
     */
    public void setRefreshToken(String refreshToken)
    {
        mSharedPreferences.edit().putString("refreshToken", refreshToken).commit();
    }


    /**
     * Set the left menu displayed items list of the Model
     *
     * @param leftMenuItems the array list of displayed items to set
     */
    public void setLeftMenuItems(ArrayList<Screen> leftMenuItems)
    {
        mLeftMenuItems = leftMenuItems;
    }

    /**
     * Gets the list of left menu displayed items
     *
     * @return the list of the left menu displayed items
     */
    public ArrayList<Screen> getLeftMenuItems()
    {
        return mLeftMenuItems;
    }

    /**
     * Returns the Screen type associated to the itemPostion item of the left menu list.
     *
     * @param itemPosition item position in the left menu list
     * @return the screen type
     */
    public Screen getCorrespondingLeftItemScreen(int itemPosition)
    {
        return mLeftMenuItems.get(itemPosition);
    }
}
