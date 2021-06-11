package dk.superawesome.labymodsk.classes;

public class CutWithHeight {

    /**
     * Anchor x position in percent.<br><br>Examples:<br> 0% would be on the left side of the screen.<br>50% is in the middle of the screen.
     */
    private final int width;

    /**
     * Anchor y position in percent<br><br>Examples:<br> 0% would be on the top side of the screen.<br>50% is in the middle of the screen.
     */
    private final int height;

    /**
     * Create anchor for orientation
     *
     * @param width Anchor x percentage (0 - 100)
     * @param y Anchor y percentage (0 - 100)
     */
    public CutWithHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Get the anchor x percentage
     *
     * @return Anchor x percentage
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the anchor y percentage
     *
     * @return Anchor y percentage
     */
    public int getHeight() {
        return height;
    }
}
