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

public class ExprVoicesetting extends SimpleExpression<String> {
    private Expression<String> setting;
    private Expression<Number> value;
    private Expression<Boolean> valueboolean;

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
        this.setting = (Expression<String>) e[0];
        this.value = (Expression<Number>) e[1];
        this.valueboolean = (Expression<Boolean>) e[2];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        JsonObject settingObj = new JsonObject();
        if(valueboolean != null) {
            settingObj.addProperty(setting.getSingle(event), valueboolean.getSingle(event));
        }
        if(value != null) {
            settingObj.addProperty(setting.getSingle(event), value.getSingle(event));
        }
        Gson gson = new Gson();
        return new String[] {gson.fromJson(settingObj, JsonObject.class).toString()};
    }
}
