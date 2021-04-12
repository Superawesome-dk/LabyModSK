package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Map;

public class Permissions extends Effect {
    private Expression<String> permission;
    private Expression<Player> targets;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.permission = (Expression<String>) expressions[0];
        this.targets = (Expression<Player>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return "send permissions %strings% to %players%";
    }

    @Override
    protected void execute(Event event) {
        Gson gson = new Gson();
        JsonObject permissionsObject = new JsonObject();
        for(String a : permission.getArray(event)) {
            JsonObject object = gson.fromJson(a, JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : object.entrySet() ) {
               permissionsObject.addProperty(entry.getKey(), entry.getValue().getAsBoolean());
            }
        }
        for (Player player : targets.getArray(event)) {
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "PERMISSIONS", permissionsObject);
        }
    }
}
