package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MI extends JFrame{
        JPanel panels;
        JPanel panel;

        JButton add_int, gen_month,list_act,dter,cust_r,del_t,back;

        public MI(JPanel panels, int tax_id){
            this.panels = panels;
            add_int = new JButton("Add Interest");
            add_int.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel a_intr = new addInt(panels).panel;
                    panels.add(a_intr,"Add_I");
                    c.show(panels,"Add_I");
                }
            });

            gen_month = new JButton("Generate Monthly Statement");
            gen_month.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel gm = new genMonth(panels,tax_id).panel;
                    panels.add(gm,"GM");
                    c.show(panels,"GM");
                }
            });

            list_act = new JButton("List Active Customers");
            list_act.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel l_act= new listAct(panels).panel;
                    panels.add(l_act,"LA");
                    c.show(panels,"LA");
                }
            });

            dter = new JButton("Generate DTER");
            dter.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel d = new DTER(panels).panel;
                    panels.add(d,"DTER");
                    c.show(panels,"DTER");
                }
            });

            cust_r = new JButton("Customer Report");
            cust_r.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel rep = new custRep(panels).panel;
                    panels.add(rep,"REP");
                    c.show(panels,"REP");
                }
            });

            del_t = new JButton("Delete Transactions");
            del_t.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    JPanel del_tr = new delTrans(panels).panel;
                    panels.add(del_tr,"DEL");
                    c.show(panels,"DEL");
                }
            });

            back = new JButton("Back");
            back.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    CardLayout c = (CardLayout)panels.getLayout();
                    c.show(panels,"F");
                }
            });
    
            panel = new JPanel(new GridLayout(4,2));
            panel.add(add_int);
            panel.add(gen_month);
            panel.add(list_act);
            panel.add(dter);
            panel.add(cust_r);
            panel.add(del_t);
        }
}