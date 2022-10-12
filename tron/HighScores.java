/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

/**
 *
 * @author bli
 */
public class HighScores {

    int maxScores;
    PreparedStatement insertStatement;
    PreparedStatement deleteStatement;
    Connection connection;

    public HighScores(int maxScores) throws SQLException {
        this.maxScores = maxScores;
        Properties connectionProps = new Properties();
        // Add new user -> MySQL workbench (Menu: Server / Users and priviliges)
        //                             Tab: Administrative roles -> Check "DBA" option
        connectionProps.put("user", "student");
        connectionProps.put("password", "W3EDSaq2");
        connectionProps.put("serverTimezone", "UTC");
        String dbURL = "jdbc:mysql://localhost:3306/highscores";
        connection = DriverManager.getConnection(dbURL, connectionProps);
        
        
        String insertQuery = "INSERT INTO HIGHSCORES (NAME, SCORE) VALUES (?, ?)";
        insertStatement = connection.prepareStatement(insertQuery);
        String deleteQuery = "DELETE FROM HIGHSCORES WHERE NAME=?";
        deleteStatement = connection.prepareStatement(deleteQuery);
    }

    public ArrayList<HighScore> getHighScores() throws SQLException {
        String query = "SELECT * FROM HIGHSCORES";
        ArrayList<HighScore> highScores = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery(query);
        while (results.next()) {
            String name = results.getString("NAME");
            int score = results.getInt("SCORE");
            highScores.add(new HighScore(name, score));
        }
        sortHighScores(highScores);
        return highScores;
    }

    public void putHighScore(String name, int score) throws SQLException {
        ArrayList<HighScore> highScores = getHighScores();
        if (highScores.size() < maxScores) {
            insertScore(name, score);
        } else {
            int leastScore = highScores.get(highScores.size() - 1).getScore();
            String leastScoreName = highScores.get(highScores.size() - 1).getName();
            if (leastScore < score) {
                deleteScores(leastScoreName);
                insertScore(name, score);
            }
        }
    }

    /**
     * Sort the high scores in descending order.
     * @param highScores 
     */
    private void sortHighScores(ArrayList<HighScore> highScores) {
        Collections.sort(highScores, new Comparator<HighScore>() {
            @Override
            public int compare(HighScore t, HighScore t1) {
                return t1.getScore() - t.getScore();
            }
        });
    }

    private void insertScore(String name, int score) throws SQLException {
        Tipus t = new Tipus();
        t = contains(name);
        if(t.getA()){
            deleteScores(name);
            insertStatement.setString(1, name);
            insertStatement.setInt(2, t.getPont() + 1);
            insertStatement.executeUpdate();
            
        }else{
            insertStatement.setString(1, name);
            insertStatement.setInt(2, score);
            insertStatement.executeUpdate();
        }
    }

    /**
     * Deletes all the highscores with score.
     *
     * @param score
     */
    private void deleteScores(String name) throws SQLException {
        deleteStatement.setString(1, name);
        deleteStatement.executeUpdate();
    }
    public Tipus contains(String name) throws SQLException {
        Tipus t  = new Tipus();
        for(HighScore h : getHighScores()){
            if(h.getName().equals(name)){
                t.setA(true);
                t.setPont(h.getScore());
                t.setName(h.getName());
            }
        }
        return t;
    }
    
    public class Tipus{
        String name;
        Boolean a;
        int pont;
        
        public Tipus(){
            name = "";
            a = false;
            pont = 0;
        }
        
        public String getName(){
            return name;
        }
        public void setName(String n){
            name = n;
        }
        
        public Boolean getA(){
            return a;
        }
        public void setA(Boolean b){
            a = b;
        }
        
        public int getPont(){
            return pont;
        }
        public void setPont(int p){
            pont = p;
        }
    }
}
