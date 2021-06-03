public class Accounts{
    public int id_num;
    public List<Transactions> trans_list = new ArrayList<Transactions>();

    public Accounts(int id){
        id_num = id;
    }

}

public class Market_Account extends Accounts{
    int balance;
    
    public Market_Account(int id, int balance){
        super(id)
        this.balance = balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
}

public class Stock_Account extends Accounts{
    
}
