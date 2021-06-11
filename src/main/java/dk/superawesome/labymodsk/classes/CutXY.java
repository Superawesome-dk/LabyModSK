package dk.superawesome.labymodsk.classes;

public class CutXY {

    /**
     * Anchor x position in percent.<br><br>Examples:<br> 0% would be on the left side of the screen.<br>50% is in the middle of the screen.
     */
    private final int x;

    /**
     * Anchor y position in percent<br><br>Examples:<br> 0% would be on the top side of the screen.<br>50% is in the middle of the screen.
     */
    private final int y;

    /**
     * Create anchor for orientation
     *
     * @param x Anchor x percentage (0 - 100)
     * @param y Anchor y percentage (0 - 100)
     */
    public CutXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the anchor x percentage
     *
     * @return Anchor x percentage
     */
    public int getX() {
        return x;
    }

    /**
     * Get the anchor y percentage
     *
     * @return Anchor y percentage
     */
    public int getY() {
        return y;
    }
}
