package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class DiscordRich extends Effect {
    private Expression<Player> player;
    private Expression<String> discordRich;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.discordRich = (Expression<String>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonObject obj = new JsonObject();
        obj.addProperty( "hasGame", true );
        obj.addProperty( "game_mode", discordRich.getSingle(event) );
        obj.addProperty( "game_startTime", 0 ); // Set to 0 for countdown
        obj.addProperty( "game_endTime", 0 );
        for (Player player : player.getArray(event))
            LabyModPlugin.getInstance().sendServerMessage(player, "discord_rpc", obj);
    }
}
