package net.stars.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import net.stars.Main;

public class StockAccount{
    int tax_id;
    
    public StockAccount(int tax_id){
        this.tax_id = tax_id;
    }

    public boolean buy(String aid, int amt) {
        Market_Account m = new Market_Account(this.tax_id);
        double b = m.getBal();
        double pr = 0;

        String q1 = "SELECT price FROM Stocks WHERE aid = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q1)){
                pstmt.setString(1,aid);
                ResultSet r = pstmt.executeQuery();
                r.next();
                pr = r.getDouble("price");
                System.out.println(pr);
                conn.close();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
        }
        
        //check if you have enough bal 
        double total = pr*amt+20; 
        System.out.println(total);
        if(total > b)
            return false;
        //check if there is existing tuple 
        boolean exist = false;
        String q2= "SELECT COUNT(*) as count \n"
        + "FROM Stock_Acc \n"
        + "WHERE tax_id = ? AND aid = ? AND buy_pr = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q2)) {
            pstmt.setInt(1,this.tax_id);
            pstmt.setString(2,aid);
            pstmt.setDouble(3,pr);
            ResultSet r = pstmt.executeQuery();
            while(r.next())
            {
                int c = r.getInt("count");
                if(c == 1){
                    exist = true;
                }
            }
            conn.close();
        } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
        }
        
        double new_b = b-total;

        //update existing 
        if(exist)
        {
            String q3 = "UPDATE Stock_Acc SET shares = shares + ? \n "
            + "WHERE tax_id = ? AND aid = ? AND buy_pr = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q3)){
                pstmt.setInt(1,amt);
                pstmt.setInt(2,this.tax_id);
                pstmt.setString(3,aid);
                pstmt.setDouble(4,pr);
                pstmt.executeUpdate();
                conn.close();
            }  catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }   
                
        } else {
            //add new stock to account
            String q4 = "INSERT INTO Stock_Acc(tax_id, shares, aid, buy_pr) \n" +
            "VALUES(?,?,?,?);";
            try (Connection conn = DriverManager.getConnection(Main.url);
                PreparedStatement pstmt = conn.prepareStatement(q4)){
                    pstmt.setInt(1,this.tax_id);
                    pstmt.setInt(2,amt);
                    pstmt.setString(3,aid);
                    pstmt.setDouble(4,pr);
                    pstmt.executeUpdate();
                    conn.close();
                }catch (SQLException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
            }
        
        //subtract the amt from market_acc
        String q5 = "UPDATE Market_Acc SET balance = ? \n"+
        "WHERE tax_id = ? ";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(q5)){
            pstmt.setDouble(1,new_b);
            pstmt.setInt(2,this.tax_id);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

        MarketGod mar = new MarketGod();
        String d = mar.getDate();
        Transaction t = new Transaction();
        t.stockTransaction(tax_id, "buy", d, aid, amt, pr, total*-1);
        return true;
    }

    public boolean sell (String aid, int amt, double buy_pr) {
        String query = "SELECT shares FROM Stocks_Acc WHERE tax_id = ? AND aid = ? AND buy_pr = ?";
        int count = 0;
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, tax_id);
            pstmt.setString(2, aid);
            pstmt.setDouble(3, buy_pr);
            ResultSet res = pstmt.executeQuery();
            
            while (res.next()) 
                count = res.getInt("shares");
            
            conn.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
         //doesn't own at least 'amt' amount of stocks
        if (count < amt)
            return false;

        // current price
        double cur_price = 0;
        String q1 = "SELECT price FROM Stocks WHERE aid = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
        PreparedStatement pstmt = conn.prepareStatement(q1)){
            pstmt.setString(1,aid);
            ResultSet r = pstmt.executeQuery();
            r.next();
            cur_price = r.getDouble("price");
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        double gain = cur_price * amt - 20;
        
        
        // if need to delete tuple
        if (count == amt) {
            String del = "DELETE FROM Stock_Acc WHERE tax_id = ? AND aid = ? AND buy_pr = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(del)){
                pstmt.setInt(1, tax_id);
                pstmt.setString(2, aid);
                pstmt.setDouble(3, buy_pr);
                pstmt.executeUpdate();

                conn.close();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        // just updating (subtracting from amt)
        else {
            String upd = "UPDATE Stock_Acc SET shares = shares - ? WHERE tax_id = ? AND aid = ? AND buy_pr = ?";
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(upd)){
                pstmt.setInt(1, amt);
                pstmt.setInt(2, tax_id);
                pstmt.setString(2, aid);
                pstmt.setDouble(3, buy_pr);
                pstmt.executeUpdate();

                conn.close();
            }catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        //add profit money into account

        String prof = "UPDATE Market_Acc SET balance = balance + ? WHERE tax_id = ?";
        try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(prof)){
                pstmt.setDouble(1, gain);
                pstmt.setInt(2, tax_id);
                pstmt.executeUpdate();

                conn.close();
        }catch (SQLException e) {
                System.out.println(e.getMessage());
        }

        MarketGod m = new MarketGod();
        String date = m.getDate();
        Transaction t = new Transaction();
        t.stockTransaction(tax_id, "sel", date, aid, count, cur_price, gain);
        return true;
    }
}
    