/**
 * Ez a TronGUI osztály, itt hozzuk létre azt az ablakot amiben a játék fog lefutni,
 * illetve azokat az ablakokat amik a név, szín megadáshoz kellenek
 */
package tron;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



public class TronGUI {
    private JFrame frame;
    private GameEngine gameArea;
    /**
     * A TronGUI konstruktora
     * Itt adunk nevet az ablaknak, bekérjük a játékosok neveit és színeit.
     * Ezek után létrehozzuk a játékteret
     * @throws SQLException 
     */
    public TronGUI() throws SQLException{
        frame = new JFrame("Tron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String playerOne = (String) JOptionPane.showInputDialog(frame, "Enter your name:");
        String playerTwo = (String) JOptionPane.showInputDialog(frame, "Enter your name:");
        
        Object[] colors = {"GREEN", "BLUE", "RED", "BLACK", "WHITE", "YELLOW"};
        ImageIcon icon = new ImageIcon("data/images/icon.png");
        String colorOne = (String) JOptionPane.showInputDialog(frame, "Choose your color:", "Choosing color", JOptionPane.PLAIN_MESSAGE, icon, colors, colors[0]);
        
        Object[] o = {"", "", "", "", ""};
        int index = 0;
        for(int i = 0; i < colors.length; i++){
            if(!colors[i].equals(colorOne)){
                o[index] = colors[i];
                index++;
            }
        }
        String colorTwo = (String) JOptionPane.showInputDialog(frame, "Choose your color:", "Choosing color", JOptionPane.PLAIN_MESSAGE, icon, o, o[0]);
        gameArea = new GameEngine(playerOne,playerTwo, colorOne, colorTwo);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        
        JMenu menu = new JMenu("Scores");
        menuBar.add(menu);
        
        JMenuItem scores = new JMenuItem(new AbstractAction("Show scores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new HighScoreWindow(gameArea.getScores().getHighScores(), frame);
                } catch (SQLException ex) {
                    System.err.println("SQL exception thrown!");
                }
            }
        });
        
        menu.add(scores);
        
        
        frame.getContentPane().add(gameArea);
        
        frame.setPreferredSize(new Dimension(900,900));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
