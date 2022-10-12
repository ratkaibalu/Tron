/**
 * Ez a GameEngine osztály, itt indul el a játék.
 * Itt beállítjuk azt hogy a játékosok milyen gombok lenyomásával tudnak lépni,
 * illetve a játékosok sebességét, magasságát stb..
*/
package tron;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import tron.HighScores;

/**
 *
 * @author Balu
 */
public class GameEngine extends JPanel{
    private final int FPS = 20;
    private final int PLAYER_WIDTH = 30;
    private final int PLAYER_HEIGHT = 30;
    private final int PLAYER_MOVEMENT = 30;
    private final String playerOneColor;
    private final String playerTwoColor;
    private final String playerOneName;
    private final String playerTwoName;
    boolean istart = true;
    boolean start = true;
    public boolean[][] free = new boolean[30][30];
    
    private final HighScores highScores;
    private boolean paused = false;
    private Image background;
    private int levelNum = 0;
    private Level level;
    private Player playerOne;


    private Player playerTwo;
    private Timer newFrameTimer;
    /**
     * Ez a GameEngine konstruktora, itt állítjuk be a játékosok neveit arra amit megadtak.
     * Itt fogjuk létrehozni a Timer-t is ami abban fog segíteni hogy a játék folyamatosan menjen.
     * A játékosok színét is itt állítjuk be.
     * @param player1 az első játékos neve
     * @param player2 a második játékos neve
     * @param color1 az első játékos színe
     * @param color2 a második játékos színe
     * @throws SQLException ha nem találja az adatbázist akkor ezt a hibát dobja
     */
    public GameEngine(String player1, String player2, String color1, String color2) throws SQLException {
        super();
        highScores = new HighScores(10);
        playerOneColor = color1;
        playerTwoColor = color2;
        playerOneName = player1;
        playerTwoName = player2;
        background = new ImageIcon("data/images/background2.jpg").getImage();
        
        //----Első játékos: nyilak
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerOne.setLeftVelx(-PLAYER_MOVEMENT);
                playerOne.setMoved(true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerOne.setRightVelx(PLAYER_MOVEMENT);
                playerOne.setMoved(true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerOne.setUpVely(PLAYER_MOVEMENT);
                playerOne.setMoved(true);
            }
        });
       
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerOne.setDownVely(-PLAYER_MOVEMENT);
                playerOne.setMoved(true);
            }
        });
        
        //-----Második játékos: WASD
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerTwo.setLeftVelx(-PLAYER_MOVEMENT);
                playerTwo.setMoved(true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerTwo.setRightVelx(PLAYER_MOVEMENT);
                playerTwo.setMoved(true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerTwo.setDownVely(PLAYER_MOVEMENT);
                playerTwo.setMoved(true);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                playerTwo.setUpVely(-PLAYER_MOVEMENT);
                playerTwo.setMoved(true);
            }
        });
        
        //----Pause: ESC
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
    }
    /**
     * Ez a restart() függvény. A játék indításakor és mindig amikor valamelyik fél
     * pontot szerez akkor ezt a függvényt hívja meg a program.
     * A metódus beállítja a játékosok alapvető helyzetét, színét, magasságát,szélességét.
     */
    public void restart() {
        try {
            level = new Level("data/levels/level0" + levelNum + ".txt");
        } catch (IOException ex){
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image playerOneImage = new ImageIcon("data/images/" +playerOneColor +".png").getImage();
        Image playerTwoImage = new ImageIcon("data/images/"+playerTwoColor+".png").getImage();
        playerOne = new Player(420,780, PLAYER_WIDTH,PLAYER_HEIGHT, playerOneImage);
        playerTwo = new Player(420,30, PLAYER_WIDTH,PLAYER_HEIGHT, playerTwoImage);
        for(int i=0;i<30;i++){
            for(int j=0;j<30;j++){
                free[i][j] = true;
            }
        }
        playerOne.setMoved(false);
        playerTwo.setMoved(false);
    }
    /**
     * Ez a paintComponent() függvény. Ez a metódus fogja kirajzolni a monitorra
     * a játékteret. Ha megmozdul valamelyik játékos akkor mindig újra hívódik ez a függvény.
     * @param grphcs Ha valami változás történik a játéktérben
     * akkor ezzel fogjuk meghívni a draw függvényt.
     */
    @Override
    protected void paintComponent(Graphics grphcs){
       
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 900, 900, null);
        playerOne.draw(grphcs);
        playerTwo.draw(grphcs);
        for(Move m : playerOne.getMove()){
            m.draw(grphcs);
        }
        for (Move m : playerTwo.getMove()) {
            m.draw(grphcs);
        }
    }
    /**
     * Ez a getScores() függvény. Visszaad egy HighScores tipusú értéket.
     * @return pontérték
     */
    public HighScores getScores(){
        return highScores;
    }
    /**
     * NewFrameListener nevű osztály. Ez fogja elvégezni a játék szabályos működését.
     * Ha az egyik játékos kimegy a pályáról vagy neki megy a saját vagy ellenfele
     * fénycsíkjának akkor az ellenfél kapja a pontot. És a függvény újra kirajzolja
     * a játékosokat. Ha az egyik játékos pontot kapott akkor meghívódik a restart()
     * függvény.
     */
    class NewFrameListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae){
            if (!paused) {
           
                free[playerOne.getY()/30][playerOne.getX()/30] = false;
                free[playerTwo.getY()/30][playerTwo.getX()/30] = false;
           
                
                playerOne.move(playerOneColor);
           
                playerTwo.move(playerTwoColor);

                
                if (free[playerOne.getY() / 30][playerOne.getX() / 30] == false && playerOne.getMoved() == true) {
                    try{
                        highScores.putHighScore(playerTwoName, 1);
                    }catch(SQLException e){
                        System.out.println("SQLException thrown!");
                    }
                    restart();
                }
                if (free[playerTwo.getY() / 30][playerTwo.getX() / 30] == false && playerTwo.getMoved() == true) {
                    try{
                        highScores.putHighScore(playerOneName, 1);
                    }catch(SQLException e){
                        System.out.println("SQLException thrown!");
                    }
                    restart();
                }


                
                if(playerOne.collides(playerTwo)){
                    restart();
                }
                if(playerTwo.collides(playerOne)){
                    restart();
                }
                
                if (playerOne.gameOver()) {
                    try {
                        highScores.putHighScore(playerTwoName, 1);
                    } catch (SQLException e) {
                        System.out.println("SQLException thrown!");
                    }
                    System.out.println(playerTwoName + " won!");

                    restart();
                }
                if(playerTwo.gameOver()){
                    try {
                        highScores.putHighScore(playerOneName, 1);
                    } catch (SQLException e) {
                        System.out.println("SQLException thrown!");
                    }
                    System.out.println(playerOneName + " won!");
                    
                    restart();
                }
            }
            repaint();
        }
    }
    
}
