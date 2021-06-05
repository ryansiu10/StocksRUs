package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class showTransactions extends JFrame{
    JPanel panels, panel;
    JLabel amount;
    JTextField amt;
    JButton dep, back;

    public showTransactions(JPanel panels) {
        this.panels = panels;

        amount = new JLabel("Enter the amound you would like to deposit: ", SwingConstants.CENTER);
        amt = new JTextField();

        dep = new JButton("Deposit");

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
