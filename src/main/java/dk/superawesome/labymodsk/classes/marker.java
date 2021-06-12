package dk.superawesome.labymodsk.classes;

import org.bukkit.Location;

import java.util.UUID;

public class marker {
    private final Location location;
    private final Boolean large;
    private final UUID sender;
    private UUID target;

    public marker(Location location, Boolean large, UUID sender, UUID target) {
        this.location = location;
        this.large = large;
        this.sender = sender;
        this.target = target;
    }

    public marker(Location location, Boolean large, UUID sender) {
        this.location = location;
        this.large = large;
        this.sender = sender;
    }

    public Location getLocation() {
        return location;
    }

    public Boolean getLarge() {
        return large;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getTarget() {
        return target;
    }
}
