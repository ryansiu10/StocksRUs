package net.stars.UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import net.stars.entities.*;

public class stockdisplay extends JFrame{
    JPanel panels, panel;
    JLabel sym, mess;
    JTextField sym_f;
    JButton q, back;
    JScrollPane pane;
    JTable table;

    public stockdisplay(JPanel panels) {
        this.panels = panels;

        table = new JTable();
        pane = new JScrollPane(table);
        
        panel = new JPanel(new GridLayout(3,2));
        sym = new JLabel("Enter stock symbol: ", SwingConstants.CENTER);
        sym_f = new JTextField();

        back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                CardLayout c = (CardLayout)panels.getLayout();
                c.show(panels,"TI");
            }
        });
        
        q = new JButton("Submit");
        q.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Stock s = new Stock();
                double pr = 0;
                pr = s.getStock(sym_f.getText());
                mess = new JLabel("Current Price: $" + pr, SwingConstants.CENTER);
                Movie_Contract m = new Movie_Contract();

                String[] col = {"Title","Role", "Year", "Value"};
                Object[][] l = m.getMovieContractInfo(sym_f.getText());
                table = new JTable(l, col) {
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(25);
                table.getColumnModel().getColumn(2).setPreferredWidth(25);
                table.getColumnModel().getColumn(3).setPreferredWidth(150);
                
                // add new table and update panel
                panel.add(pane);
                panel.revalidate();
                panel.repaint();
            }
        });

        

        panel.add(back);
        panel.add(mess);
        panel.add(sym);
        panel.add(sym_f);
        panel.add(q);
        panel.add(pane);
    }
}
