package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import net.stars.entities.*;

public class Buy extends JFrame{
    JPanel panels, panel;
    JLabel sym, amount;
    JTextField sym_f, amt;
    JButton buy, back;

    public Buy(JPanel panels, int tax_id) {
        this.panels = panels;

        sym = new JLabel("Enter stock symbol: ", SwingConstants.CENTER);
        sym_f = new JTextField();
        amount = new JLabel("Enter the number of shares: ", SwingConstants.CENTER);
        amt = new JTextField();

        buy = new JButton("Buy");
        buy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String aid = sym_f.getText();
                int quantity = Integer.parseInt(amt.getText());
                StockAccount s = new StockAccount(tax_id);

                s.buy(aid, quantity);

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
        panel.add(buy);
        
    }
}
