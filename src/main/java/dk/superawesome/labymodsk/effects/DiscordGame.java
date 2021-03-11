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
    private Expression<String> joinsecret;
    private String joinsecret2 = UUID.randomUUID().toString();
    private Expression<String> spectatsecret;
    private String spectatsecret2 = UUID.randomUUID().toString();
    private Expression<String> matchsecret;
    private String matchsecret2 = UUID.randomUUID().toString();

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[3];
        this.joinsecret = (Expression<String>) expressions[2];
        this.spectatsecret = (Expression<String>) expressions[1];
        this.matchsecret = (Expression<String>) expressions[0];
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
        addSecret( obj, "hasMatchSecret", "matchSecret",  matchsecret != null ? matchsecret.getSingle(event) : matchsecret2, domain );
        addSecret( obj, "hasSpectateSecret", "spectateSecret", spectatsecret != null ? spectatsecret.getSingle(event) : spectatsecret2, domain );
        addSecret( obj, "hasJoinSecret", "joinSecret", matchsecret != null ? joinsecret.getSingle(event) : joinsecret2, domain );
        for (Player player : player.getArray(event))
            LabyModPlugin.getInstance().sendServerMessage(player, "discord_rpc", obj);
    }
    public JsonObject addSecret(JsonObject jsonObject, String hasKey, String key, String secret, String domain) {
        jsonObject.addProperty( hasKey, true );
        jsonObject.addProperty( key, secret.toString() + ":" + domain );
        return jsonObject;
    }
}
