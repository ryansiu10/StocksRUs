package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.lang.model.util.ElementScanner14;
import javax.swing.*;

import net.stars.entities.*;

public class Reg extends JFrame{
    JPanel panels;
    JPanel panel;
    JLabel name, user, pass, add, state, phone, email, t_id;
    JTextField n, u, pw, a, s, ph, em, t;
    JButton reg, back;
    
    public Reg(JPanel panels)
    {
        this.panels = panels;
        name = new JLabel("Full name: ");
        n = new JTextField();
        user = new JLabel("Username: ");
        u = new JTextField();
        pass = new JLabel("Password: ");
        pw = new JTextField();
        add = new JLabel("Street address: ");
        a = new JTextField();
        state = new JLabel("State: ");
        s = new JTextField();
        phone = new JLabel("Phone Number: ");
        ph = new JTextField();
        email = new JLabel("Email: ");
        em = new JTextField();
        t_id = new JLabel("Tax ID: ");
        t = new JTextField();
        
        reg = new JButton("Submit");
        //register the account with button 
        reg.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Customer c = new Customer();
                //Market_Account m = new Market_Account(Integer.parseInt(t.getText()));
                if (c.reg(n.getText(),u.getText(),pw.getText(),a.getText(),s.getText(),Integer.parseInt(ph.getText()),em.getText(),Integer.parseInt(t.getText())))
                {
                    System.out.println("Succesfully Registered!");
                }
                else{
                    System.out.println("Error with registration!");
                }
                CardLayout car = (CardLayout)panels.getLayout();
                car.show(panels,"F");
            }   
        });
        
        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"MI");
            }
        });
        
        panel = new JPanel(new GridLayout(9,2));
        panel.add(name);
        panel.add(n);
        panel.add(user);
        panel.add(u);
        panel.add(pass);
        panel.add(pw);
        panel.add(add);
        panel.add(a);
        panel.add(state);
        panel.add(s);
        panel.add(phone);
        panel.add(ph);
        panel.add(email);
        panel.add(em);
        panel.add(t_id);
        panel.add(t);
        panel.add(back);
        panel.add(reg);
        
    }
    
}
