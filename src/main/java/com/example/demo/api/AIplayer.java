package com.example.demo.api; /**
 * Implments a simple AI player to
 * automatically contol the tiger moves
 *
 * @author Professor Ajmal Mian
 * @dated Sep 2021
 * @version 1.0
 */
//import java.util.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIplayer {
    private Random rn; // for random tiger or location selection
    private GameRules rul; // an instance of GameRules to check for legal moves
    private int[] tigerLocs; // location of tigers for convenience 
    private int ntigers; // number of tigers placed


    public AIplayer() {
    }

    /**
     * Constructor for objects of class AIplayer.
     * Initializes instance variables.
     */
    public AIplayer(Random rn, GameRules rul, int[] tigerLocs, int ntigers) {
        // TODO 14 done
        this.rn = rn;
        this.rul = rul;
        this.tigerLocs = tigerLocs;
        this.ntigers = ntigers;
    }

    /**
     * Place tiger in a random vacant location on the Board
     * Update the board, the tiger count and tigerLocs.
     */
    public void placeTiger(Board bd,int loc) {
        //TODO 15
        bd.setTiger(loc);
        // 增加老虎
        rul.incrTigers();
    }

    /**
     * If possible to eat a goat, must eat and return 1
     * If cannot eat any goat, make a move and return 0
     * If cannot make any move (all Tigers blocked), return -1
     */
    public int makeAmove(Board bd) {
        if (eatGoat(bd)) return 1; // did eat a goat
        else if (simpleMove(bd)) return 0; // made a simple move
        else return -1; // could not move
    }

    /**
     * Randomly choose a tiger, move it to a legal destination and return true
     * if none of the tigers can move, return false.
     * Update the board and the tiger location (tigerLocs)
     */
    private boolean simpleMove(Board bd) {
        // TODO 21
        // 先挑选出能够移动的老虎
        List<Integer> tigerCanMoveList=new ArrayList<>();
        int[][] legalMoves = rul.legalMoves;
        for (int tigerLoc : tigerLocs) {
            int[] legalMove = legalMoves[tigerLoc];
            for (int i : legalMove) {
                if (bd.isVacant(i)) {
                    tigerCanMoveList.add(tigerLoc);
                    break;
                }
            }
        }
        if(tigerCanMoveList.size()==0){
            return false;
        }
        int randomNum=tigerCanMoveList.size()==1?1:tigerCanMoveList.size()-1;
        // 随机挑一只老虎
        int tigerIndex = rn.nextInt(randomNum);
        Integer tigerLoc = tigerCanMoveList.get(tigerIndex);
        // 移动这只老虎
        int[] legalMove = legalMoves[tigerLoc];
        for (int i : legalMove) {
            if (bd.isVacant(i)) {
                bd.setVacant(tigerLoc);
                bd.setTiger(i);
                break;
            }
        }
        return true;
    }

    /**
     * If possible, eat a goat and return true otherwise return false.
     * Update the board and the tiger location (tigerLocs)
     * <p>
     * Hint: use the canEatGoat method of GameRules
     */
    private boolean eatGoat(Board bd) {
        // TODO 22 done
        int[] scapeGoat = new int[2];
        // 如果存在能够吃羊的老虎,只活动这一只老虎就够了
        for (int tigerLoc : tigerLocs) {
            if (rul.canEatGoat(tigerLoc, bd, scapeGoat)) {
                System.out.println("老虎坐标为："+tigerLoc);
                System.out.println("替罪羊："+scapeGoat[0]);
                System.out.println("移动后位置："+scapeGoat[1]);
                // 吃掉羊
                bd.setVacant(scapeGoat[0]);
                rul.addGoat(-1);
                // 移动老虎
                bd.setVacant(tigerLoc);
                bd.setTiger(scapeGoat[1]);
                return true;
            }
        }
        return false;
    }

    // 传递老虎坐标
    public void setTigerLocs(int[] tigerLocs) {
        this.tigerLocs = tigerLocs;
    }
}
