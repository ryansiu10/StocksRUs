package net.stars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import net.stars.entities.*;
import net.stars.UI.*;

public class Main{
    public static String url = "jdbc:sqlite:db/stars.db";
    // public static void connect() {
    //     Connection conn = null;
    //     try {
    //         // db parameters
            
    //         // create a connection to the database
    //         conn = DriverManager.getConnection(url);
            
    //         System.out.println("Connection has been established.");
            
    //     } catch (SQLException e) {
    //         System.out.println(e.getMessage());
    //     } finally {
    //         try {
    //             if (conn != null) {
    //                 conn.close();
    //             }
    //         } catch (SQLException ex) {
    //             System.out.println(ex.getMessage());
    //         }
    //     }
    // }
    
    public static void init_tables()
    {
        String market = "CREATE TABLE IF NOT EXISTS Market( \n"+
            "mk_status varchar(255) NOT NULL ,\n" +
            "date DATE NOT NULL \n" +
            ");";

        String manager = "CREATE TABLE IF NOT EXISTS Manager( \n"+
            "name varchar(255), \n" +
            "username varchar(255) UNIQUE, \n" +
            "password varchar(255),\n" +
            "address varchar(255),\n" +
            "state char(2),\n" +
            "phonenumber varchar(100), \n" +
            "email varchar(255), \n" + 
            "tax_id int(9) PRIMARY KEY\n" +
            ");";

        String customer = "CREATE TABLE IF NOT EXISTS Customer( \n"+
            "name varchar(255), \n" +
            "username varchar(255) UNIQUE, \n" +
            "password varchar(255),\n" +
            "address varchar(255),\n" +
            "state char(2),\n" +
            "phonenumber varchar(100), \n" +
            "email varchar(255), \n" + 
            "tax_id int(9) PRIMARY KEY\n" +
            ");";

      
        String market_acc = "CREATE TABLE IF NOT EXISTS Market_Acc( \n" + 
            "tax_id int NOT NULL PRIMARY KEY, \n" + 
            "balance real NOT NULL, \n" + 
            "FOREIGN KEY (tax_id) REFERENCES Customer(tax_id) \n" +
            "ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";
        
        String stock_acc = "CREATE TABLE IF NOT EXISTS Stock_Acc( \n" +
            "tax_id int NOT NULL,\n"+
            "shares int NOT NULL,\n" + 
            "aid char(3) NOT NULL,\n" +
            "buy_pr real NOT NULL, \n" +
            "PRIMARY KEY(tax_id,shares,aid), \n" +
            "FOREIGN KEY(tax_id) REFERENCES Customer(tax_id),\n" +
            "FOREIGN KEY(aid) REFERENCES Actors(aid)\n" +
            ");";
            
        String transactions = "CREATE TABLE IF NOT EXISTS Transactions( \n" +
            "tax_id int NOT NULL, \n" + 
            "type char(3) NOT NULL, \n" +
            "date DATE NOT NULL, \n" +
            "stock_sym char(3), \n" +
            "num_shares int, \n" +
            "price real, \n" +
            "earn real NOT NULL ,\n" +
            "PRIMARY KEY (tax_id, stock_sym), \n" +
            "FOREIGN KEY (tax_id) REFERENCES Customer(tax_id) \n" +
            "FOREIGN KEY (stock_sym) REFERENCES Actors(aid) \n" +
            ");";
        
        String balances = "CREATE TABLE IF NOT EXISTS Balances( \n"+
            "tax_id int NOT NULL, \n"+
            "balance real NOT NULL, \n "+
            "date DATE NOT NULL \n" +
            ");";

        String stocks = "CREATE TABLE IF NOT EXISTS Stocks( \n" +
            "aid char(3) NOT NULL ,\n" + 
            "price real NOT NULL, \n"+
            "date DATE NOT NULL, \n" +
            "PRIMARY KEY (aid,date) ,\n" +
            "FOREIGN KEY (aid) REFERENCES Actors(aid) \n"+
            "ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";

        String actors = "CREATE TABLE IF NOT EXISTS Actors( \n" +
            "aid char(3) NOT NULL PRIMARY KEY, \n"+
            "name varchar(255) NOT NULL, \n"+
            "dob DATE NOT NULL \n"+
            ");";

        String movies = "CREATE TABLE IF NOT EXISTS Movies( \n" +
            "title char NOT NULL PRIMARY KEY, \n" +
            "prod_year int NOT NULL, \n" + 
            "ranking real NOT NULL, \n" + 
            "rev int NOT NULL \n" +
            ");";

        String movie_Contracts = "CREATE TABLE IF NOT EXISTS Movie_Contracts( \n" +
            "aid char(3) NOT NULL PRIMARY KEY,\n" +
            "movie_title char NOT NULL, \n" +
            "role char NOT NULL, \n" +
            "year int NOT NULL, \n" +
            "tot_value real NOT NULL, \n" +
            "FOREIGN KEY (aid) REFERENCES Actors(aid) \n" + 
            ");";
        
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement()) {
            stmt.execute(market);
            stmt.execute(balances);
            stmt.execute(manager);
            stmt.execute(customer);
            stmt.execute(market_acc);
            stmt.execute(stock_acc);
            stmt.execute(transactions);
            stmt.execute(stocks);
            stmt.execute(actors);
            stmt.execute(movies);
            stmt.execute(movie_Contracts);
            System.out.println("Tables set up!");
            conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public static void insert_data(){
            String insertManager= "INSERT INTO Manager(name, username, password, address, state, phonenumber, email, tax_id) \n" +
                "VALUES(\"John Admin\",\"admin\",\"secret\",\"Stock Company SB\",\"CA\",8056374632,\"admin@stock.com\",1000);" ;
            
            String insertCustomer = "INSERT INTO Customer(name, username, password, address, state, phonenumber, email, tax_id )\n" +
                "VALUES \n" +
                "(\"Alfred Hitchcock\",\"alfred\",\"hi\",\"6667 El Colegio #40 SB\",\"CA\",8052574499,\"alfred@hotmail.com\",1022), \n" +
                "(\"Billy Clinton\",\"billy\",\"cl\",\"5777 Hollister SB\",\"CA\",8055629999,\"billy@yahoo.com\",3045), \n" +
                "(\"Cindy Laugher\",\"cindy\",\"la\",\"7000 Hollister SB\",\"CA\",8056930011,\"cindy@hotmail.com\",2034), \n" +
                "(\"David Copperfill\",\"david\",\"co\",\"1357 State St SB\",\"CA\",8058240011,\"david@yahoo.com\",4093), \n" +
                "(\"Elizabeth Sailor\",\"sailor\",\"sa\",\"4321 State St SB\",\"CA\",8051234567,\"sailor@hotmail.com\",1234), \n" +
                "(\"George Brush\",\"brush\",\"br\",\"5346 Foothill Av\",\"CA\",8051357999,\"george@hotmail.com\",8956), \n" +
                "(\"Ivan Stock\",\"ivan\",\"st\",\"1235 Johnson Dr\",\"NJ\",8053223243,\"ivan@yahoo.com\",2341), \n" +
                "(\"Joe Pepsi\",\"joe\",\"pe\",\"3210 State St\",\"CA\",8055668123,\"pepsi@pepsi.com\",0456), \n" +
                "(\"Magic Jordon\",\"magic\",\"jo\", \"3852 Court Rd\",\"NJ\",8054535539,\"jordon@jordon.org\",3455), \n" +
                "(\"Olive Stoner\",\"olive\",\"st\",\"6689 El Colegio #151\",\"CA\",8052574499,\"olive@yahoo.com\",1123), \n" +
                "(\"Frank Olson\",\"frank\",\"ol\",\"6910 Whittier Dr\",\"CA\",8053456789,\"frank@gmail.com\",3306);";

            String insertMarketAcc = "INSERT INTO Market_Acc(tax_id, balance) \n" +
                "VALUES(1022, 10000), \n "+
                "(3045, 100000), \n" +
                "(2034,50000), \n" +
                "(4093,45000), \n" +
                "(1234,200000), \n" +
                "(8956,5000), \n" +
                "(2341,2000), \n" +
                "(0456,10000), \n" +
                "(3455,130200),\n"+
                "(1123,35000), \n" +
                "(3306,30500);";
 
            String insertStockAcc = "INSERT INTO Stock_Acc(tax_id, shares, aid, buy_pr) \n" +
                "VALUES \n" +
                "(1022, 100, \"SKB\", 40.00), \n" +
                "(3045, 500, \"SMD\", 71.00), \n" +
                "(3045, 100, \"STC\", 32.50), \n" +
                "(2034, 250, \"STC\", 32.50), \n" +
                "(4093, 100, \"SKB\", 40.00), \n" +
                "(4093, 500, \"SMD\", 71.00), \n" +
                "(4093, 50, \"STC\", 32.50), \n" +
                "(1234, 1000, \"SMD\", 71.00), \n" +
                "(8956, 100, \"SKB\", 40.00), \n" +
                "(2341, 300, \"SMD\", 71.00), \n" +
                "(0456, 500, \"SKB\", 40.00), \n" +
                "(0456, 100, \"STC\", 32.50), \n" +
                "(0456, 200, \"SMD\", 71.00), \n" +
                "(3455, 1000, \"SKB\", 40.00), \n" +
                "(1123, 100, \"SKB\", 40.00), \n" +
                "(1123, 100, \"SMD\", 71.00), \n" +
                "(1123, 100, \"STC\", 32.50), \n" +
                "(3306, 100, \"SKB\", 40.00), \n" +
                "(3306, 200, \"STC\", 32.50), \n" +
                "(3306, 100, \"SMD\", 71.00);";
            
            String insertStocks = "INSERT INTO Stocks(aid, price, date) \n" +
                "VALUES (\"SKB\", 40.00, \"2013-03-16\"), \n" +
                "(\"SMD\", 71.00, \"2013-03-16\") ,\n" +
                "(\"STC\", 32.50, \"2013-03-16\");";

            String insertActors = "INSERT INTO Actors(aid,name,dob) \n" +
                "VALUES (\"SKB\", \"Kim Basinger\",\"1958-12-08\"), \n" +
                "(\"SMD\", \"Michael Douglas\", \"1944-09-25\"), \n" +
                "(\"STC\", \"Tom Cruise\", \"1962-07-03\");";
            
            String insertMovies = "INSERT INTO Movies(title, prod_year, ranking, rev) \n" +
                "VALUES (\"Confidential\", 1997, 7.8, 842), \n"+
                "(\"A Perfect Murder\", 1998, 8.4, 1230), \n"+
                "(\"Jerry Maguire\", 1996, 6.3, 183);";

            String insertMoviesCon = "INSERT INTO Movie_Contracts(aid, movie_title, role, year, tot_value) \n" +
                "VALUES \n" +
                "(\"SKB\", \"Confidential\", \"Actor\", 1997, 5000000), \n" +
                "(\"SMD\", \"A Perfect Murder\", \"Actor\", 1998, 10000000), \n" +
                "(\"STC\", \"Jerry Maguire\", \"Actor\", 1996, 5000000);";
            
            try (Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement()) {
                stmt.execute(insertManager);
                stmt.execute(insertCustomer);
                stmt.execute(insertMarketAcc);
                stmt.execute(insertStockAcc);
                stmt.execute(insertStocks);
                stmt.execute(insertActors);
                stmt.execute(insertMovies);
                stmt.execute(insertMoviesCon);
                System.out.println("Data Added!");
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }   
        }
        
        public static void setInitDate(String date)
        {
            String query = "INSERT INTO Market VALUES(?,?); \n";
            try (Connection conn = DriverManager.getConnection(Main.url);
            PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setString(1,"open");
            pstmt.setString(2,date);
            pstmt.executeUpdate();
            conn.close();
            }
            catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        public static void delete_tables(){
            String[] tables = {"Market","Balances","Manager","Customer", "Market_Acc", "Stock_Acc", "Transactions","Stocks","Actors","Movies","Movie_Contracts"};
            for(String i:tables){
                String del = "DROP TABLE IF EXISTS " + i + ";";
                
                try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()){
                   stmt.execute(del);
                   conn.close();
                } catch (SQLException e) {
                System.out.println(e.getMessage());
            }  
            }
        }
  //execution of application
    public static void main(String[] args) {
        if(args[0].equals("setup")){
            delete_tables();
            init_tables();
            insert_data();
            setInitDate("2013-03-16");
        }
        else if(args[0].equals("run")){
            new UI();
        }
        else if(args[0].equals("test")){
            MarketGod mar = new MarketGod();
            mar.setDate("2013-03-28");
        }
        else if(args[0].equals("reset"))
        {
            delete_tables();
        }
        
    }
}