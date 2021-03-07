package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.UUID;

public class DiscordGame  extends Effect {
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonObject obj = new JsonObject();
        String domain = "superawesome.dk";
        addSecret( obj, "hasMatchSecret", "matchSecret", UUID.randomUUID().toString(), domain );
        addSecret( obj, "hasSpectateSecret", "spectateSecret", UUID.randomUUID().toString(), domain );
        addSecret( obj, "hasJoinSecret", "joinSecret", UUID.randomUUID().toString(), domain );
        for (Player player : player.getArray(event))
            LabyModPlugin.getInstance().sendServerMessage(player, "discord_rpc", obj);
    }
    public JsonObject addSecret(JsonObject jsonObject, String hasKey, String key, String secret, String domain) {
        jsonObject.addProperty( hasKey, true );
        jsonObject.addProperty( key, secret.toString() + ":" + domain );
        return jsonObject;
    }
}
