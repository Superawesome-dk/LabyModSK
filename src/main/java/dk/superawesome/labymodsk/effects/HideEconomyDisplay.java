package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class HideEconomyDisplay extends Effect {
    private Expression<String> displayType;
    private Expression<Player> players;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.displayType = (Expression<String>) expressions[0];
        this.players = (Expression<Player>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonObject economyObject = new JsonObject();
        JsonObject cashObject = new JsonObject();

        // Visibility
        cashObject.addProperty( "visible", false );

        economyObject.add(displayType.getSingle(event), cashObject);
        for (Player player : players.getArray(event))
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "economy", economyObject);
    }
}