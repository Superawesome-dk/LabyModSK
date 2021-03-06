package dk.superawesome.labymodsk.Expression;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprPermission extends SimpleExpression<String> {
    private Expression<String> permission;
    private Expression<Boolean> bologna;

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
        this.permission = (Expression<String>) e[0];
        this.bologna = (Expression<Boolean>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        JsonObject permissionObject = new JsonObject();
        permissionObject.addProperty(permission.getSingle(event), bologna.getSingle(event));
        Gson gson = new Gson();
        return new String[] {gson.fromJson(permissionObject, JsonObject.class).toString()};
    }
}
