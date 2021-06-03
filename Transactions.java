public class Transactions{
    public Date date;
    public char stock_sym;
    public int num_shares;
    public float price;

    public Transactions(Date date, char stock_sym, int num_shares, float price) {
        this.date = date;
        this.stock_sym = stock_sym;
        this.num_shares = num_shares;
        this.price = price;

    }
    
    public void deposit(Market_Account MA, float money) {
        //add money to the MARKET account balance
        MA.balance += money;

    }

    public void withdraw(Market_Account MA, float money) {
        //subtract money from the MARKET account balance
        MA.balance -= money;

    }

    public void buy(int shares) {

    }

    public void sell(int shares) {

    }

    public void acc_interest(float money) {

    }

}