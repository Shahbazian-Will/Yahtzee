import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.net.*;
import java.io.*;

public class Yahtzee extends JFrame
{
   Color dieColor = Color.RED;
   static URL rulesLink;
      
   public Yahtzee()
   {
    super("Yahtzee");
    
    DisplayPanel display = new DisplayPanel(dieColor);
    YahtzeeTable table = new YahtzeeTable(display, dieColor);
    ControlPanel controls = new ControlPanel(table);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBorder(new EmptyBorder(0, 5, 0, 5));
    panel.add(display, BorderLayout.NORTH);
    panel.add(table, BorderLayout.CENTER);
    panel.add(controls, BorderLayout.SOUTH);

    Container c = getContentPane();
    c.add(panel, BorderLayout.CENTER);
  }

  public static void main(String[] args)
  {
   JOptionPane.showMessageDialog(null, 
                              "Rules \nScore each roll by selecting one of the available score options from the drop down box. \nThe remaining valid options will be printed out for you. \nYou can only select each score option once, except for Yahtzee, which can be selected four times, with the first score being worth 50 points and each additional score worth 100. \nYou have the option to reroll dice up to three times between scoring rolls. \nThere is a 35 point bonus if the total of the upper section is at least 63. \nThe upper section consists of the score options Aces-Sixes.");
    try
   {
      rulesLink = new URL("http://grail.sourceforge.net/demo/yahtzee/rules.html");  
   }
   catch (MalformedURLException error)
   {
      rulesLink = null;
   }
    System.out.println("Full rules: ");
    System.out.print(rulesLink);
    Yahtzee window = new Yahtzee();
    window.setBounds(100, 100, 320, 240);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE);
    window.setVisible(true);
  }
}