package dk.superawesome.labymodsk.Expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class AddonEntry extends SimpleExpression<String> {
    private Expression<String> uuid;
    private Expression<Boolean> required;

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
        this.uuid = (Expression<String>) e[0];
        this.required = (Expression<Boolean>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        JsonObject addon = new JsonObject();
        addon.addProperty("uuid", uuid.getSingle(event));
        addon.addProperty("required", required.getSingle(event));
        Gson gson = new Gson();
        return new String[] {gson.fromJson(addon, JsonObject.class).toString()};
    }
}
