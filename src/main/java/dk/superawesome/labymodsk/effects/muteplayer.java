package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class muteplayer extends Effect {
    private Expression<Player> target;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.target = (Expression<Player>) expressions[0];
        this.player = (Expression<Player>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return "voicechat mute %players% for %players%";
    }

    @Override
    protected void execute(Event event) {
        for(Player target : target.getArray(event)) {
            JsonObject result = new JsonObject();
            result.addProperty("mute", true );
            result.addProperty("target", target.getUniqueId().toString() );
            JsonObject object = new JsonObject();
            object.add("mute_player", result);
            for (Player player : player.getArray(event))
                LabyModPlugin.getInstance().sendServerMessage(player, "voicechat", object);
        }
    }
}
