package com.eirb.projets9.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.eirb.projets9.ReferenceApplication;
import com.eirb.projets9.map.objects.Point;
import com.eirb.projets9.map.objects.PolygonRoom;
import com.eirb.projets9.map.objects.RectangleRoom;
import com.eirb.projets9.scanner.BeaconRecord;

/**
 * This class correspond to the custom view creating school's map
 */

public class MapView extends View {

	int svgHeight = 1400;
	int svgWidth = 1100;

	int clickCount = 0;
	long startTime;
	long duration;
	long time;

	static final int MAX_DURATION = 300;

	ArrayList<PolygonRoom> clickablePolygons = MapModel.getClickablePolygons();
	ArrayList<PolygonRoom> nonClickablePolygons = MapModel.getNonClickablePolygons();
	ArrayList<RectangleRoom> clickableRectangles = MapModel.getClickableRectangles();
	ArrayList<RectangleRoom> nonClickableRectangles = MapModel.getNonClickableRectangles();
	PolygonRoom contour = MapModel.getContour();


	/* -------------------- Private classes -------------------- */

	/*
	 * This class enables the custom view's canvas to be pinched to zoom
	 * */
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {

			final float originalScale = mCalculatedScale;
			final float scale = detector.getScaleFactor();
			mScaleFactor = mScaleFactor*detector.getScaleFactor();
			mScaleFactor = Math.max(0.8f, Math.min(mScaleFactor, 1000.0f));
			//Center the zoom
			final float centerX = detector.getFocusX();
			final float centerY = detector.getFocusY();

			float diffX = centerX - mPosX;
			float diffY = centerY - mPosY;

			diffX = diffX * scale - diffX;
			diffY = diffY * scale - diffY;

			mPosX -= diffX;
			mPosY -= diffY;
			invalidate();

			//refresh canvas

			return true;
		}
	}



	/* -------------------- Attributes -------------------- */

	/**
	 * Personnalize design of the drawers
	 */
	private Paint mYellow = new Paint();
	private Paint mBlackStroke = new Paint();
	private Paint mGrey = new Paint();
	private Paint mRed = new Paint();
	private Paint mGreen = new Paint();
	private Paint mBlue = new Paint();
	private Paint mBlueStroke = new Paint();
	/**
	 * TouchEvent management variables
	 */
	//Detection of a movement
	private boolean moving = false;
	private float mOffsetX;
	private float mOffsetY;

	//Calculate movement coordinates
	private static final int INVALID_POINTER_ID = -1;
	private float mPosX;
	private float mPosY = -100.f;
	private float mLastTouchX;
	private float mLastTouchY;
	private int mActivePointerId = INVALID_POINTER_ID;

	//zoom management
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.f;
	private int mCanvasWidth;
	private int mCanvasHeight;
	private float mCalculatedScale = 1.f; //to get the same scale factor on any different screen

	public MapView(Context context, AttributeSet attrs) throws ParserConfigurationException, SAXException, IOException {
		super(context, attrs);
		//at first we show storey 0
		setStorey(0,context);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

	}


	/* -------------------- Custom View Methods -------------------- */

	/**
	 * Draws on the canvas
	 * Modifies its appearance by zooming and translating
	 * Defines paints parameters
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//saves the content to be able to zoom and translate
		canvas.save();
		
		System.out.println("ON DRAW");
		contour = MapModel.getContour();

		mCalculatedScale = Math.min((float)canvas.getWidth()/(float)svgWidth, (float)canvas.getHeight()/(float)svgHeight);
		mCanvasWidth = canvas.getWidth();
		mCanvasHeight = canvas.getHeight();
		//translation
		canvas.translate(mPosX, mPosY);

		//zoom

		canvas.scale(mCalculatedScale *mScaleFactor, mCalculatedScale *mScaleFactor);


		//Paints parameters
		mYellow.setColor(Color.rgb(255, 222, 0));
		mRed.setColor(Color.rgb(255, 0, 0));
		mGreen.setColor(Color.rgb(0,200,83));
		mBlue.setColor(Color.rgb(150,150,255));
		mBlackStroke.setColor(Color.BLACK);
		mBlackStroke.setStyle(Paint.Style.STROKE);
		mBlackStroke.setStrokeJoin(Paint.Join.ROUND);
		mBlackStroke.setStrokeCap(Paint.Cap.ROUND);
		mBlackStroke.setStrokeWidth(3);
		mBlackStroke.setDither(true);
		
		mBlueStroke.setColor(Color.rgb(150,150,255));
		mBlueStroke.setStyle(Paint.Style.STROKE);
		mBlueStroke.setStrokeJoin(Paint.Join.ROUND);
		mBlueStroke.setStrokeCap(Paint.Cap.ROUND);
		mBlueStroke.setStrokeWidth(1);
		mBlueStroke.setDither(true);
		
		mGrey.setColor(Color.rgb(200,200,200));


		//drawings
		if(contour != null)
			canvas.drawPath(contour.getPath(), mGrey);
		else
			Log.d("Map", "Contour problem");

		for(int w = 0; w < nonClickablePolygons.size(); w++)
		{
			canvas.drawPath(nonClickablePolygons.get(w).getPath(), mGrey);
			canvas.drawPath(nonClickablePolygons.get(w).getPath(), mBlackStroke);
		}

		for(int w = 0; w < clickablePolygons.size(); w++)
		{
			if(clickablePolygons.get(w).getState() == 1 ){
				canvas.drawPath(clickablePolygons.get(w).getPath(), mGreen);
				canvas.drawPath(clickablePolygons.get(w).getPath(), mBlackStroke);
			}
			else if(clickablePolygons.get(w).getState() == 0){
				canvas.drawPath(clickablePolygons.get(w).getPath(), mRed);
				canvas.drawPath(clickablePolygons.get(w).getPath(), mBlackStroke);
			}
			else{
				canvas.drawPath(clickablePolygons.get(w).getPath(), mYellow);
				canvas.drawPath(clickablePolygons.get(w).getPath(), mBlackStroke);
			}

		}

		for(int i = 0; i < nonClickableRectangles.size(); i++)
		{
			canvas.drawRect(nonClickableRectangles.get(i).getRect(), mYellow);
			canvas.drawRect(nonClickableRectangles.get(i).getRect(), mBlackStroke);

		}

		for(int i = 0; i < clickableRectangles.size(); i++)
		{
			if(clickableRectangles.get(i).getState() == 1){
				canvas.drawRect(clickableRectangles.get(i).getRect(), mGreen);
				canvas.drawRect(clickableRectangles.get(i).getRect(), mBlackStroke);
			}
			else if(clickableRectangles.get(i).getState() == 0){
				canvas.drawRect(clickableRectangles.get(i).getRect(), mRed);
				canvas.drawRect(clickableRectangles.get(i).getRect(), mBlackStroke);
			}
			else{
				canvas.drawRect(clickableRectangles.get(i).getRect(), mYellow);
				canvas.drawRect(clickableRectangles.get(i).getRect(), mBlackStroke);
			}


		}
		
		// Added ( has to be outside the for loop ;) )
		canvas.drawPath(triangulation(ReferenceApplication.records), mGreen);
		
		// Test to move dot every second
		Date date = new Date();
		double degrees = date.getSeconds()*6d;	// 360 °
		double rad = Math.toRadians(degrees);
		double radius = 50;
		double xOffset = radius*Math.cos(rad);
		double yOffset = radius*Math.sin(rad);

		canvas.drawPath(addDot(650+xOffset,600+yOffset,15), mBlue);
		canvas.drawPath(addDot(650+xOffset,600+yOffset,20), mBlueStroke);


		canvas.restore();

	}




	/**
	 * Manages events on the canvas
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		//zoom detector
		mScaleDetector.onTouchEvent(ev);

		//events management on the canvas
		final int action = ev.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			startTime = System.currentTimeMillis();
			clickCount++;
			final float x = ev.getX();
			final float y = ev.getY();
			mOffsetX = 0f;
			mOffsetY = 0f;
			mLastTouchX = x;
			mLastTouchY = y;
			mActivePointerId = ev.getPointerId(0);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			final int pointerIndex = ev.findPointerIndex(mActivePointerId);
			final float x = ev.getX(pointerIndex);
			final float y = ev.getY(pointerIndex);

			// Only move if the ScaleGestureDetector isn't processing a gesture.
			if (!mScaleDetector.isInProgress()) {

				//realtime translation
				final float dx = x - mLastTouchX;
				final float dy = y - mLastTouchY;

				//store the final state of the translation
				mPosX += dx;
				mPosY += dy;
				mOffsetX += Math.abs(dx);
				mOffsetY += Math.abs(dy);

				//detection of a movement or just an action down
				if(mOffsetX > 25 || mOffsetY > 25){
					moving = true;
				}
				else{
					moving = false;
				}

				//Limit the translation of the map vertically and horizontally
				float horizontalLimit = (mScaleFactor/2)*svgWidth;
				float verticalLimit = (mScaleFactor/2)*svgHeight;
				if(mPosX > horizontalLimit)
					mPosX = horizontalLimit;
				if(mPosX < -horizontalLimit)
					mPosX = -horizontalLimit;
				if(mPosY > verticalLimit)
					mPosY = verticalLimit;
				if(mPosY < -verticalLimit -100)
					mPosY = -verticalLimit -100;

				invalidate();
			}

			mLastTouchX = x;
			mLastTouchY = y;

			break;
		}

		case MotionEvent.ACTION_UP: {
			mActivePointerId = INVALID_POINTER_ID;

			long time = System.currentTimeMillis() - startTime;
			duration=  duration + time;
			if(clickCount == 2 && mScaleFactor == 1)
			{
				if(duration<= MAX_DURATION)
				{

					mScaleFactor = 2.5f;
					mPosX = -mCanvasWidth;
					mPosY = -mCanvasHeight+200;

					invalidate();
				}
				clickCount = 0;
				duration = 0;
				time = 0;
				startTime = 0;

			}

			if(clickCount == 2 && mScaleFactor == 2.5f)
			{
				if(duration<= MAX_DURATION)
				{
					mScaleFactor = 1f;
					mPosX = 0;
					mPosY = -100;

					invalidate();
				}
				clickCount = 0;
				duration = 0;
				time = 0;
				startTime = 0;

			}

			//calculate position with translation and zoom
			float a = (ev.getX()-mPosX)/(mScaleFactor* mCalculatedScale);
			float b = (ev.getY()-mPosY)/(mScaleFactor* mCalculatedScale);

			//detection of the region and shows alertDialog
			for(int w = 0; w < clickableRectangles.size(); w++)
			{
				if(clickableRectangles.get(w).getRect().contains(a, b) && !moving){ //&& clickableRectangles.get(w).getState()!= 2) {

					for(int j = 0; j < clickableRectangles.size(); j++) 
					{
						if(clickableRectangles.get(j).getState()==0) 
						{
							clickableRectangles.get(j).setState(3);
						}
					}

					for(int j = 0; j < clickablePolygons.size(); j++) 
					{
						if(clickablePolygons.get(j).getState()==0) 
						{
							clickablePolygons.get(j).setState(3);
						}
					}

					clickableRectangles.get(w).setState(0);
					//AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
					//alertDialog.setTitle(clickableRectangles.get(w).getName());
					//alertDialog.show();
					invalidate();
				}


			}
			for(int w = 0; w < clickablePolygons.size(); w++)
			{
				if(clickablePolygons.get(w).getRegion().contains((int) a, (int) b) && !moving) {

					for(int j = 0; j < clickableRectangles.size(); j++) 
					{
						if(clickableRectangles.get(j).getState()==0) 
						{
							clickableRectangles.get(j).setState(3);
						}
					}

					for(int j = 0; j < clickablePolygons.size(); j++) 
					{
						if(clickablePolygons.get(j).getState()==0) 
						{
							clickablePolygons.get(j).setState(3);
						}
					}

					clickablePolygons.get(w).setState(0);
					invalidate();

					//AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
					//alertDialog.setTitle(clickablePolygons.get(w).getName());
					//alertDialog.show();

				}
			}
			break;
		}

		case MotionEvent.ACTION_CANCEL: {
			mActivePointerId = INVALID_POINTER_ID;
			break;
		}

		//a finger leaves the screen but at least one finger is still touching it.
		case MotionEvent.ACTION_POINTER_UP: {
			final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
					>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
					final int pointerId = ev.getPointerId(pointerIndex);
					if (pointerId == mActivePointerId) {
						final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
						mLastTouchX = ev.getX(newPointerIndex);
						mLastTouchY = ev.getY(newPointerIndex);
						mActivePointerId = ev.getPointerId(newPointerIndex);
					}
					break;
		}
		}

		return true;
	}



	/* -------------------- Methods --------------------
    /**
	 * public method to select the storey to show according to the selected button above the custom view
	 * @param storey selected from button
	 */
	public void setStorey(int storeyNb, Context context){
		try {
			MapModel.svgParsingStorey(storeyNb, context);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		invalidate();
	}

	/**
	 * public method to restore original scale and screen
	 */
	public void setOriginalScale(){
		mScaleFactor = 1f;
		mPosX = 0;
		mPosY = -100;
		clickCount = 0;
		duration = 0;
		time = 0;
		startTime = 0;
		invalidate();
	}


	public Path triangulation(ArrayList<BeaconRecord> records) {
		
		ArrayList<Point> pointsList = new ArrayList<Point>();
        
		/*
		ArrayList<String> uuids = null; //list of beacons uuids
		ArrayList<Double> distances = null; //list of distances matching the beacons

		for (int i=0; i<records.size(); i++) {
			BeaconRecord record = records.get(i);

			String uuid = record.getUuid(); //get the uuid of the beacon
			uuids.add(uuid);

			ArrayList<ScanRecord> list = record.getList();
			double distance = list.get(list.size()).getDistance(); //get the last distance recorded by the beacon
			distances.add(distance);

		}*/
		
		Point point1 = new Point(400,400); 
		Point point2 = new Point(600,400); 
		Point point3 = new Point(600,600); 
		Point point4 = new Point(400,600); 
		
		pointsList.add(point1);
		pointsList.add(point2);
		pointsList.add(point3);
		pointsList.add(point4);
		
		Path polygonPath = new Path();
        polygonPath.moveTo(pointsList.get(0).getX(), pointsList.get(0).getY());
        for (int j = 0; j < pointsList.size(); j++) {
            polygonPath.lineTo(pointsList.get(j).getX(), pointsList.get(j).getY());
        }
		return polygonPath;
	}
	
	
public Path addDot(double x, double y, int radius) {
		System.out.println((float) x);
		System.out.println((float) y);
		        
		Path polygonPath = new Path();
                
		polygonPath.addCircle((float) x, (float) y, radius, Path.Direction.CCW);

		return polygonPath;
	}

}

