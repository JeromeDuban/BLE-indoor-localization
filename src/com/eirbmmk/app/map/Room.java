package com.eirbmmk.app.map;

public abstract class Room{

    private String id = new String();
    private String name = new String();
    private int state;


    /**
     * Constructor of Room
     *
     * @param id the id of the room
     * @param name the name of the room
     * @param state the state (free or busy) of the room
     */
    public Room(String id, String name, int state) {
        this.setId(id);
        this.setName(name);
        this.setState(state);
    }

    public Room(){
        this.setId(null);
        this.setName(null);
        this.setState(2);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}



