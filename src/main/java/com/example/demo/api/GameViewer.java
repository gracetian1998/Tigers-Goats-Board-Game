package com.example.demo.api;
/**
 * Controls the drawing of the board and game play. Allows the human player to make goat moves.
 * Calls AIplayer to make tiger moves.
 *
 * @Student 1 Name:
 * @Student 1 Number:
 * @Student 2 Name:
 * @Student 2 Number:
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameViewer implements MouseListener {

    // 2D coordinates of valid locations on the board in steps of block size
    public static final int[][] locs =
            {{1, 1}, {4, 1}, {7, 1},
                    {2, 2}, {4, 2}, {6, 2},
                    {3, 3}, {4, 3}, {5, 3},
                    {1, 4}, {2, 4}, {3, 4},
                    {5, 4}, {6, 4}, {7, 4},
                    {3, 5}, {4, 5}, {5, 5},
                    {2, 6}, {4, 6}, {6, 6},
                    {1, 7}, {4, 7}, {7, 7}};
    // instance variables
    private final int bkSize; // block size - all other measurements to be derived from bkSize
    private final int brdSize; // board size
    private final SimpleCanvas sc; // an object of SimpleCanvas to draw
    private final GameRules rules; // an object of GameRules
    private final Board bd; // an object of Board
    private final AIplayer ai; //an object of AIplayer
    // source and destination for the goat moves
    // 表示羊棋从起点到终点的移动的两个坐标值
    private final int[] mov = {-1, -1}; //-1 means no selection
    // 创建Random
    private final Random rd = new Random();
    // 羊咩咩图标
    private final ImageIcon goatIcon = new ImageIcon(this.getClass().getClassLoader()
            .getResource("image/goat_icon.png"));
    // 羊咩咩图标
    private final ImageIcon goatSelectedIcon = new ImageIcon(this.getClass().getClassLoader()
            .getResource("image/goat_selected_icon.png"));
    // 老虎图标
    private final ImageIcon tigerIcon = new ImageIcon(this.getClass().getClassLoader()
            .getResource("image/tiger_icon.png"));
    // 用来临时记录上一次点击选择中的羊的变量
    private Integer lastSelectedGoat;

    /**
     * Constructor for objects of class GameViewer Initializes instance variables and adds mouse
     * listener. Draws the board.
     */
    public GameViewer(int bkSize) {
        this.bkSize = bkSize;
        brdSize = bkSize * 8;
        sc = new SimpleCanvas("Tigers and Goats", brdSize, brdSize, Color.BLUE);
        sc.addMouseListener(this);
        rules = new GameRules();
        bd = new Board();
        ai = new AIplayer(rd, rules, null, 0);
        drawBoard();
    }

    /**
     * Constructor with default block size
     */
    public GameViewer() {
        this(80);
    }

    /**
     * Draws the boad lines and the pieces as per their locations. Drawing of lines is provided,
     * students to implement drawing of pieces and number of goats.
     */
    private void drawBoard() {
        // 更换底色
        sc.drawRectangle(0, 0, brdSize, brdSize, Color.BLUE); //wipe the canvas

        //draw shadows of Goats and Tigers - not compulsory, for beauty only /////////////

        //////////////////////////////////////////////////////
        // Draw the lines
        drawChessBoard();

        // TODO 10
        // Draw the goats and tigers. (Drawing the shadows is not compulsory)
        // 遍历全部点，如果是羊，就画羊棋，如果是虎就画虎棋
        for (int i = 0; i <= 23; i++) {
            if (bd.isGoat(i)) {
                int[] locArray = locs[i];
                // sc.drawCircle(locArray[0] * 80, locArray[1] * 80, 20, Color.white);
                sc.drawPic(goatIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
            }

            if (!bd.isGoat(i) && !bd.isVacant(i)) {
                int[] locArray = locs[i];
                // sc.drawRectangle(locArray[0] * 80 - 20, locArray[1] * 80 - 20,locArray[0] * 80 + 20, locArray[1] * 80 + 20, Color.yellow);
                sc.drawPic(tigerIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
            }
        }
        // Display the number of goats
        // 绘制当前的羊咩咩数量
        sc.drawRectangle(100, 580, 270, 620, Color.BLUE);
        sc.drawString("现在有羊咩咩：" + rules.getNumGoats(), 100, 600, Color.WHITE);

    }

    /**
     * If vacant, place a goat at the user clicked location on board. Update goat count in rules and
     * draw the updated board
     */
    public void placeGoat(int loc) {
        // TODO 2 done
        // 棋盘判断该位置是否可以下棋？
        boolean vacant = bd.isVacant(loc);
        if (vacant) {
            // 下呗
            bd.setGoat(loc);
            // 画棋
            rules.addGoat(1);
            int[] locArray = locs[loc];
            // sc.drawCircle(locArray[0] * 80, locArray[1] * 80, 20, Color.white);
            sc.drawPic(goatIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
        }
    }

    /**
     * Calls the placeTiger method of AIplayer to place a tiger on the board. Increments tiger count
     * in rules. Draws the updated board. 下虎棋这边漏了入参，已修改
     */
    public void placeTiger(int loc) {
        // TODO 13 done
        // 下呗
        ai.placeTiger(bd, loc);
        // 画棋
        int[] locArray = locs[loc];
        // sc.drawRectangle(locArray[0] * 80 - 20, locArray[1] * 80 - 20, locArray[0] * 80 + 20, locArray[1] * 80 + 20, Color.yellow);
        sc.drawPic(tigerIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
    }

    /**
     * Toggles goat selection - changes the colour of selected goat. Resets selection and changes
     * the colour back when the same goat is clicked again. Selects destination (if vacant) to move
     * and calls moveGoat to make the move.
     */
    public void selectGoatMove(int loc) {
        // TODO 16 done
        // 切换羊咩咩的颜色
        if (bd.isGoat(loc)) {
            // 选择一只羊咩咩，则标记上该坐标值
            if (lastSelectedGoat == null) {
                int[] locArray = locs[loc];
                // sc.drawCircle(locArray[0] * 80, locArray[1] * 80, 20, Color.green);
                sc.drawPic(goatSelectedIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
                lastSelectedGoat = loc;
                return;
            }
            // 如果选同一只，则表示取消选择
            if (lastSelectedGoat == loc) {
                int[] locArray = locs[loc];
                // sc.drawCircle(locArray[0] * 80, locArray[1] * 80, 20, Color.white);
                sc.drawPic(goatIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
                lastSelectedGoat = null;
                return;
            }
            // 如果选择另一只，则切换坐标值
            int[] locArray = locs[lastSelectedGoat];
            // sc.drawCircle(locArray[0] * 80, locArray[1] * 80, 20, Color.white);
            sc.drawPic(goatIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
            locArray = locs[loc];
            //  sc.drawCircle(locArray[0] * 80, locArray[1] * 80, 20, Color.green);
            sc.drawPic(goatSelectedIcon, locArray[0] * 80 - 40, locArray[1] * 80 - 40, 80, 80);
            lastSelectedGoat = loc;
        }
    }

    /**
     * Make the user selected goat move only if legal otherwise set the destination to -1 (invalid).
     * If did make a goat move, then update board, draw the updated board, reset mov to -1,-1. and
     * call tigersMove() since after every goat move, there is a tiger move.
     */
    // 添加了入参起点a，终点b
    public void moveGoat(int a, int b) {
        // TODO 18
        // 交换羊的位置
        bd.swap(a, b);

        // 需要重新绘制棋盘上的棋子+显示羊咩咩数
        drawBoard();

        // reset move to -1.-1
        mov[0] = -1;
        mov[1] = -1;

        // 轮到老虎AI移动了
        tigersMove();
    }

    /**
     * Call AIplayer to make its move. Update and draw the board after the move. If Tigers cannot
     * move, display "Goats Win!". If goats are less than 6, display "Tigers Win!". No need to
     * terminate the game.
     */
    public void tigersMove() {
        // TODO 20
        // 判断老虎当前能否移动，或者吃羊，如果不能移动或者吃羊，则GG思密达
        int[] tigerLocs = new int[3];
        int tigerFlag = 0;
        // 找出所有的老虎的坐标数值
        for (int i = 0; i <= 23; i++) {
            if (!bd.isGoat(i) && !bd.isVacant(i)) {
                tigerLocs[tigerFlag] = i;
                tigerFlag++;
            }
        }
        // 将老虎坐标传递给AiPlayer
        ai.setTigerLocs(tigerLocs);

        // 手动遍历所有老虎坐标进行操作
        int makeAmove = ai.makeAmove(bd);
        System.out.println("老虎移动方式：" + makeAmove);
        // 需要重新绘制棋盘上的棋子+显示羊咩咩数
        drawBoard();
        if (makeAmove == -1) {
            JOptionPane
                    .showMessageDialog(null, "羊咩咩获胜~~！", "游戏提示", JOptionPane.INFORMATION_MESSAGE);
            rules.resetGame();
            bd.resetGame();
            drawChessBoard();
        }
        if (rules.getNumGoats() == 5) {
            JOptionPane.showMessageDialog(null, "羊咩咩木大了！！", "游戏提示", JOptionPane.WARNING_MESSAGE);
            rules.resetGame();
            bd.resetGame();
            drawChessBoard();
        }
    }

    /**
     * 画棋盘
     */
    private void drawChessBoard() {
        // 更换底色
        sc.drawRectangle(0, 0, brdSize, brdSize, Color.BLUE); //wipe the canvas
        for (int i = 1; i < 9; i++) {
            // 棋盘颜色
            //draw the red lines
            if (i < 4) {
                sc.drawLine(locs[i - 1][0] * bkSize, locs[i - 1][1] * bkSize,
                        locs[i + 5][0] * bkSize, locs[i + 5][1] * bkSize, Color.red);
            } else if (i == 4) {
                sc.drawLine(locs[i + 5][0] * bkSize, locs[i + 5][1] * bkSize,
                        locs[i + 7][0] * bkSize, locs[i + 7][1] * bkSize, Color.red);
            } else if (i == 5) {
                sc.drawLine(locs[i + 7][0] * bkSize, locs[i + 7][1] * bkSize,
                        locs[i + 9][0] * bkSize, locs[i + 9][1] * bkSize, Color.red);
            } else {
                sc.drawLine(locs[i + 9][0] * bkSize, locs[i + 9][1] * bkSize,
                        locs[i + 15][0] * bkSize, locs[i + 15][1] * bkSize, Color.red);
            }

            if (i == 4 || i == 8) {
                continue; //no more to draw at i=4,8
            }
            // vertical white lines
            sc.drawLine(i * bkSize, i * bkSize,
                    i * bkSize, brdSize - i * bkSize, Color.white);
            // horizontal white lines
            sc.drawLine(i * bkSize, i * bkSize,
                    brdSize - i * bkSize, i * bkSize, Color.white);

        }
    }

    /**
     * Respond to a mouse click on the board. Get a valid location nearest to the click (from
     * GameRules). If nearest location is still far, do nothing. Otherwise, call placeGoat to place
     * a goat at the location. Call this.placeTiger when it is the tigers turn to place. When the
     * game changes to move stage, call selectGoatMove to move the user selected goat to the user
     * selected destination.
     */
    public void mousePressed(MouseEvent e) {
        // TODO 1 要完善计数机制，然后到老虎移动，循环
        // 获取鼠标点下的位置的坐标
        Point p = e.getPoint();

        int x = (int) p.getX();
        int y = (int) p.getY();

        // 获取数值化坐标
        int i = rules.nearestLoc(x, y, 40, locs);

        // 当i==24就是找不到任何匹配的数组，直接结束方法
        if (i == 24) {
            return;
        }

        // 判断是否为移动模式，不是，则下羊+下虎
//        int numGoats = rules.getNumGoats();
        if (!rules.isMoveStage()) {
            int numGoats = setUpChess(i);
            // 绘制当前的羊咩咩数量
            sc.drawRectangle(100, 580, 270, 620, Color.BLUE);
            sc.drawString("现在有羊咩咩：" + numGoats, 100, 600, Color.WHITE);
        }

        // 判断是否为移动模式，是则选择羊（选中后变绿色）
        if (rules.isMoveStage()) {
            selectGoatMove(i);
            if (lastSelectedGoat != null) {
                // 判断是否为合法移动坐标,即判断取出对应位置的数组，是否存在对应的元素
                if (rules.isLegalMove(lastSelectedGoat, i) && bd.isVacant(i)) {
                    mov[0] = lastSelectedGoat;
                    mov[1] = i;
                    moveGoat(mov[0], mov[1]);
                    // 移动完后，必须将上一次选择清空，如果不清空，下一次点击鼠标会又一次画上一次羊的位置
                    lastSelectedGoat = null;
                }
            }
        }
        // 当老虎变成3只的时候，开始移动阶段
        if (rules.getNumTigers() == 3) {
            rules.isGoatsTurn();
        }
    }

    /**
     * 下棋
     *
     * @param i 指定的数值化坐标
     * @return 羊咩咩数量
     */
    private int setUpChess(int i) {
        // 然后下羊棋,需要判断是否为12只了，如果12只就不准再下新羊了
        if (12 - rules.getNumGoats() > 0) {
            placeGoat(i);
        }
        int numGoats = rules.getNumGoats();
        // 判断0只羊时不准下老虎，判断如果老虎是否为3只了，如果超过3只不能下老虎了
        Random rn = new Random();
        if (numGoats != 0 && numGoats % 4 == 0 && numGoats != 20 && 3 - rules.getNumTigers() > 0) {
            System.out.println("我要下老虎啦！");
            int rdNum;
            do {
                rdNum = rn.nextInt(23);
            }
            while (!bd.isVacant(rdNum) || bd.isGoat(rdNum));
            placeTiger(rdNum);
        }
        return numGoats;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
