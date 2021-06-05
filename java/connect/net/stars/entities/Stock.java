package net.stars.entities; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;

public class Stock{
    public char aid;
    public float price;
    public String date;
    
    public double getStock(String aid){
        double pr = 0;
        String q = "SELECT price FROM Stocks WHERE aid = ? ";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q)){
            pstmt.setString(1,aid);
            ResultSet r = pstmt.executeQuery();
            r.next();
            pr = r.getDouble("price");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return pr;
    }

    
}