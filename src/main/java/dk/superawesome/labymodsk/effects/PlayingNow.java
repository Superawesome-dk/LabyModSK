package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class PlayingNow extends Effect {
    private Expression<Player> player;
    private Expression<String> PlayingNow;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.PlayingNow = (Expression<String>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonObject object = new JsonObject();
        object.addProperty( "show_gamemode", true );
        object.addProperty( "gamemode_name", PlayingNow.getSingle(event) );
        for (Player player : player.getArray(event))
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "server_gamemode", object);
    }
}
