public class Die {
    private int numDots;

    public Die() {
        roll();
    }

    public void roll() {
        numDots = (int) (Math.random() * 6) + 1;
    }
    public int getNumDots() {
        return numDots;
    }
}