package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ShowEconomyDisplay extends Effect {
    private Expression<String> displayType;
    private Expression<String> customUrl;
    private Expression<String> divisorformat;
    private Expression<Number> divisor;
    private Expression<Player> players;
    private Expression<Integer> number;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.displayType = (Expression<String>) expressions[0];
        this.number = (Expression<Integer>) expressions[1];
        this.customUrl = (Expression<String>) expressions[2];
        this.divisorformat = (Expression<String>) expressions[3];
        this.divisor = (Expression<Number>) expressions[4];
        this.players = (Expression<Player>) expressions[5];
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

        cashObject.addProperty( "visible", true );
        cashObject.addProperty( "balance", number.getSingle(event) );
        if(customUrl != null) {
            cashObject.addProperty( "icon", customUrl.getSingle(event) );
        }
        if(divisorformat != null) {
            JsonObject decimalObject = new JsonObject();
            decimalObject.addProperty("format", divisorformat.getSingle(event)); // Decimal format
            decimalObject.addProperty("divisor", divisor.getSingle(event));
            cashObject.add("decimal", decimalObject);
        }

        economyObject.add(displayType.getSingle(event), cashObject);
        for (Player player : players.getArray(event))
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "economy", economyObject);
    }
}