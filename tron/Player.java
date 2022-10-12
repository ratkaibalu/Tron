/**
 * Ez a Player osztály. A Sprite egyik alosztálya.
 */
package tron;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
/**
 *
 * @author Balu
 */
public class Player extends Sprite {
    private double velx;
    private double vely;
    private boolean moved = false;
    
    private ArrayList<Move> playerMove = new ArrayList<>();
    private int index = 0;
    
    /**
     * Ez a Player osztály konstruktora.
     * @param x a játékos x koordinátája
     * @param y a játékos y koordinátája
     * @param width a játékos szélessége
     * @param height a játékos magassága
     * @param image a játékos színe/képe
     */
    public Player(int x, int y, int width, int height, Image image){
        super(x,y,width,height,image);
    }

    /**
     * Ez a move() függvény. Ez fogja beállítani a játékosok helyzetét. Ennek 
     * köszönhetően látszik úgy mintha folyamatosan mozognának a játékosok.
     * @param color a játékos színe
     */
    public void move(String color){
        x += velx;
        y += vely;
        playerMove.add(new Move(x, y, width, height, new ImageIcon("data/images/" + color + ".png").getImage()));

    }

    /**
     * Ez a gameOver() metódus. Ha az egyik játékos kimegy a játéktérből akkor 
     * visszaad egy igaz értéket, egyébként hamist ad vissza.
     * @return boolean érték
     */
    public boolean gameOver() {
        if (x + width >= 900 || x <= 0 || y + height >= 900 || y <= 0) {
            return true;
        }
        return false;
    }
    
    // ---Getterek és Setterek---
    public boolean getMoved() {
        return moved;
    }

    public void setMoved(boolean b) {
        moved = b;
    }

    public ArrayList<Move> getMove() {
        return playerMove;
    }

    public void setLeftVelx(double velx) {
        if (!(this.velx > 0)) {
            this.velx = velx;
            vely = 0;
        }
    }

    public void setRightVelx(double velx) {
        if (!(this.velx < 0)) {
            this.velx = velx;
            vely = 0;
        }
    }

    public void setUpVely(double vely) {
        if (!(this.vely < 0)) {
            this.vely = vely;
            velx = 0;
        }
    }

    public void setDownVely(double vely) {
        if (!(this.vely > 0)) {
            this.vely = vely;
            velx = 0;
        }
    }

}
