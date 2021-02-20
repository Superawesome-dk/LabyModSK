package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Cinescopes extends Effect {
    private Expression<Player> player;
    private Expression<Integer> coverage;
    private Expression<Long> duration;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[2];
        this.duration = (Expression<Long>) expressions[1];
        this.coverage = (Expression<Integer>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonObject object = new JsonObject();
        object.addProperty( "coverage", coverage.getSingle(event) );
        long duration2 = duration.getSingle(event) * 1000;
        object.addProperty( "duration", duration2);
        for (Player player : player.getArray(event))
            LabyModPlugin.getInstance().sendServerMessage( player, "cinescopes", object );
    }
}
