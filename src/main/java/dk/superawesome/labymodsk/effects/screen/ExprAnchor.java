package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import com.google.gson.JsonObject;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprAnchor extends SimplePropertyExpression<JsonObject, Anchor> {
    static {
        register(ExprAnchor.class, Anchor.class,
                "[screen] anchor",
                "jsonobject"
        );
    }

    @Override
    protected String getPropertyName() {
        return "anchor";
    }

    @Nullable
    @Override
    public Anchor convert(JsonObject object) {
        Anchor finalAnchor = new Anchor(0, 0);
        if(object.has("anchor")) {
            finalAnchor = new Anchor(object.get("anchor").getAsJsonObject().get("x").getAsDouble(), object.get("anchor").getAsJsonObject().get("y").getAsDouble());
        }
        return finalAnchor;
    }

    @Override
    public Class<? extends Anchor> getReturnType() {
        return Anchor.class;
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Object[].class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length < 2 || delta.length > 2) return;
        System.out.println(delta.length);
        Anchor Finalanchor = null;
        if(delta[0] instanceof Double && delta[1] instanceof Double) {
            Finalanchor = new Anchor((double) delta[0], (double) delta[1]);
        }

        if (mode == Changer.ChangeMode.SET) {
            JsonObject obj = new JsonObject();
            obj.addProperty("x", Finalanchor.getX());
            obj.addProperty("y", Finalanchor.getY());
            getExpr().getSingle(e).add("anchor", obj);
        }
    }

}
