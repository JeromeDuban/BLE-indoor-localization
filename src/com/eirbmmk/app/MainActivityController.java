package com.eirbmmk.app;

import android.app.Fragment;
import java.util.ArrayList;

import com.eirb.projets9.MainActivity;

/**
 * MVC Controller part of the main Activity of the application.
 * It manages interaction with the Activity (like asking for displaying menu, etc.) and access to
 * the MainActivityModel and the global information of the application.
 */
public class MainActivityController {


    /* -------------------- Attributes -------------------- */

    /**
     * The View part of the Activity MVC pattern.
     */
    private MainActivity mView;

    /**
     * The Model part of the Activity MVC pattern.
     */
    private MainActivityModel mModel;

    /**
     * The current screen type displayed
     * Warning : If the application was completely killed by the OS, the current fragment will always
     * be the "home" fragment, at this time, the News fragment.
     *
     */
    private Screen mCurrentScreen;


    /* -------------------- Constructors -------------------- */


    /**
     * Constructor of the MainActivityController class.
     *
     * @param model the model related to the MVC
     * @param view the view related to the MVC
     */
    public MainActivityController(MainActivityModel model, MainActivity view)
    {
        mModel = model;
        mView = view;
        mCurrentScreen = null; // no screen displayed yet
    }


    /* -------------------- Methods -------------------- */


    /**
     * Get the login of the authenticated user from the Model.
     * If the user is not authenticated, return null;
     *
     * @return return the login of the authenticated user or null if the user is not authenticated.
     */
    public String getUserLogin()
    {
        return mModel.getLogin();
    }

    /**
     * Save the user login in the Mode.
     *
     * @param login the login to save
     */
    public void saveUserLogin(String login)
    {
        mModel.setLogin(login);
    }

    /**
     * Logout the current user from the application.
     * It deletes the stored login from the model.
     */
    public void logoutUser()
    {
        mModel.setLogin(null);
    }


    /**
     * Sets the application UI to authenticated mode by changing left menu items and coming back
     * to the News 'home' screen.
     */
//    public void setAuthenticatedMode()
//    {
//        mView.setLeftMenuItems(true);
//
//        //createMVCFragment(Screen.NEWS);
//    }

    /**
     * Sets the application UI to non authenticated mode by changing left menu items and coming back
     * to the News 'home' screen.
     */
//    public void setNonAuthenticatedMode()
//    {
//        mView.setLeftMenuItems(false);
//
//        //createMVCFragment(Screen.NEWS);
//    }

    /**
     * Returns the current displayed screen
			 @return the current displayed screen
     */
    public Screen getCurrentScreen()
    {
        return mCurrentScreen;
    }

    /**
     * Indicates if the user is authenticated or not with his account.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public boolean isUserAuthenticated()
    {
        return mModel.getLogin() != null;
    }

    /**
     * Create the MVC pattern corresponding to the screen to display.
     * It's instantiate the Controller, Model and View (Fragment) corresponding to the screen type given
     * in parameter.
     *
     * If the instantiation succeeds,the new View is displayed, otherwise an error message.
     *
     * @param screen the screen type to display
     */
    public void createMVCFragment(Screen screen)
    {
        Class fragmentClass = null;
        //Class controllerClass = null;
        Class modelClass = null;
        Fragment fragment = null;
        String prefixClassName = screen.getPackage() + "." + screen.getClassPrefix();

//        if (mCurrentScreen == screen)  //don't reload the same screen
//        {
//            // close the left menu
//            mView.closeLeftMenu();
//            return;
//        }

        try {
            fragmentClass = Class.forName(prefixClassName + "Fragment");
            //controllerClass = Class.forName(prefixClassName + "Controller");
            modelClass = Class.forName(prefixClassName + "Model");
        } catch(ClassNotFoundException e){
            android.util.Log.i("ERROR", "Erreur lors de la recuperation des classes du MVC !");
            e.printStackTrace();
//            mView.displayLoadingFragmentError(screen);
            return;
        }

        try {
          fragment = (Fragment) fragmentClass.getConstructor(MainActivityController.class, modelClass).newInstance(this, modelClass.newInstance());
        } catch (Exception e)
        {
            android.util.Log.i("ERROR", "Erreur lors de l'instanciation des classes du MVC !");
            e.printStackTrace();
//            mView.displayLoadingFragmentError(screen);
            return;
        }

        mCurrentScreen = screen;

        // change the title displayed at the top of the activity
//        mView.setActionBarTitle(screen.getTitle());

        // display the new fragment
//        mView.displayMainFragment(fragment);

        // close the left menu
//        mView.closeLeftMenu();
    }

    /**
     * Recreates all the MVC pattern for the current displayed fragment.
     * It is used to resolve the Operating System fragment destruction when the application
     * is inactive. See also the onCreateView of each fragment.
     *
     * Warning : If the application was completely killed by the OS, the current fragment will always
     * be the "home" fragment, at this time, the News fragment.
     *
     */
    public void recreateCurrentMVCFragment()
    {
        this.createMVCFragment(mCurrentScreen);
    }

    /**
     *
     * This method asks the Vue to display a secondary fragment.
     * A secondary fragment is an internal fragment of a package / functionality which is not
     * automatically displayed when the functionality is loads (using the left menu of the application)
     * and permits to display complementary content for the suitable functionality.
     * For instance, the news main fragment is the list of news and the secondary one is another one
     * which displays a selected news in detail.
     *
     * When the back button is pressed on a secondary fragment, it comes back to the main fragment
     * of the functionality.
     *
     * @param fragment the secondary fragment to display
     */
    public void displaySecondaryFragment(Fragment fragment)
    {
//        mView.displaySecondaryFragment(fragment);
    }

    /**
     * Saves the left menu displayed items given in parameter in the MainActivityModel.
     *
     * @param leftMenuItems the left menu items list to save in the model
     */
    public void saveLeftMenuItems(ArrayList<Screen> leftMenuItems)
    {
        mModel.setLeftMenuItems(leftMenuItems);
    }

    /**
     * Returns the Screen type associated to the item number itemPosition of the left menu list.
     *
     * @param itemPosition item position in the left menu list
     * @return le screen type
     */
    public Screen getLeftItemScreen(int itemPosition)
    {
        return mModel.getCorrespondingLeftItemScreen(itemPosition);
    }

    /**
     * Save the token given in parameter and got during user authentication in the model.
     *
     * @param token the token to save
     */
//    public void saveToken(Token token)
//    {
//        mModel.setAccessToken(token.getAccessToken());
//        mModel.setRefreshToken(token.getRefreshToken());
//    }

    /**
     * Tests if the network is connected and if internet is accessible or not.
     *
     * @return true if connected, false otherwise
     */
//    public boolean isNetworkConnected()
//    {
//        return mView.isNetworkConnected();
//    }
}
