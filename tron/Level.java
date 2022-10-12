/**
 * Ez a Level osztály.
*/
package tron;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 *
 * @author Balu
 */
public class Level  {
    /**
     * A Level osztály konstruktora.
     * @param levelPath String tipusú érték
     * @throws IOException hibánál ezt az exception-t dobja
     */
    public Level(String levelPath) throws IOException {
        loadLevel(levelPath);
    }
    /**
     * Ez a loadLevel() függvény. Ez a metódus tölti be a pályát.
     * @param levelPath String tipusú érték
     * @throws FileNotFoundException hibánál ilyen exception-t is dob
     * @throws IOException hibánál ilyen exception-t is dob
     */
    public void loadLevel(String levelPath)  throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(levelPath));
    }
}
