package net.stars.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;

public class Movie{
    public String title;
    public int year;
    public float ranking;


    public Movie(String title, int year, int ranking) {
        this.title = title;
        this.year = year;
        this.ranking = ranking;
    }

    public Object[][] getMovieInfo(String title) {
        Object[][] info = new Object[1][3];
        String q = "SELECT prod_year, ranking, rev \n" + 
        "FROM Movies WHERE title = ? ";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q)){
            pstmt.setString(1,title);
            ResultSet r = pstmt.executeQuery();
            r.next();
            info[0][0] = r.getInt("prod_year");
            info[0][1] = r.getDouble("ranking");
            info[0][2] = r.getInt("rev");
            conn.close();
        } 
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return info;
    }
    
}