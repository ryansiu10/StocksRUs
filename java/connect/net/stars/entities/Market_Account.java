package net.stars.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;

public class Market_Account{
    int tax_id;
    double balance;

    public Market_Account(int tax_id){
        this.tax_id = tax_id;
        this.balance = 1000;
    }

    public double getBal() {
        double bal = this.balance;
        String query = "SELECT balance \n" +
        "FROM Market_Acc \n"+
        "WHERE tax_id = ? ";
        
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setDouble(1, this.tax_id);
            ResultSet res = pstmt.executeQuery();
            res.next();
            bal = res.getDouble("balance");

            conn.close();
        } 
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return bal;
    }

    public void withdraw(double amt){
        String query = "UPDATE Market_Acc SET balance = ? \n" +
        "WHERE tax_id = ?";

        double bal = getBal() - amt;

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setDouble(1, bal);
            pstmt.setInt(2, this.tax_id);
            pstmt.executeUpdate();

            conn.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        MarketGod m = new MarketGod();
        String date = m.getDate();
            
        Transaction t = new Transaction();
        t.withdrawTransaction(date, this.tax_id, -1 * amt);
        /////////////////////////////////////////////////////////////
        
    }

    public void deposit(double amt){
        String query = "UPDATE Market_Acc SET balance = balance + ? \n"+
        "WHERE tax_id = ?";

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setDouble(1,amt);
            pstmt.setInt(2,this.tax_id);
            pstmt.executeUpdate();
            //create deposit trans
            conn.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        MarketGod m = new MarketGod();
        String date = m.getDate();

        Transaction t = new Transaction();
        t.depositTransaction(date, this.tax_id, amt);
    }
}