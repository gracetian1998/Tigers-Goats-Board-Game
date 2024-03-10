package com.example.demo.api;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Maintains game rules. Checks if a move is legal. It also maintains move turns and the game
 * stage.
 *
 * @Student 1 Name:
 * @Student 1 Number:
 * @Student 2 Name:
 * @Student 2 Number:
 */
public class GameRules {

    // 改成{3，7，11},
    // list of all legal moves legalMoves[0] is {1,3,9} which means a piece from [0] can move to
    // [1],[3] or [9]. legalMoves[1] is {0,2,4} meaning a piece from [1] can move to [0],[2] or [4]
    public final int[][] legalMoves = {{1, 3, 9}, {0, 2, 4}, {1, 5, 14}, {0, 4, 6, 10},
            {1, 3, 5, 7}, {2, 4, 8, 13},
            {3, 7, 11}, {4, 6, 8}, {5, 7, 12}, {0, 10, 21}, {3, 9, 11, 18}, {6, 10, 15},
            {8, 13, 17}, {5, 12, 14,
            20}, {2, 13, 23},
            {11, 16, 18}, {15, 17, 19}, {12, 16, 20}, {10, 15, 19, 21}, {16, 18, 20, 22},
            {13, 17, 19, 23}, {9, 18,
            22}, {19, 21, 23},
            {14, 20, 22}};
    private final int MAXGOATS = 12;
    //list of all legal eat moves by tigers e.g. legalEats[0] means that a tiger at [0] can
    // eat a goat at [1] and land in [2] (there has to be a goat at [1] and [2] must be vacant)
    // tiger at [0] can also eat a goat at [9] and jump to 21 OR eat a goat at [3] and jump to [6]
    // legalEats[4]={} is empty meaning that at tiger at [4] has no options
    //例如，legalEats[0]意味着一只老虎在[0]可以
    //在[1]吃一只山羊，在[2]降落([1]必须有一只山羊，[2]必须是空的)
    //在[0]点的老虎也可以在[9]点吃一只山羊并跳到21点，或者在[3]点吃一只山羊并跳到[6]点
    // legalEats[4]={}是空的，意思是在tiger在[4]没有选项
    private final int[][] legalEats = {{1, 2, 9, 21, 3, 6}, {4, 7}, {1, 0, 5, 8, 14, 23},
            {4, 5, 10, 18}, {},
            {4, 3, 13, 20}, {3, 0, 7, 8, 11, 15}, {4, 1}, {5, 2, 7, 6, 12, 17}, {10, 11}, {},
            {10, 9}, {13, 14}, {},
            {13, 12}, {11, 6, 16, 17, 18, 21}, {19, 22}, {12, 8, 16, 15, 20, 23}, {10, 3, 19, 20},
            {}, {13, 5, 19, 18},
            {9, 0, 18, 15, 22, 23}, {19, 16}, {14, 2, 20, 17, 22, 21}};
    // Modify not final variable
    private final boolean goatsTurn;
    // Instance variables to maintain whose move it is
    // Modify not final variable
    private boolean moveStage;
    // Modify not final variable
    private int numGoats; //the number of goats on the board
    // Modify not final variable
    private int numTigers; //the number of tigers on the board

    //NOTE: You MUST use only the above 2 arrays to implement all moves.
    // Adding more arrays is NOT allowed.
    //注意:你必须只使用以上两个数组来实现所有的移动。
    //不允许添加更多数组。


    /**
     * Constructor for objects of class GameRules 类gamerrules对象的构造函数
     */
    public GameRules() {
        moveStage = false;
        goatsTurn = true;
        numGoats = 0;
        numTigers = 0;
    }

    /**
     * returns moveStage 是否为开始移动阶段
     */
    public boolean isMoveStage() {
        return moveStage;
    }

    /**
     * returns true if it is goats turn 如果是山羊回合，返回真
     */
    public boolean isGoatsTurn() {
        moveStage = true;
        return goatsTurn;
    }

    /**
     * Adds (+1 or -1) to goat numbers. Changes the goatsTurn and moveStage as per rules.
     * 给山羊数加(+1或-1)。 根据规则改变山羊回合数和移动回合数。
     */
    public void addGoat(int n) {
        //TODO 12 done
        numGoats = numGoats + n;
    }

    /**
     * returns number of goats
     */
    public int getNumGoats() {
        return numGoats;
    }

    /**
     * returns number of tigers
     */
    public int getNumTigers() {
        return numTigers;
    }

    /**
     * increments tigers and gives turn back to goats
     */
    public void incrTigers() {
        //TODO 16 done
        numTigers++;
        // 不管怎么样，都是轮到羊操作
        System.out.println("我加老虎啦！");
    }

    /**
     * Returns the nearest valid location (0-23) on the board to the x,y mouse click. Locations are
     * described in project description on LMS. You will need bkSize & GameViewer.locs to compute
     * the distance to a location. If the click is close enough to a valid location on the board,
     * return that location, otherwise return -1 (when the click is not close to any location).
     * Choose a threshold for proximity of click based on bkSize. 校验鼠标点击有效范围值
     */
    public int nearestLoc(int x, int y, int bkSize, int[][] locs) {
        // TODO 11 done

        // 点击之后坐标值发生改变
        x = x % 80 > bkSize ? x / 80 + 1 : x / 80;
        y = y % 80 > bkSize ? y / 80 + 1 : y / 80;

        int[] chessLocation = {x, y};

        System.out.println("X坐标：" + x);
        System.out.println("y坐标：" + y);

        // 获取所在的location
        int i = 0;
        for (; i < locs.length; i++) {
            if (Arrays.equals(locs[i], chessLocation)) {
                break;
            }
        }

        System.out.println("数值为：" + i);
        return i;
    }

    /**
     * Returns true if a move from location a to b is legal, otherwise returns false. For example:
     * a,b = 1,2 -> true; 1,3 -> false; 20,17 -> true. Refer to the project description for details.
     * Throws an exception for illegal arguments.
     */
    public boolean isLegalMove(int a, int b) {
        // TODO 19 done
        int[] legalMove = legalMoves[a];
        return Arrays.stream(legalMove).boxed().collect(Collectors.toList()).contains(b);
    }

    /**
     * Returns true of the tiger at tigerLoc (location) can eat any goat the location of the goat
     * that can be eaten is filled in scapeGoat[0] the destination where the tiger will land after
     * eating the goad is filled in scapeGoat[1]. Returns false if the tiger cannot eat any goat
     * <p>
     * NOTE: This method can use legalEats[][] only and no other array.
     */
    public boolean canEatGoat(int tigerLoc, Board bd, int[] scapeGoat) {
        // TODO 23 done
        // 输入老虎的位置，并且判断对应位置是否有羊和空点
        int[] legalEat = legalEats[tigerLoc];
        // 获取数组的长度，并且遍历
        for (int i = 0; i < legalEat.length; i++) {
            // 找出数组中羊的位置,且存在羊，且下一个位置为空
            if (i % 2 == 0 && bd.isGoat(legalEat[i]) && bd.isVacant(legalEat[i + 1])) {
                scapeGoat[0] = legalEat[i];
                scapeGoat[1] = legalEat[i + 1];
                return true;
            }
        }
        return false;
    }

    // 重新开启游戏
    public void resetGame() {
        moveStage = false;
        numGoats = 0;
        numTigers = 0;
    }
}
