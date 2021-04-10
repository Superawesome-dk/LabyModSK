package dk.superawesome.labymodsk.classes;

public class packages {
    private String MessageKey;

    public packages(String id) {
        this.MessageKey = id;
    }
    @Override
    public String toString() {
        return MessageKey.toString();
    }
}
