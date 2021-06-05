package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.stars.entities.Market_Account;

public class Depo extends JFrame{
    JPanel panels, panel;
    JLabel amount;
    JTextField amt;
    JButton dep, back;

    public Depo(JPanel panels,int tax_id) {
        this.panels = panels;

        amount = new JLabel("Enter the amound you would like to deposit: ", SwingConstants.CENTER);
        amt = new JTextField();

        dep = new JButton("Deposit");
        dep.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Market_Account m = new Market_Account(tax_id);
                m.deposit(Double.parseDouble(amt.getText()));
                System.out.println("Deposited "+ amt.getText());
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
        panel.add(amount);
        panel.add(amt);
        panel.add(dep);
        panel.add(back);
    }
}
