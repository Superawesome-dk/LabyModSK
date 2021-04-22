package dk.superawesome.labymodsk.Expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class FlagUser extends SimpleExpression<String> {
    private Expression<Player> targets;
    private Expression<String> code;

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
        targets = (Expression<Player>) e[0];
        code = (Expression<String>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        JsonArray subtitles = new JsonArray();
        for (Player target : targets.getArray(event)) {
            JsonObject entry = new JsonObject();
            entry.addProperty("uuid", target.getUniqueId().toString());
            entry.addProperty("code", code.getSingle(event));
            subtitles.add(entry);
        }
        Gson gson = new Gson();
        return new String[] {gson.fromJson(subtitles, JsonArray.class).toString()};
    }
}
