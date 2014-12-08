package com.eirbmmk.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
    Base class for Functionality fragments.
 */
public abstract class BaseFragment extends Fragment {

    /* -------------------- Attributes -------------------- */

    /**
     * Waiting spinner
     */
    protected ProgressBar mSpinner;

    /**
     * The rootView of the fragment layout
     */
    protected View mRootView;

    /**
     * Reference of BaseController to stop running tasks at fragment destruction
     */
    private BaseController mBaseController;


    /* -------------------- Constructors -------------------- */

    /* -------------------- Methods -------------------- */

		/**
		 *
		 *
		 *@param inflater
		 *@param container
		 *@param savedInstanceState
		 *
		 *@return
		 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // the OS destroyed the old fragment and instantiates
            // a new one using the default constructor. This new fragment has an illegal state
            // (no controller and mainActivityController reference).
            // So recreate all the MVC pattern for the fragment.
            // Warning : If the application was completely killed by the OS, the current Fragment
            // will be the home fragment and not this fragment.

//            MainActivity mainAc = (MainActivity) getActivity();
//            mainAc.recreateCurrentMVCFragment();
            return null;
        }

//        mSpinner = (ProgressBar) container.findViewById(R.id.loading_spinner);
        hideLoadingSpinner();

        mRootView = container; // just to initialize it. Normally it will be re-affected

        return mRootView; // just to return something
    }


    @Override
    public void onDestroy() {
        if (mBaseController != null) // if mBaseController was not set using setBaseController in the Fragment constuctor
            mBaseController.stopAsyncRunningTasks();

        super.onDestroy();

    }

    /**
     * Shows the loading spinner.
     */
    public void showLoadingSpinner()
    {
        mSpinner.setVisibility(View.VISIBLE);
    }


    /**
     * Hides the Loading spinner.
     */
    public void hideLoadingSpinner()
    {
//        mSpinner.setVisibility(View.GONE);
    }

    /**
     * Displays the error message in an alert box
     *
     * @param message the error message to display
     */
    public void displayErrorMessage(String message)
    {
        // Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Erreur")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });


        // Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Sets the BaseController reference
     * @param controller the baseController to set
     */
    public void setBaseController(BaseController controller)
    {
        mBaseController = controller;
    }
}
