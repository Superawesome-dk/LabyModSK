package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;


public class DiscordRich extends Effect {
    private Expression<Player> player;
    private Expression<String> displayname;
    private Expression<Timespan> starttime;
    private Long starttime2 = 0L;
    private Expression<Timespan> endtime;
    private Long endtime2 = 0L;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.displayname = (Expression<String>) expressions[1];
        this.starttime = (Expression<Timespan>) expressions[2];
        this.endtime = (Expression<Timespan>) expressions[3];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        if(starttime != null) {
            starttime2 = System.currentTimeMillis() - starttime.getSingle(event).getMilliSeconds();
        }
        if(endtime != null) {
            endtime2 = System.currentTimeMillis() + endtime.getSingle(event).getMilliSeconds();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty( "hasGame", true );
        obj.addProperty( "game_mode", displayname.getSingle(event) );
        obj.addProperty( "game_startTime", starttime2);
        obj.addProperty( "game_endTime", endtime2 );
        for (Player player : player.getArray(event))
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "discord_rpc", obj);
    }
}
