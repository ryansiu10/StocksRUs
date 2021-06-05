package net.stars.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;
public class Movie_Contract{
    public char aid;
    public String movie_title;
    public String role;
    public int year;
    public int tot_value;

    public Object[][] getMovieContractInfo(String aid) {
        Object[][] info = new Object[1][3];
        String q = "SELECT movie_title, role, year, tot_value \n" + 
        "FROM Movie_Contracts WHERE aid = ? ";        
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q)){
            pstmt.setString(1,aid);
            ResultSet res = pstmt.executeQuery();
            res.next();
            info[0][0] = res.getString("movie_title");
            info[0][1] = res.getString("role");
            info[0][2] = res.getInt("year");
            info[0][3] = res.getInt("tot_value");
            conn.close();
        } 
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return info;
    }


    
}