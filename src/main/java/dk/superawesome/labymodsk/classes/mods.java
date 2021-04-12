package dk.superawesome.labymodsk.classes;

public class mods {
    private String MessageKey;

    public mods(String id) {
        this.MessageKey = id;
    }
    @Override
    public String toString() {
        return MessageKey.toString();
    }
}
