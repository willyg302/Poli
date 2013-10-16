package com.hichi.poli.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String hashtag;
    private String candidate;
    private Date created;
    
    public Tile(int id, String type, String data, boolean bookmarked, String hashtag, String candidate, String date) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.bookmarked = bookmarked;
        this.hashtag = hashtag;
        this.candidate = candidate;
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); 
        try {
            created = format.parse(date);
        } catch (ParseException e) {
            // Fail gracefully
            created = new Date();
        }
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
    
    public String getHashtag() {
        return hashtag;
    }
    
    public String getCandidate() {
        return candidate;
    }
    
    public Date getCreated() {
        return created;
    }
}