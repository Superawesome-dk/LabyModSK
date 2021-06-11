package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.Widget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetId extends SimplePropertyExpression<Widget, Number> {

    static {
        register(ExprWidgetId.class, Number.class,
                "[widget] id",
                "widget"
        );
    }

    @Nullable
    @Override
    public Number convert(Widget widget) {
        return widget.getId();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget id";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Number.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        if (!(delta[0] instanceof Number)) return;
        Number value = (Number) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                widget.setId(value.intValue());
            }
        }
    }
}
