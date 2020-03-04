// Represents a control panel for a Yahtzee "table"

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.*;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

public class ControlPanel extends JPanel
    implements ActionListener
{
  private YahtzeeTable table;
  private String[] scores = {"Score", "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes", "3 of a kind", "4 of a kind", "Full house", "Small straight", "Large straight", "Yahtzee", "Chance"};


  // Constructor
  public ControlPanel(YahtzeeTable t)
  {
    table = t;

    JButton rollButton = new JButton("Roll");
    rollButton.addActionListener(this);

  //AD added drop down box for scoring
    JComboBox scoreList = new JComboBox(scores);
    scoreList.setSelectedIndex(0);
    scoreList.addActionListener(this);
 
    add(rollButton);
    add(scoreList);
  }

  public int findIndex(String choice)
  {
   for (int i = 0; i < 12; i++)
   {
      if (scores[i].equals(choice))
      {
         return i;
      }
   }
   return 0;
  }

  // Called when the roll button is clicked
  public void actionPerformed(ActionEvent e)
  {
   //AD detect drop down
    if(e.getActionCommand().equals("comboBoxChanged"))
    {
        JComboBox cb = (JComboBox)e.getSource();
        String choice = (String)cb.getSelectedItem();
        if (table.findIndex(choice) == -1)
        {
         JOptionPane.showMessageDialog(null, 
                              "Choose an option that has not been chosen yet.");
        }
        else if (table.started == false)
        {
         JOptionPane.showMessageDialog(null, 
                              "Roll dice before trying to score.");
        }
        else
        {
           table.total+=table.scoreRoll(choice);
           if (choice.equals("Aces") || choice.equals("Twos") || choice.equals("Threes") || choice.equals("Fours") || choice.equals("Fives") || choice.equals("Sixes"))
           {
            table.upperSectionTotal += table.scoreRoll(choice);
           }
           for (int j = 0; j < 2; j++)
           {
               for (int i = 0; i < 7; i++)
               {
                  if (table.scoreOptions[j][i].equalsIgnoreCase(choice) || (choice.equals("Yahtzee") && (table.yahtzeeCount > 4 || table.scoreRoll(choice) == -1)))
                  {
                     table.scoreOptions[j][i] = "";
                     scores[findIndex(choice)] = "";
                  }
                  else if (choice.equals("Yahtzee"))
                  {
                     table.yahtzeeCount++;
                  }
               }
            }
            table.started = false;
           if (table.gameOver())
           {
               if (table.upperSectionTotal >= 63)
               {
                  table.total+= 35;
               }
               String name = (String)JOptionPane.showInputDialog(
               null,  "Game over, you scored " + table.total + " points. \nEnter your name to add to the scores table here: ", 
               "Game over",
               JOptionPane.PLAIN_MESSAGE, null, null, "name");
               try
               {
                   String filename= "scores.txt";
                   FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                   fw.write(name + ": " + table.total + "\n");//appends the string to the file
                   fw.close();
                   System.exit(0);
                }
                catch(IOException ioe)
               {
                   System.err.println("IOException: " + ioe.getMessage());
               }
            }
           table.rollsLeft = 3;
           table.display.update(table.rollsLeft, table.total, table.upperSectionTotal);
       }
    }
    else
    {
     if (!table.diceAreRolling())
     {
      table.die1Roll = true;
      table.die2Roll = true;
      table.die3Roll = true;
      table.die4Roll = true;
      table.die5Roll = true;
      
      if (!table.started)
      {
         table.rollDice();
      }
      else
      {
         try
         {
            if (table.rollsLeft == 0)
             {
               JOptionPane.showMessageDialog(null, 
                                       "Out of rerolls; must score an option.");
             }
            else
            {
               
               String c = (String)JOptionPane.showInputDialog(
                        null, "Enter the die numbers of the dice you wish to keep, or leave blank for none. \n Red = 1, Orange = 2, Yellow = 3, Green = 4, Blue = 5",
                        "Reroll",
               JOptionPane.PLAIN_MESSAGE, null, null, "");
               if (c == null)
               {
                 return;
               }
               if (!c.equalsIgnoreCase(""))
               {
                  while (!(c.equals("") || c.contains("1") || c.contains("2") || c.contains("3") || c.contains("4") || c.contains("5")))
                  {
                     c = (String)JOptionPane.showInputDialog(
                           null, "Enter valid input",
                           "Error",
                     JOptionPane.PLAIN_MESSAGE, null, null, "");
                     if (c == null)
                     {
                        return;
                     }
                  }
               }
               if (c.contains("1"))
               {  
                  table.die1Roll = false;
               }
               if (c.contains("2"))
               {  
                  table.die2Roll = false;
               }
               if (c.contains("3"))
               {  
                  table.die3Roll = false;
               }
               if (c.contains("4"))
               { 
                  table.die4Roll = false;
               }
               if (c.contains("5"))
               {  
                  table.die5Roll = false;
               }
               
            }
         }
         catch (NullPointerException ex)
         {
            System.exit(0);
         }
         table.rollDice();
        }
     }
   }
  }
} 