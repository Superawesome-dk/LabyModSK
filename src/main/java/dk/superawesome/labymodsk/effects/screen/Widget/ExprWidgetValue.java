package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.ContainerWidget;
import net.labymod.serverapi.common.widgets.components.ValueContainerWidget;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.LabelWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetValue extends SimplePropertyExpression<Widget, String> {

    static {
        register(ExprWidgetValue.class, String.class,
                "[widget] value",
                "widget"
        );
    }

    @Nullable
    @Override
    public String convert(Widget widget) {
        if(widget instanceof ValueContainerWidget) {
            return ((ValueContainerWidget) widget).getValue();
        } else if(widget instanceof LabelWidget) {
            return ((LabelWidget) widget).getValue().toString();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget value";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        if (!(delta[0] instanceof String)) return;
        String value = (String) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                if(widget instanceof ValueContainerWidget) {
                    ((ValueContainerWidget) widget).setValue(value);
                } else if(widget instanceof LabelWidget) {
                    ((LabelWidget) widget).setValue(value);
                } else {
                    return;
                }
            }
        }
    }
}
