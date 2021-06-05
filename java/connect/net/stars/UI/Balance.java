package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import net.stars.entities.*;

public class Balance extends JFrame{
    JPanel panels, panel;
    JLabel amount;
    JButton back;

    public Balance(JPanel panels, int tax_id) {
        this.panels = panels;

        Market_Account m = new Market_Account(tax_id);
        double balance = m.getBal();
        amount = new JLabel("Balance: " + balance, SwingConstants.CENTER);
        System.out.println("Showing Balance");

        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"TI");
            }
        });

        panel = new JPanel(new GridLayout(3,2));
        panel.add(amount);
        panel.add(back);
    }
}
