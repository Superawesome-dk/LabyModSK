package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class Flags extends Effect {
    private Expression<Player> targets;
    private Expression<String> flags;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.targets = (Expression<Player>) expressions[1];
        this.flags = (Expression<String>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        Gson gson = new Gson();
        JsonObject flagPacket = new JsonObject();
        JsonArray result = new JsonArray();
        for(String a : flags.getArray(event)) {
            JsonArray array = gson.fromJson(a, JsonArray.class);
            for (int i = 0; i < array.size(); i++) {
                result.add(array.get(i));
            }
        }
        flagPacket.add("users", result);
        for(Player player : targets.getArray(event)) {
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "language_flag", flagPacket );
        }
    }
}
