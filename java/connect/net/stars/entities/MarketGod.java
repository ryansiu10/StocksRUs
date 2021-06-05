package net.stars.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.time.LocalDate;

import net.stars.Main;

public class MarketGod {
    String status;
    String date;

    public void open(){
        String query = "UPDATE Market SET mk_status = ? , date = ? \n";
        //increment date by one for next opening date 
        String prev_day = getDate();
        String new_date = LocalDate.parse(prev_day).plusDays(1).toString();

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1,"open");
            pstmt.setString(2,new_date);
            pstmt.executeUpdate();
            conn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close(){
        String query = "UPDATE Market SET mk_status = ? \n";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1,"close");
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        //update Balances 
        String query2 = "SELECT tax_id, balance \n"+
        "FROM Market_Acc";

        ArrayList<Integer> tax_ids = new ArrayList<Integer>();
        ArrayList<Double> b = new ArrayList<Double>();
        
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query2)){
            ResultSet r = pstmt.executeQuery();
            while(r.next()){
                tax_ids.add(r.getInt("tax_id"));
                b.add(r.getDouble("balance"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String query3 = "INSERT INTO Balances(tax_id, balance, date) \n"+
            "VALUES (?, ?, ?);";
        for(int i = 0; i < tax_ids.size(); i++){
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(query3)){
                pstmt.setInt(1,tax_ids.get(i));
                pstmt.setDouble(2,b.get(i));
                pstmt.setString(3,getDate());
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void setStock(String aid, double price){
        String q = "UPDATE Stocks SET price = ? WHERE aid = ? \n";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q)){
            pstmt.setDouble(1,price);
            pstmt.setString(2,aid);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void setDate(String date){
        String curr_day = getDate();
        System.out.println(curr_day);
        while(!curr_day.equals(date)){
            curr_day = getDate();
            System.out.println(curr_day);
            open();
            close();
        }
    }

    public String getDate(){
        String q = "SELECT date \n" +
        "FROM Market";
        String d = "";

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q)){
            ResultSet r = pstmt.executeQuery();
            r.next();
            d = r.getString("date");
            conn.close();
        }   
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return d;
    }
}
