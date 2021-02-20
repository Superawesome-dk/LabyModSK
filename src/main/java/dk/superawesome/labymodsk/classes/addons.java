package dk.superawesome.labymodsk.classes;

public class addons {
    private String MessageKey;

    public addons(String id) {
        this.MessageKey = id;
    }
    @Override
    public String toString() {
        return MessageKey.toString();
    }
}
