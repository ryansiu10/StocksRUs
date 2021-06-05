package net.stars.UI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import net.stars.entities.Manager;

public class addInt extends JFrame {
    JPanel panels;
    JPanel panel;

    JLabel set_int;
    JTextField rate;
    JButton add_int,back;

    public addInt(JPanel panels){
        this.panels = panels;
        set_int = new JLabel("Set Interest Rate (%): ", SwingConstants.CENTER);
        rate = new JTextField();

        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"MI");
            }
        });

        add_int = new JButton("Add Interest"); 
        panel = new JPanel(new GridLayout(2,2));
        panel.add(set_int);
        panel.add(rate);
        panel.add(back);
        panel.add(add_int);
    }
}
