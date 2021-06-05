package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.stars.entities.Customer;

public class TLog extends JFrame{
    JPanel panels;
    JPanel panel;

    JLabel user, pass;
    JTextField user_field;
    JPasswordField pass_field;
    JButton login, back;

    public TLog(JPanel panels){
        this.panels = panels;
        
        user = new JLabel("Username: ", SwingConstants.CENTER);
        user_field = new JTextField();

        pass = new JLabel("Password: ", SwingConstants.CENTER);
        pass_field = new JPasswordField();
        
        login = new JButton("Login");
        //login button eveent 
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = user_field.getText();
                String pass = String.valueOf(pass_field.getPassword());

                Customer c = new Customer();
                int id = c.log(user, pass);

                if (id != -9999) {

                    CardLayout ca = (CardLayout)panels.getLayout();
                    JPanel tr_int = new TI(panels,id).panel;
                    panels.add(tr_int, "TI");
                    ca.show(panels, "TI");
                }
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
        panel.add(user);
        panel.add(user_field);
        panel.add(pass);
        panel.add(pass_field);
        panel.add(back);
        panel.add(login);
    }

}
