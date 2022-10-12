/**
 * Ez a Move osztály. A Sprite egyik alosztálya
 */
package tron;
import tron.Sprite;
import java.awt.Image;

/**
 *
 * @author Balu
 */
public class Move extends Sprite{
    /**
     * Ez a Move osztály konstruktora
     * @param x a lépés x koordinátája
     * @param y a lépés y koordinátája
     * @param width a lépés szélessége
     * @param height a lépés magassága
     * @param image a lépés képe
     */
    public Move(int x,int y, int width, int height, Image image){
        super(x,y,width,height,image);
    }
    
}
