package Yahtzee;

// Represents the Yahtzee table with five rolling dice

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.Timer;
import java.util.Scanner;

public class YahtzeeTable extends JPanel
                        implements ActionListener
{
  private RollingDie die1, die2, die3, die4, die5;
  private final int delay = 20;
  private Timer clock;
  private DisplayPanel display;
  static int rollsLeft = 3;
  private int yahtzeeCount = 0;
  private int total = 0;
  private int rollScore = 0;
  
  int[] diceFacesArray = new int[5];
  int[] numOfEachFace = new int[6];
  
  private String[] upperScoreOptions = {"Aces", "Twos", "Threes", "Fours", "Fives", "Sixes",};
  private String[] lowerScoreOptions = {"3 of a kind", "4 of a kind", "Full house", "Small straight", "Large straight", "Yahtzee", "Chance"};

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
   rollsLeft--;
   display.update(rollsLeft);
    RollingDie.setBounds(3, getWidth() - 3, 3, getHeight() - 3);
    die1.roll();
    die2.roll();
    die3.roll();
    die4.roll();
    die5.roll();
    clock.start();
  }

  // Processes timer events
  public void actionPerformed(ActionEvent e)
  {
    if (diceAreRolling())
    {
      if (!clock.isRunning())
      {
         clock.restart();
         die1.avoidCollision(die2);
         die1.avoidCollision(die3);
         die1.avoidCollision(die4);
         die1.avoidCollision(die5);
   
         die2.avoidCollision(die1);
         die2.avoidCollision(die4);
         die2.avoidCollision(die3);
         die2.avoidCollision(die5);
 
         die3.avoidCollision(die1);
         die3.avoidCollision(die2);
         die3.avoidCollision(die4);
         die3.avoidCollision(die5);
    
         die4.avoidCollision(die1);
         die4.avoidCollision(die2);
         die4.avoidCollision(die3);
         die4.avoidCollision(die5);

         die5.avoidCollision(die1);
         die5.avoidCollision(die2);
         die5.avoidCollision(die3);
         die5.avoidCollision(die4);
      }
   }
   else
   {
      clock.stop();
  
     
      
      while(die1.isRolling())
      {
        System.out.println("x");
      }
       diceFacesArray[0] = die1.getNumDots();
      diceFacesArray[1] = die2.getNumDots();
      diceFacesArray[2] = die3.getNumDots();
      diceFacesArray[3] = die4.getNumDots();
      diceFacesArray[4] = die5.getNumDots();
      for (int i = 0; i < 5; i++)
      {
         System.out.print(diceFacesArray[i] + ", ");
      }
      
    
         System.out.println("\nScore your roll in one of the following categories by entering the name of the combination. The number next to the combination indicates how many points you will receive for choosing that combo.");
         System.out.println("Or, if you have any rolls left, you can type 'reroll'. \nRolls left: " + rollsLeft);
         for (int i = 0 ; i < upperScoreOptions.length; i++)
         {
            if (!upperOrLower(upperScoreOptions[i]).equals(""))
            {
               System.out.println(upperScoreOptions[i] +  ": " + scoreRoll(upperScoreOptions[i]));
            }
         }
         
         for (int j = 0; j < lowerScoreOptions.length; j++)
         {
            if (!upperOrLower(lowerScoreOptions[j]).equals(""))
            {
               System.out.println(lowerScoreOptions[j] + ": " + scoreRoll(lowerScoreOptions[j]));
            }
         }
         System.out.println("Total score so far: " + total);
         
         Scanner reader = new Scanner(System.in);
         String input = reader.nextLine();
         if (input.equalsIgnoreCase("Reroll"))
         {
            if (rollsLeft > 0)
            {
               rollDice();
            }
            else
            {
               while (upperOrLower(input).equals(""))
               {
                  System.out.println("No more rerolls, choose an option to score");
                  input = reader.nextLine();
               }
            }
         }
         else if (!upperOrLower(input).equals(""))
         {
            if (upperOrLower(input).equals("Lower"))
            {
               if (!lowerScoreOptions[findIndex(input)].equalsIgnoreCase("Yahtzee") || (input.equalsIgnoreCase("Yahtzee") && yahtzeeCount >= 4))
               {
                  lowerScoreOptions[findIndex(input)] = "";
                  rollsLeft = 3;
               }
            }
            else if (upperOrLower(input).equals("Upper"))
            {
               upperScoreOptions[findIndex(input)] = "";
               rollsLeft = 3;
            }
         }
      }

    repaint();
  }
    
  public String upperOrLower(String scoreOption)
  {
   for (int i = 0; i < upperScoreOptions.length; i++)
   {
      if (upperScoreOptions[i].equalsIgnoreCase(scoreOption))
      {
         return "Upper";
      }
   }
   for (int j = 0; j < lowerScoreOptions.length; j++)
   {
      if (lowerScoreOptions[j].equalsIgnoreCase(scoreOption))
      {
         return "Lower";
      }
   }
   return "";
  }
  
  public int findIndex(String scoreOption)
  {
   if (upperOrLower(scoreOption).equals("Upper"))
   {  
      for (int i = 0; i < upperScoreOptions.length; i++)
      {
         if (upperScoreOptions[i].equals(scoreOption))
         {
            return i;
         }
      }
   }
   else if (upperOrLower(scoreOption).equals("Lower"))
   {
      for (int j = 0; j < lowerScoreOptions.length; j++)
      {
         if (lowerScoreOptions[j].equals(scoreOption))
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
   
   /*if (index == -1)
   {
      return -1;
   }
   */
   
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
            yahtzeeCount++;
            if (yahtzeeCount >= 2 && yahtzeeCount <= 4)
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
   if (index == 0 || index == 1)
   {
      for (int i = 0; i < 6; i++)
      {
         if (numOfEachFace[i] >= (index + 3))
         {
            return true;
         }
      }
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
   else if (index == 3)
   {
      boolean passed = true;
      for (int i = 0; i < 5; i++)
      {
         if (numOfEachFace[i] >= 1)
         {
            return true;
         }
      }
      
      for (int j = 1; j < 6; j++)
      {
         if (numOfEachFace[j] >= 1)
         {
            return true;
         }
      }
      return false;
   }
   else if (index == 4)
   {
      for (int i = 0; i < 6; i++)
      {
         if (numOfEachFace[i] != 1)
         {
            return false;
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
    System.out.println("Die: " + die1.getNumDots() + " " + die2.getNumDots() + " " +die3.getNumDots() + " " + die4.getNumDots() + " " + die5.getNumDots() );
    die1.draw(g);
    die2.draw(g);
    die3.draw(g);
    die4.draw(g);
    die5.draw(g);
  }
}