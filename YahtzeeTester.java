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

public class YahtzeeTester
{
   static String scoresLeft = "";
   static String choice = "Yahtzee";
   static int total = 0;
   public static void main(String[] args)
   {
      Color dieColor = Color.RED;
      DisplayPanel display = new DisplayPanel(dieColor);
      YahtzeeTable table = new YahtzeeTable(display, dieColor);
      ControlPanel controls = new ControlPanel(table);
      
         for (int i = 0; i < 5; i++)
         {
            table.diceFacesArray[i] = 1;
         }
         
         for (int j = 0; j < 6; j++)
         {
            table.numOfEachFace[j] = 0;
            
            for (int i = 0; i < 5; i++)
            {
               if (table.diceFacesArray[i] == j + 1)
               {
                  table.numOfEachFace[j]++;
               }
            }
         }
      table.scoreRoll("Aces");
      for (int j = 0; j < 2; j++)
         {
            for (int i = 0; i < 7; i++)
            {
               if (table.scoreOptions[j][i].equalsIgnoreCase("Aces") || choice.equals("Yahtzee") && (table.yahtzeeCount > 4 || table.scoreRoll(choice) == 0))
               {
                  table.scoreOptions[j][i] = "";
               }
            }
         }

      
      
      for (int l = 0; l < 2; l++)
      {
         scoresLeft = "";
         System.out.println("\n" + table.yahtzeeCount + " Yahtzees so far");
         
         for (int i = 0 ; i < 2; i++)
         {     
            for (int j = 0; j < 7; j++)
            {
               if (!table.upperOrLower(table.scoreOptions[i][j]).equals(""))
               {
                  scoresLeft = scoresLeft + "\n" + (table.scoreOptions[i][j] + ": " + table.scoreRoll(table.scoreOptions[i][j]));
               }
            }
         }      
         System.out.println(scoresLeft);
         System.out.println(table.upperOrLower(choice));
         total += table.scoreRoll(choice);
         table.yahtzeeCount++;
         
         //System.out.println(table.yahtzeeCount + " Yahtzees so far");
         System.out.println("Total: " + total);
         
         for (int j = 0; j < 2; j++)
         {
            for (int i = 0; i < 7; i++)
            {
               if (table.scoreOptions[j][i].equalsIgnoreCase(choice) && !choice.equals("Yahtzee"))
               {
                  table.scoreOptions[j][i] = "";
               }
               else if (choice.equals("Yahtzee"))
               {
                  if (table.yahtzeeCount == 4)
                  {
                     table.scoreOptions[j][i] = "";
                  }
               }
            }
         }
   
         
         for (int i = 0; i < 2; i++)
         {     
            for (int j = 0; j < 7; j++)
            {
               if (!table.upperOrLower(table.scoreOptions[i][j]).equals(""))
               {
                  scoresLeft = scoresLeft + "\n" + (table.scoreOptions[i][j] + ": " + table.scoreRoll(table.scoreOptions[i][j]));
               }
            }
         }     
      }
   }
   
   public void scoreYahtzee()
   {
      
   }
}