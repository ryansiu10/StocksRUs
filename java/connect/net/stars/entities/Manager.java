package net.stars.entities; 

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import net.stars.Main;

public class Manager{
    public String username;
    public String name;
    public char state;
    public int phone_num;
    public String email;
    public int tax_id;
    public String password;
    
    public int log(String user, String pass)
    {
        int tax_id = -9999;
        String q = "SELECT tax_id \n"+
        "FROM Manager WHERE username = ? AND password = ?";
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

    public void add_int(double perc){
        String query = "SELECT tax_id FROM Market_Acc";

        ArrayList<Integer> idList = new ArrayList<Integer>();

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            ResultSet res = pstmt.executeQuery();

            while(res.next())
                idList.add(res.getInt("tax_id"));

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // average monthly balance

        ArrayList<Double> balList = new ArrayList<Double>();

        for (int i = 0; i < idList.size(); i++) {
            String balquery = "SELECT balance FROM Balances WHERE tax_id = ?";
            int ind = idList.get(i);
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(balquery)){
            
            pstmt.setInt(1, ind);
            ResultSet res = pstmt.executeQuery();

            while(res.next())
                balList.add(res.getDouble("balance"));
            
            conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        for (int i = 0; i < idList.size(); i++) {
            int id = idList.get(i);
            double balance = balList.get(i);

            double interest = balance * perc * 0.01;
            interest = (double) Math.round(interest * 100) / 100;
            
            String updatequery = "UPDATE Market_Acc SET balance = balance + ? WHERE tax_id = ?";

            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(updatequery)){
          
            pstmt.setDouble(1, interest);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
                
            conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            
            MarketGod m = new MarketGod();
            String date = m.getDate();
            
            Transaction t = new Transaction();
            t.interestTransaction(date, id, interest);
            /////////////////////////////////////////////////////////////

        }

    }

    public Object[][] gen_month(int tax_id){
        int size = 0;
        String q1 = "SELECT COUNT(*) as count \n" +
        "FROM Transactions \n" +
        "WHERE tax_id = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q1)){
            pstmt.setInt(1,tax_id);
            ResultSet r = pstmt.executeQuery();
            while(r.next()){
                size = r.getInt("count");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Object[][] trans = new Object[size][6];
        String q2 = "SELECT type, date, stock_sym, num_shares, price, earn \n" +
        "FROM Transactions \n" +
        "WHERE tax_id = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q2)){
            pstmt.setInt(1,tax_id);
            ResultSet r = pstmt.executeQuery();
            for(int i = 0; i < size; i++)
            {  
                r.next();
                trans[i][0] = r.getString("date");
                trans[i][1] = r.getString("type");
                if(r.getString("type") == "wit" || r.getString("type") == "dep" || r.getString("type") == "int")
                {
                    trans[i][2] = "";
                    trans[i][3] = "";
                    trans[i][4] = "";
                }
                else
                {
                    trans[i][2] = r.getString("stock_sym");
                    trans[i][3] = r.getInt("num_shares");
                    trans[i][4] = r.getDouble("price");
                }
                trans[i][5] = r.getDouble("earn");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return trans;
    }
    
    public ArrayList<Integer> list_active(){
        String q = "SELECT tax_id \n" +
        "FROM (SELECT tax_id, SUM(num_shares) as shares \n"+
        "FROM Transactions \n"+
        "WHERE type = 'buy' OR type = 'sell' \n" +
        "GROUP BY tax_id) WHERE shares >= 1000";
        
        ArrayList<Integer> active = new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q)){
                ResultSet r = pstmt.executeQuery();
                while(r.next())
                {
                    active.add(r.getInt("tax_id"));
                }
                conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return active;
    }

    public ArrayList<Integer> DTER(){
        String q = "SELECT tax_id \n" +
        "FROM(SELECT tax_id, SUM(earn) as profit \n" +
        "FROM Transactions \n" +
        "GROUP BY tax_id) WHERE profit > 10000";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q)){
            ResultSet r = pstmt.executeQuery();
            while(r.next())
            {
                ids.add(r.getInt("tax_id"));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ids;
    }

    public Object[][] cust_report(int tax_id){
        //Get market account and balance 
        int size = 0;
        String q1 = "SELECT COUNT(*) as count \n" +
        "FROM Stock_Acc \n"+
        "WHERE tax_id = ? ";

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q1)){
            pstmt.setInt(1,tax_id);
            ResultSet r = pstmt.executeQuery();
            while(r.next())
            {
                size = r.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Object[][] rep = new Object[size+1][3];
        String q2= "SELECT balance \n" +
        "FROM Market_Acc \n" +
        "WHERE tax_id = ? ";

        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q2)){
            pstmt.setInt(1,tax_id);
            ResultSet r = pstmt.executeQuery();
            r.next();
            rep[0][0] = "Market";
            rep[0][1] = r.getDouble("balance");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String q3 = "SELECT shares, aid, buy_pr \n" +
        "FROM Stock_Acc \n" +
        "WHERE tax_id = ? ";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q3)){
            pstmt.setInt(1,tax_id);
            ResultSet r = pstmt.executeQuery();
            for(int i = 1; i < size+1; i++)
            {
                r.next();
                rep[i][0] = r.getInt("shares");
                rep[i][1] = r.getString("aid");
                rep[i][2] = r.getDouble("buy_pr");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rep;
    }

    public void delete_trans(){
        String q1 = "DELETE FROM Transactions";
        String q2 = "DELETE FROM Balances";
        try (Connection conn = DriverManager.getConnection(Main.url);
            Statement s = conn.createStatement()){
                s.execute(q1);
                s.execute(q2);
                System.out.println("Deleted!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

}