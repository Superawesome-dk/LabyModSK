package dk.superawesome.labymodsk.Expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ActionEntry extends SimpleExpression<String> {
    private Expression<String> displayName;
    private Expression<String> type;
    private Expression<String> value;

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
        displayName = (Expression<String>) e[0];
        type = (Expression<String>) e[1];
        value = (Expression<String>) e[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        JsonObject entry = new JsonObject();
        entry.addProperty("displayName", displayName.getSingle(event));
        entry.addProperty("type", type.getSingle(event));
        entry.addProperty("value", value.getSingle(event));
        Gson gson = new Gson();
        return new String[] {gson.fromJson(entry, JsonObject.class).toString()};
    }
}
