package dk.superawesome.labymodsk.effects.Marker;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.superawesome.labymodsk.Expression.Subtitle;
import dk.superawesome.labymodsk.classes.marker;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ExprMarker extends SimpleExpression<marker> {
    static {
        Skript.registerExpression(ExprMarker.class, marker.class, ExpressionType.COMBINED,
                "[the] marker with sender %string% and location %location%[ and target %-entity%]",
                "[the] large marker with sender %string% and location %location%[ and target %-entity%]"
        );
    }

    private Expression<String> sender;
    private Expression<Location> location;
    private Expression<Entity> target;
    private boolean isLarge;

    public Class<? extends marker> getReturnType() {
        return marker.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        sender = (Expression<String>) e[0];
        location = (Expression<Location>) e[1];
        target = (Expression<Entity>) e[2];
        isLarge = parser.mark == 2;
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected marker[] get(Event event) {
        UUID sender = UUID.fromString(this.sender.getSingle(event));
        marker marker = null;
        if(target != null) {
            UUID target = this.target.getSingle(event).getUniqueId();
            marker = new marker(location.getSingle(event), isLarge, sender, target);
        } else {
            marker = new marker(location.getSingle(event), isLarge, sender);
        }
        return new marker[]{marker};
    }
}
