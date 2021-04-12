package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Cinematic extends Effect {
    private Expression<Player> targets;
    private Expression<Timespan> duration;
    private Expression<String> points;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.targets = (Expression<Player>) expressions[2];
        this.duration = (Expression<Timespan>) expressions[1];
        this.points = (Expression<String>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        Gson gson = new Gson();
        JsonArray result = new JsonArray();
        for(String a : points.getArray(event)) {
            JsonObject object = gson.fromJson(a, JsonObject.class);
            result.add(object);
        }
        JsonObject cinematic = new JsonObject();
        if(result.size() != 0) {
            cinematic.add( "points", result );
            cinematic.addProperty( "duration", duration.getSingle(event).getMilliSeconds() );
            for (Player player : targets.getArray(event)) {
                JsonObject json = (JsonObject) result.get(0);
                Location l = new Location(Bukkit.getPlayer(player.getUniqueId()).getWorld(), json.get("x").getAsDouble(), json.get("y").getAsDouble(), json.get("z").getAsDouble());
                player.teleport(l);
                LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "cinematic", cinematic);
            }
        } else {
            for (Player player : targets.getArray(event)) {
                player.sendMessage("Contact the server owner!");
            }
        }
    }
}
