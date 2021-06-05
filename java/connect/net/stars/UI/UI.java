package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class UI extends JFrame{
    JPanel panels;
    JPanel first;

    JButton trader, manager, reg;
    JPanel manlog,trlog,reg_pg;

    public UI(){
        panels = new JPanel(new CardLayout());
        first = new JPanel(new GridLayout(3,1));
        
        trader = new JButton("Trader");
        manager = new JButton("Manager");
        reg = new JButton("Register");


        first.add(trader);
        first.add(manager);
        first.add(reg);

        manlog = new MLog(panels).panel;
        trlog = new TLog(panels).panel;
        reg_pg = new Reg(panels).panel;

        panels.add(manlog,"ML");
        panels.add(trlog,"TL");
        panels.add(reg_pg, "REG");
        panels.add(first,"F");

        trader.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"TL");
            }
        });

        manager.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"ML");
            }
        });

        reg.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"REG");
            }
        });

        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panels, BorderLayout.CENTER);
        setTitle("StarsRUs");
        setSize(800,400);
        setLocationRelativeTo(null);
        setVisible(true);
        CardLayout p = (CardLayout)panels.getLayout();
        p.show(panels,"F");
    }

    
}
