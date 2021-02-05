package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class Subtitles extends Effect {
    private Expression<Player> players;
    private Expression<String> value;
    private Expression<Number> size;
    private Expression<Player> targets;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.players = (Expression<Player>) expressions[0];
        this.value = (Expression<String>) expressions[1];
        this.size = (Expression<Number>) expressions[2];
        this.targets = (Expression<Player>) expressions[3];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonArray array = new JsonArray();
        for(Player player : players.getArray(event)) {
            JsonObject subtitle = new JsonObject();
            subtitle.addProperty("uuid", player.getUniqueId().toString());
            subtitle.addProperty("size", size.getSingle(event));

            if (value != null)
                subtitle.addProperty("value", value.getSingle(event));
            array.add(subtitle);
        }
        for (Player target : targets.getArray(event))
            LabyModPlugin.getInstance().sendServerMessage(target, "account_subtitle", array);
    }
}
