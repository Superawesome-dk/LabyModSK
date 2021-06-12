package dk.superawesome.labymodsk.effects.Marker;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import dk.superawesome.labymodsk.classes.marker;
import dk.superawesome.labymodsk.effects.screen.EffSendScreen;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.common.widgets.util.EnumScreenAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffSendMarker extends Effect {

    private Expression<marker> exprMarker;
    private Expression<Player> exprPlayer;

    static {
        Skript.registerEffect(EffSendMarker.class,
                "[LabyModSK] send [marker] %markers% to %players%"
        );
    }


    @Override
    protected void execute(Event e) {
        for(marker marker : exprMarker.getArray(e)) {
            JsonObject markerJson = new JsonObject();
            markerJson.addProperty("sender", marker.getSender().toString());
            markerJson.addProperty("x", marker.getLocation().getX());
            markerJson.addProperty("y", marker.getLocation().getY());
            markerJson.addProperty("z", marker.getLocation().getZ());
            markerJson.addProperty("large", marker.getLarge());
            JsonObject sendMarkers = new JsonObject();
            sendMarkers.add("add_marker", markerJson);
            for(Player target : exprPlayer.getArray(e)) {
                LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(target.getUniqueId(), "marker", sendMarkers);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "send markers " + exprMarker.toString(e, debug) + " to player " + exprPlayer.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprMarker = (Expression<marker>) exprs[0];
        exprPlayer = (Expression<Player>) exprs[1];
        return true;
    }
}
