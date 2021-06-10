package dk.superawesome.labymodsk.classes;

public class OffSet {

    /**
     * Anchor x position in percent.<br><br>Examples:<br> 0% would be on the left side of the screen.<br>50% is in the middle of the screen.
     */
    private final double x;

    /**
     * Anchor y position in percent<br><br>Examples:<br> 0% would be on the top side of the screen.<br>50% is in the middle of the screen.
     */
    private final double y;

    /**
     * Create anchor for orientation
     *
     * @param x Anchor x percentage (0 - 100)
     * @param y Anchor y percentage (0 - 100)
     */
    public OffSet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the anchor x percentage
     *
     * @return Anchor x percentage
     */
    public double getX() {
        return x;
    }

    /**
     * Get the anchor y percentage
     *
     * @return Anchor y percentage
     */
    public double getY() {
        return y;
    }
}
