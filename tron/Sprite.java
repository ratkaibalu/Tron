/**
 * Ez a Sprite ősosztály. Ebből származik le a Player és a Move alosztály.
 */
package tron;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author Balu
 */
public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image image;
    /**
     * Ez a Sprite osztály konstruktora.
     * @param x az elem x koordinátája
     * @param y az elem y koordinátája
     * @param width az elem szélesség koordinátája
     * @param height az elem magasság koordinátája
     * @param image az elem képe
     */
    public Sprite(int x,int y, int width, int height, Image image){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }
    /**
     * Ez a draw() függvény. Ez a metódus fogja kirajzolni a játéktérre az elemeket.
     * @param g Graphics tipusú paraméter
     */
    public void draw(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }
    /**
     * Ez a collides() függvény. Ez a metódus nézi meg hogy összeütköztek-e a játékosok.
     * @param other Sprite tipusú paraméter
     * @return boolean érték
     */
    public boolean collides(Sprite other){
        Rectangle rect = new Rectangle(x,y,width,height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        return rect.intersects(otherRect);
    }
    
    // ---Getterek és Setterek---
    public int getX(){
        return x;
    }
    
     public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
