package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class recommendAddons extends Effect {
    private Expression<Player> targets;
    private Expression<String> addons;


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.targets = (Expression<Player>) expressions[1];
        this.addons = (Expression<String>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        Gson gson = new Gson();
        JsonArray sendAddons = new JsonArray();
        for (String addon : addons.getArray(event)) {
            JsonObject object = gson.fromJson(addon, JsonObject.class);
            sendAddons.add(object);
        }
        JsonObject object = new JsonObject();
        object.add("addons", sendAddons);
        for (Player player : targets.getArray(event)) {
            LabyModPlugin.getInstance().sendServerMessage( player, "addon_recommendation", object );
        }
    }
}
