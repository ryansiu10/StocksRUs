package net.stars.entities; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.stars.Main;
public class Transaction{
    public String date;
    public char stock_sym;
    public int num_shares;
    public float price;
    
    public void stockTransaction(int tax_id, String type, String date, String stock_sym, int num, Double price, Double earn){
        String q = "INSERT INTO Transactions(tax_id,type,date,stock_sym,num_shares,price,earn,total) VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?, ?);"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q)) {
                pstmt.setInt(1,tax_id);
                pstmt.setString(2,type);
                pstmt.setString(3,date);
                pstmt.setString(4,stock_sym);
                pstmt.setInt(5,num);
                pstmt.setDouble(6,price);
                pstmt.setDouble(7,earn);
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public void depositTransaction(String date, int tax_id, double amt) {
        //add money to the MARKET account balance
        String q = "INSERT INTO Transactions (tax_id,type,date,stock_sym,num_shares,price,earn,total)VALUES(\n"
        + "	?, ?, ?, ?, ?, ?, ?, ?);"; 
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q)) {
                pstmt.setInt(1,tax_id);
                pstmt.setString(2,"dep");
                pstmt.setString(3,date);
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setNull(5, Types.VARCHAR);
                pstmt.setNull(6, Types.VARCHAR);
                pstmt.setDouble(7, amt);
                pstmt.executeUpdate();  
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }

    public void interestTransaction(String date, int id, double interest) {
        String query = "INSERT INTO Transactions(tax_id,type,date,stock_sym,num_shares,price,earn,total) \n"
        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(query)){
          
            pstmt.setInt(1, id); 
            pstmt.setString(2, "int");
            pstmt.setString(3, date); 
            pstmt.setNull(4, Types.VARCHAR);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setDouble(7, interest);
            pstmt.executeUpdate();
                
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdrawTransaction(String date, int id, double amount) {
        String query = "INSERT INTO Transactions(tax_id,type,date,stock_sym,num_shares,price,earn,total) \n"
        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(query)){
          
            pstmt.setInt(1, id); 
            pstmt.setString(2, "wit");
            pstmt.setString(3, date);
            pstmt.setNull(4, Types.VARCHAR);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR); 
            pstmt.setDouble(7, amount);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}