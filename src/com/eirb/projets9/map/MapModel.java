package com.eirb.projets9.map;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;

import com.eirb.projets9.R;
import com.eirb.projets9.map.objects.Point;
import com.eirb.projets9.map.objects.PolygonRoom;
import com.eirb.projets9.map.objects.RectangleRoom;

/**
 * Contains all the needed information for Map functionality like SVG files paths.
 */
public class MapModel {

       /* -------------------- Global Variables -------------------- */

    /**
     * enables communication between buttons and custom view
     */
    static int storey = 0; //storey of the school to show

    private static ArrayList<RectangleRoom> nonClickableRectangles = new ArrayList<RectangleRoom>();
    private static ArrayList<RectangleRoom> clickableRectangles = new ArrayList<RectangleRoom>();
    private static ArrayList<PolygonRoom> nonClickablePolygons = new ArrayList<PolygonRoom>();
    private static ArrayList<PolygonRoom> clickablePolygons = new ArrayList<PolygonRoom>();
    private static PolygonRoom mContour;
    private static JSONArray jsonRoomArray;


    /* -------------------- Attributes -------------------- */
    private String mRoomURL = "http://edt.eirb.fr/p/salles.json";
    private static Context mContext;

    private static boolean logged;


    /* -------------------- Constructors -------------------- */

    public MapModel()
    {}

