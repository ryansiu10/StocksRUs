package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.stars.entities.*;

public class With extends JFrame{
    JPanel panels, panel;
    JLabel amount;
    JTextField amt;
    JButton wit, back;

    public With(JPanel panels, int tax_id) {
        this.panels = panels;

        amount = new JLabel("Enter the amound you would like to withdraw: ", SwingConstants.CENTER);
        amt = new JTextField();

        wit = new JButton("Withdraw");
        wit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Market_Account m = new Market_Account(tax_id);
                m.withdraw(Double.parseDouble(amt.getText()));
                System.out.println("Withdrew "+ amt.getText());
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
        panel.add(wit);
        panel.add(back);
    }
}
