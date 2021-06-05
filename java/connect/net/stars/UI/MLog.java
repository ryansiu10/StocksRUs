package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.stars.entities.Manager;

public class MLog extends JFrame{
    JPanel panels;
    JPanel panel;

    JLabel user, pass;
    JTextField user_field;
    JPasswordField pass_field;
    JButton login, back;

    public MLog(JPanel panels){
        this.panels = panels;
        
        user = new JLabel("Username: ", SwingConstants.CENTER);
        user_field = new JTextField();

        pass = new JLabel("Password: ", SwingConstants.CENTER);
        pass_field = new JPasswordField();
        
        //back button
        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"F");
            }
        });

        login = new JButton("Login");
        //login button event 
        login.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String user = user_field.getText();
                String pass = String.valueOf(pass_field.getPassword());
                Manager man = new Manager();
                int t = man.log(user,pass);
                if(t != -9999)
                {
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel man_int = new MI(panels, t).panel;
                    panels.add(man_int,"MI");
                    c.show(panels,"MI");
                }
            }
        });
        panel = new JPanel(new GridLayout(3,2));
        panel.add(user);
        panel.add(user_field);
        panel.add(pass);
        panel.add(pass_field);
        panel.add(back);
        panel.add(login);
        }
}   

