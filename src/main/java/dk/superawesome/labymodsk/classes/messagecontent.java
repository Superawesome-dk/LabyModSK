package dk.superawesome.labymodsk.classes;

public class messagecontent {
    private String messagecontent;

    public messagecontent(String id) {
        this.messagecontent = id;
    }
    @Override
    public String toString() {
        return messagecontent.toString();
    }
}
