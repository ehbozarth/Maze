package com.theironyard;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    static final int SIZE = 10;

    static ArrayList<ArrayList<Room>> createRooms(){
        ArrayList<ArrayList<Room>> rooms = new ArrayList<>();
        for(int row = 0; row < SIZE; row++){
            ArrayList<Room> roomRow = new ArrayList<>();
            for(int col = 0; col < SIZE; col++){
                roomRow.add(new Room(row,col));
            }
            rooms.add(roomRow);
        }
        return rooms;
    }//End of createRooms


    static ArrayList<Room> possibleNeighbors(ArrayList<ArrayList<Room>> rooms, int row, int col){
        ArrayList<Room> neighbors = new ArrayList<>();
        //top wall
        if(row > 0){
            neighbors.add(rooms.get(row-1).get(col));
        }
        //bottom wall
        if(row < SIZE-1){
            neighbors.add(rooms.get(row+1).get(col));
        }
        //left wall
        if(col > 0){
            neighbors.add(rooms.get(row).get(col-1));
        }
        //right wall
        if(col < SIZE-1){
            neighbors.add(rooms.get(row).get(col+1));
        }
        return neighbors;
    }//End of possibleNeighbors

    static Room randomNeighbor(ArrayList<ArrayList<Room>> rooms, int row, int col){
        ArrayList<Room> neighbors = possibleNeighbors(rooms, row, col);
        neighbors = neighbors.stream()
                .filter(room -> {
                    return !room.wasVisited;
                })
                .collect(Collectors.toCollection(ArrayList<Room>::new));
        //get random thing from array list
        if(neighbors.size() > 0){
            Random r = new Random();
            int index = r.nextInt(neighbors.size());
            return neighbors.get(index);
        }
        return null;
    }//end of randomNeighbor

    static void tearDownWall(Room oldRoom, Room newRoom){
        //going up
        if(newRoom.row < oldRoom.row){
            newRoom.hasBottom = false;
        }
        //going down
        else if(newRoom.row > oldRoom.row){
            oldRoom.hasBottom = false;
        }
        //going left
        else if(newRoom.col < oldRoom.col){
            newRoom.hasRight = false;
        }
        //going right
        else if(newRoom.col > oldRoom.col){
            oldRoom.hasRight = false;
        }
    }//End of tearDownWall

    static boolean createMaze(ArrayList<ArrayList<Room>> rooms, Room room){
        room.wasVisited = true;
        //ArrayList<Room> nextRooms = new ArrayList<>();
        Room nextRoom = randomNeighbor(rooms, room.row, room.col);

        if(nextRoom == null){
            boolean tempEnd = false;
            for(ArrayList<Room> tempRooms: rooms){
                for(Room tempRoom : tempRooms) {
                    if (tempRoom.isEnd) {
                        tempEnd = true;
                    }
                }
            }
            if(!tempEnd){
                room.isEnd = true;
            }
            return false;
        }
        tearDownWall(room, nextRoom);

        while(createMaze(rooms, nextRoom)){

        }
        return true;

    }//End of createMaze


    public static void main(String[] args) {
        ArrayList<ArrayList<Room>> rooms = createRooms();
        createMaze(rooms, rooms.get(0).get(0));
        for(ArrayList<Room> roomRow :rooms){
            System.out.print(" _");
        }
        System.out.println();
        for(ArrayList<Room> roomRow : rooms){
            System.out.print("|");  //Left Edge of the maze
            for(Room room : roomRow){
                if(room.row == 0 && room.col == 0){
                    room.isStart = true;
                }
            }
            for(Room room : roomRow){
                    String s1 = room.hasBottom ? "_" : " ";
                    if(room.isStart){
                        s1 = "o";
                    }
                    String s2 = room.hasRight ? "|" : " ";
                    if(room.isEnd){
                        s1 = "x";
                    }
                    System.out.print(s1 + s2);
                }
            System.out.println();
        }
    }//End of Main method


}//End of Main class
