package net.stars.entities; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;
 
public class Customer{

    public int log(String user, String pass)
    {
        int tax_id = -9999;
        String q = "SELECT tax_id \n"+
        "FROM Customer WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q)) {
                pstmt.setString(1,user);
                pstmt.setString(2,pass);
                ResultSet r = pstmt.executeQuery();
                while(r.next()){
                    tax_id = r.getInt("tax_id");
                }
                conn.close();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
        }

        if (tax_id != -9999)
        {
            System.out.println("Successful login!");
        }
        else
        {
            System.out.println("Login Failed!");
        }
        return tax_id;
    }

    public boolean reg(String name, String username,  String password, String address, String state, int phone_num, String email, int tax_id) {
        if(checkUnique(username)){
            String q1 = "INSERT INTO Customer(name,username,password,address,state,phonenumber,email,tax_id) \n"+
            "VALUES (?,?,?,?,?,?,?,?);";
            
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q1)) {
                pstmt.setString(1,name);
                pstmt.setString(2,username);
                pstmt.setString(3,password);
                pstmt.setString(4,address);
                pstmt.setString(5,state);
                pstmt.setInt(6,phone_num);
                pstmt.setString(7,email);
                pstmt.setInt(8,tax_id);
                pstmt.executeUpdate();
                conn.close();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            String q2 = "INSERT INTO Market_Acc(tax_id,balance) \n"+
            "VALUES(?,?);";
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q2)){
                double bal = 1000;
                pstmt.setInt(1, tax_id);
                pstmt.setDouble(2, bal);
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }
        return false;
    }

    public boolean checkUnique(String username) {
        boolean check = false; 
        String query = "SELECT username FROM Customer";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(query)){
            ResultSet res = pstmt.executeQuery();
            
            while(res.next())
            {
                if (username == res.getString("username")) {
                    return false;   
                }
            }
            check = true;
                
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return check;
    }
    
}