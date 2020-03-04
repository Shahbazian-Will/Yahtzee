// Represents the Yahtzee table with five rolling dice

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.Timer;
import java.util.Scanner;
import javax.swing.*;

public class YahtzeeTable extends JPanel
                        implements ActionListener
{
  private RollingDie die1, die2, die3, die4, die5;
  private final int delay = 20;
  private Timer clock;
  static DisplayPanel display;
  static int rollsLeft = 3;
  static int yahtzeeCount = 0;
  static int total = 0;
  private int rollScore = 0;
  private String displStr;
  private String scoresLeft;
  private String diceFaces;
  static boolean started = false;
  static int upperSectionTotal = 0;
  static boolean die1Roll, die2Roll, die3Roll, die4Roll, die5Roll;
  
  int[] diceFacesArray = new int[5];
  int[] numOfEachFace = new int[6];
  
  static String[][] scoreOptions = {{"Aces", "Twos", "Threes", "Fours", "Fives", "Sixes", ""},{"3 of a kind", "4 of a kind", "Full house", "Small straight", "Large straight", "Yahtzee", "Chance"}};

  // Constructor
  public YahtzeeTable(DisplayPanel displ, Color dieColor)
  {
    setBackground(Color.decode("#477148"));
    setBorder(new LineBorder(Color.ORANGE.darker(), 3));
    display = displ;
    die1 = new RollingDie(Color.RED, 24);
    die2 = new RollingDie(Color.ORANGE.darker(), 24);
    die3 = new RollingDie(Color.YELLOW.darker(), 24);
    die4 = new RollingDie(Color.GREEN, 24);
    die5 = new RollingDie(Color.BLUE, 24);
    
    clock = new Timer(delay, this);
  }

  // Rolls the dice (called when the "Roll" button
  // is clicked)
  public void rollDice()
  {
   started = true;
    if (rollsLeft > 0) //AD only allow rolling if there are rollsLeft
    {
     rollsLeft--;
     display.update(rollsLeft, total, upperSectionTotal);
     RollingDie.setBounds(3, getWidth() - 3, 3, getHeight() - 3);
     
     if (die1Roll)
     {
      die1.roll();
     }
     if (die2Roll)
     {
      die2.roll();
     }
     if (die3Roll)
     {
      die3.roll();
     }
     if (die4Roll)
     {
      die4.roll();
     }
     if (die5Roll)
     {
      die5.roll();
     }
     
    clock.start();
    }
  }

  // Processes timer events
  public void actionPerformed(ActionEvent e)
  {
    if (diceAreRolling())
    {
      if (!clock.isRunning())
      {
         clock.restart();
      }
      if (die1.isRolling())
         die1.avoidCollision(die2);
         die1.avoidCollision(die3);
         die1.avoidCollision(die4);
         die1.avoidCollision(die5);
      if (die2.isRolling())
         die2.avoidCollision(die1);
         die2.avoidCollision(die4);
         die2.avoidCollision(die3);
         die2.avoidCollision(die5);
      if (die3.isRolling())
         die3.avoidCollision(die1);
         die3.avoidCollision(die2);
         die3.avoidCollision(die4);
         die3.avoidCollision(die5);
      if (die4.isRolling())
         die4.avoidCollision(die1);
         die4.avoidCollision(die2);
         die4.avoidCollision(die3);
         die4.avoidCollision(die5);
      if (die5.isRolling())
         die5.avoidCollision(die1);
         die5.avoidCollision(die2);
         die5.avoidCollision(die3);
         die5.avoidCollision(die4);
   }
   else
   {
      clock.stop();
  
      diceFacesArray[0] = die1.getNumDots();
      diceFacesArray[1] = die2.getNumDots();
      diceFacesArray[2] = die3.getNumDots();
      diceFacesArray[3] = die4.getNumDots();
      diceFacesArray[4] = die5.getNumDots();
            
      for (int j = 0; j < 6; j++)
      {
         numOfEachFace[j] = 0;
         
         for (int i = 0; i < 5; i++)
         {
            if (diceFacesArray[i] == j + 1)
            {
               numOfEachFace[j]++;
            }
         }
      }
      diceFaces = "\nYou rolled: ";
      for (int i = 0; i < 5; i++)
      {
         diceFaces = diceFaces + "" + diceFacesArray[i] + ", ";
      }
                                   
         displStr = "Score your roll in one of the following categories by entering the name of the combination. \nThe number next to the combination indicates how many points you will receive for choosing that combo.";
         scoresLeft = "";
         for (int i = 0 ; i < 2; i++)
         {     
            for (int j = 0; j < 7; j++)
            {
               if (!upperOrLower(scoreOptions[i][j]).equals(""))
               {
                  scoresLeft = scoresLeft + "\n" + (scoreOptions[i][j] + ": " + scoreRoll(scoreOptions[i][j]));
               }
            }
         }      
         displStr = displStr + scoresLeft;
         System.out.println(diceFaces + scoresLeft);
         
      }

    repaint();
  }
    
  public String upperOrLower(String scoreOption)
  {
   if (scoreOption.equals(""))
   {
      return "";
   }
   for (int j = 0; j < 2; j++)
   {
      for (int i = 0; i < 7; i++)
      {
         if (scoreOptions[j][i].equalsIgnoreCase(scoreOption))
         {
            if (j == 0)
            {
               return "Upper";
            }
            else
            {
               return "Lower";
            }
         }
      }
   }
   return "";
  }
  
  public int findIndex(String scoreOption)
  {
   if (upperOrLower(scoreOption).equals("Upper"))
   {  
      for (int i = 0; i < 7; i++)
      {
         if (scoreOptions[0][i].equals(scoreOption))
         {
            return i;
         }
      }
   }
   else if (upperOrLower(scoreOption).equals("Lower"))
   {
      for (int j = 0; j < 7; j++)
      {
         if (scoreOptions[1][j].equals(scoreOption))
         {
            return j;
         }
      }
   }
   return -1;
  }
 
  public int scoreRoll(String scoreOption)
  {
   int index = findIndex(scoreOption);
   rollScore = 0;
   
   if (upperOrLower(scoreOption).equals("Upper"))
   {
      int target = index + 1;
      for (int i = 0; i < 5; i++)
      {
         if (diceFacesArray[i] == target)
         {
            rollScore += target;
         }
      }
   }
   else if (upperOrLower(scoreOption).equals("Lower"))
   {  
      if ((index == 0 || index == 1 || index == 6) && meetsRequirements(index))
      {
         for (int i = 0; i < 5; i++)
         {
            rollScore += diceFacesArray[i];
         }
      }
      else if (index == 2 && meetsRequirements(index))
      {
         rollScore = 25;
      }
      else if (meetsRequirements(index))
      {
         rollScore += (10 * index);
         if (index == 5)
         {
            if (yahtzeeCount >= 1 && yahtzeeCount <= 4)
            {
               rollScore = 100;
            }
            else
            {
               rollScore = 50;
            }
         }
      }
   }
   return rollScore;
  }
  
  boolean meetsRequirements(int index) //Tests to see if the selected lower option requirements are met by the current dice roll
  {
   for (int i = 0; i < 6; i++)
   {
      if (numOfEachFace[i] == 5 && yahtzeeCount >= 1 && yahtzeeCount < 5 && scoreOptions[0][i].equals(""))
      {
         return true;
      }
   }
   if (index == 0 || index == 1)
   {
      for (int i = 0; i < 6; i++)
      {
         if (numOfEachFace[i] >= (index + 3))
         {
            return true;
         }
      }
      return false;
   }
   else if (index == 2)
   {
      for (int i = 0; i < 6; i++)
      {
         if (numOfEachFace[i] == 1 || numOfEachFace[i] == 4 || numOfEachFace[i] == 5)
         {
            return false;
         }
      }
      return true;
   }
   else if (index == 4)
   {
      for (int i = 0; i < 5; i++)
      {
         if (numOfEachFace[i] != 1)
         {
            return false;
         }
      }
   }
   else if (index == 3)
   {
      for (int i = 0; i < 4; i++)
      {
         if (numOfEachFace[i] < 1)
         {
            for (int j = 1; j < 5; j++)
            {
               if (numOfEachFace[j] < 1)
               {
                  for (int k = 2; k < 6; k++)
                  {
                     if (numOfEachFace[k] < 1)
                     {
                        return false;
                     }
                  }
               }
            }
         }
      }
      return true;
   }
   else if (index == 5)
   {
      for (int i = 0; i < 6; i++)
      {
         if (numOfEachFace[i] == 5)
         {
            return true;
         }
      }
      return false;
   }
   return true;
  }

  // returns true if dice are still rolling; otherwise
  // returns false
  public boolean diceAreRolling()
  {
    return die1.isRolling() || die2.isRolling() || die3.isRolling() || die4.isRolling() || die5.isRolling();
  }

  // Called automatically after a repaint request
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    die1.draw(g);
    die2.draw(g);
    die3.draw(g);
    die4.draw(g);
    die5.draw(g);
  }
  
  public boolean gameOver()
  {
   for (int i = 0; i < 2; i++)
   {     
      for (int j = 0; j < 7; j++)
      {
         if (!upperOrLower(scoreOptions[i][j]).equals(""))
         {
            return false;
         }
      }
   }      
   return true;
  }
}