    /**
     * svg parsing of a storey
     * @param storey selected
     * @param context 
     */
    public static void svgParsingStorey(int storey, Context context) throws ParserConfigurationException, SAXException, IOException {
        // Clear lists contents
        getClickableRectangles().clear();
        getNonClickableRectangles().clear();
        getNonClickablePolygons().clear();
        getClickablePolygons().clear();
        
        if (context == null)
        	System.out.println(context);
        
        InputStream raw = context.getResources().openRawResource(R.raw.rdc);

        switch (storey) {
            case 0:
                raw = context.getResources().openRawResource(R.raw.rdc);
                break;
            case 1:
                raw = context.getResources().openRawResource(R.raw.etage1);
                break;
            case 2:
                raw = context.getResources().openRawResource(R.raw.etage2);
                break;
            case 3:
                raw = context.getResources().openRawResource(R.raw.etage3);
                break;
            case -1:
                raw = context.getResources().openRawResource(R.raw.ss);
                break;

            default:
                raw = context.getResources().openRawResource(R.raw.rdc);
        }

        // Parse XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(raw);
        NodeList nList = doc.getElementsByTagName("rect");
        NodeList pList = doc.getElementsByTagName("polygon");

        float x, y, w, h;

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String xString = eElement.getAttribute("x");
                String yString = eElement.getAttribute("y");
                String wString = eElement.getAttribute("width");
                String hString = eElement.getAttribute("height");
                if(eElement.hasAttribute("id")){ // It has a name and it is clickable
                    String idRect = eElement.getAttribute("id");
                    String nameRect = eElement.getAttribute("name");
                    int stateRect = 2;
                    // If user is connected : show room availability
                    if(logged) {
                        for (int i = 0; i < jsonRoomArray.length(); i++) {
                            JSONObject e = null;
                            try {
                                e = jsonRoomArray.getJSONObject(i);
                                if (e.getString("slug").equals(idRect)) {
                                    if (e.getString("free").equals("true")) {
                                        stateRect = 1;
                                    } else if (e.getString("free").equals("false")) {
                                        stateRect = 0;
                                    }
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }

                    //if(!xString.equals("") && !yString.equals("") && !wString.equals("") && !hString.equals("")){
                        x = Float.valueOf(xString);
                        y = Float.valueOf(yString);
                        w = Float.valueOf(wString);
                        h = Float.valueOf(hString);
                        getClickableRectangles().add(new RectangleRoom(idRect, nameRect, stateRect, new RectF(x, y, x + w, y + h)));
                    //}
                }
                else{
                    if(!xString.equals("") && !yString.equals("") && !wString.equals("") && !hString.equals("")){
                        x = Float.valueOf(xString);
                        y = Float.valueOf(yString);
                        w = Float.valueOf(wString);
                        h = Float.valueOf(hString);
                        getNonClickableRectangles().add(new RectangleRoom(new RectF(x, y, x + w, y + h)));
                    }
                }
            }
        }

        for (int temp2 = 0; temp2 < pList.getLength(); temp2++) {
            Node pNode = pList.item(temp2);
            if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pElement = (Element) pNode;
                String PointsString = pElement.getAttribute("points");
                PointsString = cleanSpace(PointsString);
                if(pElement.hasAttribute("id")){
                    String idPolygon = pElement.getAttribute("id");
                    String namePolygon = pElement.getAttribute("name");
                    int statePolygon = 2;
                    // If user is connected : show room availability
                    if(logged) {
                        for (int i = 0; i < jsonRoomArray.length(); i++) {
                            JSONObject e = null;
                            try {
                                e = jsonRoomArray.getJSONObject(i);
                                if (e.getString("slug").equals(idPolygon)) {
                                    if (e.getString("free").equals("true")) {
                                        statePolygon = 1;
                                    } else if (e.getString("free").equals("false")) {
                                        statePolygon = 0;
                                    }
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                    if(!PointsString.equals("") || !PointsString.equals(" ")){
                        ArrayList<Point> pointsList= list2PolygonPoints(PointsString);
                        Path polygonPath = new Path();
                        polygonPath.moveTo(pointsList.get(0).getX(), pointsList.get(0).getY());
                        for (int j = 0; j < pointsList.size(); j++) {
                            polygonPath.lineTo(pointsList.get(j).getX(), pointsList.get(j).getY());
                        }
                        polygonPath.lineTo(pointsList.get(0).getX(), pointsList.get(0).getY());
                        if(!idPolygon.equals("contour"))
                            getClickablePolygons().add(new PolygonRoom(idPolygon, namePolygon, statePolygon, polygonPath, new Region()));
                        else {
                            Log.d("#Enorme", "Un contour sauvage apparait...");
                            mContour = new PolygonRoom(polygonPath, new Region(), idPolygon);
                            if(mContour == null)
                               Log.e("#PasEnorme", "Contour sauvage s'enfuit...");
                        }
                    }
                }
                else{
                    if(!PointsString.equals("") || !PointsString.equals(" ")){
                        ArrayList<Point> pointsList= list2PolygonPoints(PointsString);
                        Path polygonPath = new Path();
                        polygonPath.moveTo(pointsList.get(0).getX(), pointsList.get(0).getY());
                        for (int j = 0; j < pointsList.size(); j++) {
                            polygonPath.lineTo(pointsList.get(j).getX(), pointsList.get(j).getY());
                        }
                        polygonPath.lineTo(pointsList.get(0).getX(), pointsList.get(0).getY());
                        getNonClickablePolygons().add(new PolygonRoom(polygonPath, new Region(), null));
                    }
                }
            }
        }
    }

    /**
     * clean svg string attributes problem
     * @param string to clean
     * @return cleaned string
     */
    private static String cleanSpace(String string) {
        String result = string.trim();
        ArrayList<Character> chars = new ArrayList<Character>();
        for (char c : string.toCharArray()) {
            chars.add(c);
        }
        for(int i = 1; i< chars.size()-1; i++){  //string.trim avoids segfaults
            if(Character.isWhitespace(chars.get(i)) && Character.isWhitespace(chars.get(i+1))){
                chars.remove(i);
            }
        }
        StringBuilder builder = new StringBuilder(chars.size());
        for(Character c: chars)
        {
            builder.append(c);
        }
        result = builder.toString();

        return result;
    }

    /**
     * Conversion of String to Points list
     * @param string to convert
     * @return ArrayList from converted string
     */
    private static ArrayList<Point> list2PolygonPoints(String string){
        ArrayList<Point> pointsList = new ArrayList<Point>();
        String[] strings = string.split(" ");
        for(int i = 0; i < strings.length; i++)
        {
            if(strings[i] != null && !strings[i].equals(" ") && !strings[i].equals("") && !strings[i].equals("  ") && !strings[i].equals("   ")){
                String[] strings2 = strings[i].split(",");
                float coordinateX = Float.valueOf(strings2[0]);
                float coordinateY = Float.valueOf(strings2[1]);
                pointsList.add(new Point(coordinateX, coordinateY));
            }
            else{
                Log.d("Map", "empty string while parsing svg");
            }
        }
        return pointsList;
    }





    /**
     * Getters and setters for RoomArrays
     */
    public static ArrayList<PolygonRoom> getClickablePolygons() {
        return clickablePolygons;
    }

    public static void setClickablePolygons(ArrayList<PolygonRoom> clickablePolygons) {
        clickablePolygons = clickablePolygons;
    }

    public static ArrayList<PolygonRoom> getNonClickablePolygons() {
        return nonClickablePolygons;
    }

    public static void setNonClickablePolygons(ArrayList<PolygonRoom> nonClickablePolygons) {
        nonClickablePolygons = nonClickablePolygons;
    }

    public static ArrayList<RectangleRoom> getNonClickableRectangles() {
        return nonClickableRectangles;
    }

    public static void setNonClickableRectangles(ArrayList<RectangleRoom> nonClickableRectangles) {
        nonClickableRectangles = nonClickableRectangles;
    }

    public static ArrayList<RectangleRoom> getClickableRectangles() {
        return clickableRectangles;
    }

    public static void setClickableRectangles(ArrayList<RectangleRoom> clickableRectangles) {
        clickableRectangles = clickableRectangles;
    }

    public static PolygonRoom getContour() {
        return mContour;
    }

    public static void setContour(PolygonRoom contour) {
        mContour = contour;
    }

}
