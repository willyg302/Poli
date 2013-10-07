package com.hichi.poli.data;

import java.io.Serializable;

/**
 * Represents a single tile. Analogous to a row in the tiles table in our DB.
 * 
 * @author William Gaul
 */
public class Tile implements Serializable {
    private int id;
    private String type;
    private String data;
    private boolean bookmarked;
    
    public Tile(int id, String type, String data, boolean bookmarked) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.bookmarked = bookmarked;
    }
    
    public int getID() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public String getData() {
        return data;
    }
    
    public boolean isBookmarked() {
        return bookmarked;
    }
}