package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import dk.superawesome.labymodsk.classes.OffSet;
import net.labymod.serverapi.common.widgets.components.Widget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetOffSet extends SimplePropertyExpression<Widget, OffSet> {

    static {
        register(ExprWidgetOffSet.class, OffSet.class,
                "[widget] OffSet",
                "widget"
        );
    }

    @Nullable
    @Override
    public OffSet convert(Widget widget) {
        return new OffSet(widget.getOffsetX(), widget.getOffsetY());
    }

    @Override
    public Class<? extends OffSet> getReturnType() {
        return OffSet.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget OffSet";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(OffSet.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0) return;
        if (!(delta[0] instanceof OffSet)) return;
        OffSet value = (OffSet) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                widget.setOffsetX(value.getX());
                widget.setOffsetY(value.getY());
            }
        }
    }
}
