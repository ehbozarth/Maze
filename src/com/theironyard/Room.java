package com.theironyard;

/**
 * Created by earlbozarth on 12/1/15.
 */
public class Room {

    int row;
    int col;

    boolean wasVisited = false;
    boolean hasBottom = true;
    boolean hasRight = true;
    boolean isStart = false;
    boolean isEnd = false;

    public Room(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
