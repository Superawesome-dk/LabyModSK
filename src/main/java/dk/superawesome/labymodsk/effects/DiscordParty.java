package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class DiscordParty extends Effect {
    private Expression<String> id;
    private Expression<Number> size;
    private Expression<Number> max;
    private Expression<Player> targets;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.id = (Expression<String>) expressions[0];
        this.size = (Expression<Number>) expressions[1];
        this.max = (Expression<Number>) expressions[2];
        this.targets = (Expression<Player>) expressions[3];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        String domain = "superawesome.dk";
        JsonObject obj = new JsonObject();
        obj.addProperty( "hasParty", true );

        obj.addProperty( "partyId", id.getSingle(event) + ":" + domain );
        obj.addProperty( "party_size", size.getSingle(event) );
        obj.addProperty( "party_max", max.getSingle(event) );
        for (Player player : targets.getArray(event)) {
            LabyModPlugin.getInstance().sendServerMessage( player, "discord_rpc", obj );
        }
    }
}
