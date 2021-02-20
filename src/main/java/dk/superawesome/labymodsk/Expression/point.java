package dk.superawesome.labymodsk.Expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class point extends SimpleExpression<String> {
    private Expression<Location> location;
    private Expression<Integer> tilt;

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.location = (Expression<Location>) e[0];
        this.tilt = (Expression<Integer>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        JsonObject point = new JsonObject();
        point.addProperty( "x", location.getSingle(event).getX() );
        point.addProperty( "y",  location.getSingle(event).getY() );
        point.addProperty( "z",  location.getSingle(event).getZ() );
        point.addProperty( "yaw",  location.getSingle(event).getYaw() );
        point.addProperty( "pitch",  location.getSingle(event).getPitch() );
        point.addProperty( "tilt", tilt.getSingle(event) );
        Gson gson = new Gson();
        return new String[] {gson.fromJson(point, JsonObject.class).toString()};
    }
}

