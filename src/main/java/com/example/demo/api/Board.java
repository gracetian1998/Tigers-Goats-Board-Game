package com.example.demo.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains and updates the status of the board i.e. the locations of goats and tigers
 *
 * @Student 1 Name:Yazhen Tian
 * @Student 1 Number:
 * @Student 2 Name:
 * @Student 2 Number:
 */
public class Board {

    Piece[] board;
    // 创建一个记录该坐标状态信息的map
    private Map<Integer, Piece> map = new HashMap<>();


    /**
     * Constructor for objects of class Board. Initializes the board VACANT.
     */
    public Board(Piece[] board) {
        // TODO 3 done
        this.board = board;
    }

    public Board() {
    }

    /**
     * Checks if the location a is VACANT on the board
     */
    public boolean isVacant(int a) {
        //TODO 4 done
        Piece piece = map.get(a);
        return piece == null || piece == Piece.VACANT;
    }

    /**
     * Sets the location a on the board to VACANT
     */
    public void setVacant(int a) {
        //TODO 5 done
        map.remove(a);
        map.put(a, Piece.VACANT);
    }

    /**
     * Checks if the location a on the board is a GOAT
     */
    public boolean isGoat(int a) {
        //TODO 6 done
        Piece piece = map.get(a);
        return piece == Piece.GOAT;
    }

    /**
     * Sets the location a on the board to GOAT
     */
    public void setGoat(int a) {
        //TODO 7 done
        map.remove(a);
        map.put(a, Piece.GOAT);
    }

    /**
     * Sets the location a on the board to TIGER
     */
    public void setTiger(int a) {
        //TODO 8 done
        map.remove(a);
        map.put(a, Piece.TIGER);
    }

    /**
     * Moves a piece by swaping the contents of a and b
     */
    // 只用来交换羊
    public void swap(int a, int b) {
        //TODO 9
        setGoat(b);
        setVacant(a);
    }

    // An enumated type for the three possibilities
    private enum Piece {
        GOAT, TIGER, VACANT
    }

    // 重新开启游戏
    public void resetGame() {
        map = new HashMap<>();
    }
}
