package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import net.stars.entities.*;

public class Sell extends JFrame{
    JPanel panels, panel;
    JLabel amount, sym;
    JTextField amt, sym_f;
    JButton sell, back;

    public Sell(JPanel panels,int tax_id) {
        this.panels = panels;
        sym = new JLabel("Enter stock symbol: ", SwingConstants.CENTER);
        sym_f = new JTextField();

        amount = new JLabel("Enter the number of shares: ", SwingConstants.CENTER);
        amt = new JTextField();

        sell = new JButton("Sell");
        sell.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String aid = sym_f.getText();
                int quantity = Integer.parseInt(amt.getText());
                StockAccount s = new StockAccount(tax_id);
                Stock stock = new Stock();
                double curr = stock.getStock(aid);
                s.sell(aid,quantity,curr);
                
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"TI");
            }
        });

        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"TI");
            }
        });
        panel = new JPanel(new GridLayout(3,2));
        
        panel.add(sym);
        panel.add(sym_f);
        panel.add(amount);
        panel.add(amt);
        panel.add(back);
        panel.add(sell);
    }
}
