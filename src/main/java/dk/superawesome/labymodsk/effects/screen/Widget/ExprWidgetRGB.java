package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ColorPickerWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.awt.*;

public class ExprWidgetRGB extends SimplePropertyExpression<Widget, Boolean> {

    static {
        register(ExprWidgetRGB.class, Boolean.class,
                "[widget] rgb",
                "widget"
        );
    }

    @Nullable
    @Override
    public Boolean convert(Widget widget) {
        if(widget instanceof ColorPickerWidget) {
            return ((ColorPickerWidget) widget).isRgb();
        }
        return null;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget Boolean";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Boolean.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        if (!(delta[0] instanceof Boolean)) return;
        Boolean value = (Boolean) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                if(widget instanceof ColorPickerWidget) {
                    ((ColorPickerWidget) widget).setRgb(value);
                } else {
                    return;
                }
            }
        }
    }
}
