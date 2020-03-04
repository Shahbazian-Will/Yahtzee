// Represents a display panel for a Yahtzee table

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DisplayPanel extends JPanel
{
    private JTextField rollsLeftText, scoreText, upperScoreText;
    private int rollsLeft, currentTotal;

    // Constructor
    public DisplayPanel(Color dieColor)
    {
        super(new GridLayout(2, 3, 10, 0));
        setBorder(new EmptyBorder(0, 0, 5, 0));

        add(new JLabel("    Rerolls left:"));

        Font displayFont = new Font("Monospaced", Font.BOLD, 16);

        rollsLeftText = new JTextField("" + YahtzeeTable.rollsLeft, 5);
        rollsLeftText.setFont(displayFont);
        rollsLeftText.setEditable(false);
        rollsLeftText.setBackground(dieColor.brighter());
        add(rollsLeftText);
        
        add(new JLabel("    Total:"));
        
        scoreText = new JTextField("" + YahtzeeTable.total,5);
        scoreText.setFont(displayFont);
        scoreText.setEditable(false);
        scoreText.setBackground(dieColor.brighter());
        add(scoreText);

   }

    // Updates this display, based on
    public void update(int rollsLeft, int score, int upperScore) {
        rollsLeftText.setText("  " + rollsLeft);
        scoreText.setText(" " + score + "(" + upperScore + " upper)");;
    }
}
