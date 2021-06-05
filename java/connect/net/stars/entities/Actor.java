package net.stars.entities; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;

public class Actor{
 
    public Object [][] getActorInfo(String aid){
        Object[][] info = new Object[1][3];
        String q1 = "SELECT name, dob \n" + 
        "FROM Actors WHERE aid = ? ;";
        String q2 = "SELECT movie_title \n" +
        "FROM Movie_Contracts WHERE aid = ?";        
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q1)){
            pstmt.setString(1,aid);
            ResultSet r = pstmt.executeQuery();
            r.next();
            info[0][0] = r.getString("name");
            info[0][1] = r.getString("dob");
            conn.close();
        } 
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q2)){
            pstmt.setString(1,aid);
            ResultSet r = pstmt.executeQuery();
            r.next();
            info[0][2] = r.getString("movie_title");
            conn.close();
        } 
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return info;
    }
    
}