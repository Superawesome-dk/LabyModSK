package dk.superawesome.labymodsk.classes;

import net.labymod.serverapi.Addon;

import java.util.List;

public class Addons {
    private List<Addon> Addons;

    public Addons(List<Addon> id) {
        this.Addons = id;
    }

    @Override
    public String toString() {
        return Addons.get(0).getName();
    }

}
