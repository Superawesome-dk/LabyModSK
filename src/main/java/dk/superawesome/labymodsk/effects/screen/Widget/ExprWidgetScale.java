package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.ValueContainerWidget;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.LabelWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetScale extends SimplePropertyExpression<Widget, Double> {

    static {
        register(ExprWidgetScale.class, Double.class,
                "[widget] scale",
                "widget"
        );
    }

    @Nullable
    @Override
    public Double convert(Widget widget) {
        if(widget instanceof LabelWidget) {
            return ((LabelWidget) widget).getScale();
        }
        return null;
    }

    @Override
    public Class<? extends Double> getReturnType() {
        return Double.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget scale";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Double.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        if (!(delta[0] instanceof Double)) return;
        Double value = (Double) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                if(widget instanceof LabelWidget) {
                    ((LabelWidget) widget).setScale(value);
                } else {
                    return;
                }
            }
        }
    }
}
