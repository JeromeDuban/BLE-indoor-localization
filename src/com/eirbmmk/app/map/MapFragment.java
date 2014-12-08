package com.eirbmmk.app.map;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eirb.projets9.R;
import com.eirbmmk.app.BaseFragment;
import com.eirbmmk.app.MainActivityController;

public class MapFragment extends BaseFragment implements View.OnClickListener{


	
    /* -------------------- Attributes -------------------- */
    MapView map = null;

    /**
     * The controller part of the MVC Map pattern
     */
    private MapController mController;

    private static Context mContext;

    private LinearLayout mLinearLayoutView;

    /* -------------------- Constructors -------------------- */


    /**
     * Used by the Operating System when it killed the fragment to free memory
     * and need to recreate it.
     *
     * Never use this constructor directly !
     *
     */
    public MapFragment()
    {
    }


    /**
     * Constructor of the MapFragment class
     *
     * @param container the mainActivity controller which controls the Activity containing all the fragments
     * @param model the model including Map information.
     */
    public MapFragment(MainActivityController container, MapModel model)
    {
        mController = new MapController(container, model, this);
        super.setBaseController(mController);
    }

    /**
     * public method to get the context from the fragment
     * @return the application context
     */
    public static Context getFragmentContext() {
        return mContext;
    }

    /* -------------------- Methods -------------------- */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If the fragment is in an illegal state, don't continue, wait the recreation of it.
        if (super.onCreateView(inflater, container, savedInstanceState) == null)
            return null;

        mContext = container.getContext();
        System.out.println(mController);
//        mController.setContext(mContext);
        mRootView = inflater.inflate(R.layout.fragment_map, container, false);
        mLinearLayoutView = (LinearLayout) mRootView.findViewById(R.id.map_linear_layout);
//        if(!mController.isNetworkConnected())
//            mRootView = inflater.inflate(R.layout.no_internet_connection,container, false);
//        else
//            mController.getData();
        displayData();
        return mRootView;
    }

    /**
     * public method to display data from SVG and JSON
     */
    public void displayData(){
        try {
            map = new MapView(mRootView.getContext(), null, mController.isLogged());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button button1, button2, button3, button4, button5, setOriginalScale;
        button1 = (Button) mRootView.findViewById(R.id.button1);
        button2 = (Button) mRootView.findViewById(R.id.button2);
        button3 = (Button) mRootView.findViewById(R.id.button3);
        button4 = (Button) mRootView.findViewById(R.id.button4);
        button5 = (Button) mRootView.findViewById(R.id.button5);
        setOriginalScale = (Button) mRootView.findViewById(R.id.setOriginalScaleButton);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        setOriginalScale.setOnClickListener(this);


        mLinearLayoutView.addView(map);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                map.setStorey(-1,mContext);
                break;

            case R.id.button2:
                map.setStorey(0,mContext);
                break;

            case R.id.button3:
                map.setStorey(1,mContext);
                break;

            case R.id.button4:
                map.setStorey(2,mContext);
                break;

            case R.id.button5:
                map.setStorey(3,mContext);
                break;

            case R.id.setOriginalScaleButton:
            map.setOriginalScale();
            break;
        }
    }


}
