package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TI extends JFrame{
    /*
    Deposit
    Withdrawal
    Buy
    Sell
    Show balance for his/her market account
    Show transaction history for his/her stock account
    List current price of a stock and the actor profile
    List movie info

    public TI(JPanel panels){
        panel = new JPanel(new GridLayout(3,2));
        panel.add(user);
        panel.add(user_field);
        panel.add(pass);
        panel.add(pass_field);
        panel.add(new JLabel());
        panel.add(login);
    }
    */

    JPanel panels;
    JPanel panel;

    JLabel commands;
    JButton deposit, withdrawal, buy, sell, showBal, showTrans, stock, movieInfo, back;
    

    public TI(JPanel panels,int tax_id) {
        this.panels = panels;

        commands = new JLabel("Choose a command: ", SwingConstants.CENTER);

        deposit = new JButton("Deposit");
        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel dep = new Depo(panels,tax_id).panel;
                panels.add(dep, "DP");
                c.show(panels, "DP");
            }

        });

        withdrawal = new JButton("Withdrawal");
        withdrawal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel with = new With(panels, tax_id).panel;
                panels.add(with, "WD");
                c.show(panels, "WD");
            }

        });

        buy = new JButton("Buy Stocks");
        buy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel buyS = new Buy(panels,tax_id).panel;
                panels.add(buyS, "Buy");
                c.show(panels, "Buy");
            }

        });

        sell = new JButton("Sell Stocks");
        sell.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel sellS = new Sell(panels,tax_id).panel;
                panels.add(sellS, "Sell");
                c.show(panels, "Sell");
            }

        });

        showBal = new JButton("Show Balance");
        showBal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel bal = new Balance(panels, tax_id).panel;
                panels.add(bal, "BA");
                c.show(panels, "BA");
            }

        });

        showTrans = new JButton("Show Transaction History");
        showTrans.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel tr = new showTransactions(panels).panel;
                panels.add(tr, "TR");
                c.show(panels, "TR");
            }

        });

        stock = new JButton("Show Stock Info");
        stock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel pr = new stockdisplay(panels).panel;
                panels.add(pr, "ST");
                c.show(panels, "ST");
            }

        });

        movieInfo = new JButton("List Movie Information");
        movieInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)panels.getLayout();
                JPanel mI = new MovieInfo(panels).panel;
                panels.add(mI, "MO");
                c.show(panels, "MO");
            }

        });

        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"F");
            }
        });

        panel = new JPanel(new GridLayout(3,2));
        panel.add(deposit);
        panel.add(withdrawal);
        panel.add(buy);
        panel.add(sell);
        panel.add(showBal);
        panel.add(showTrans);
        panel.add(stock);
        panel.add(movieInfo);
        panel.add(back);

    }

}
