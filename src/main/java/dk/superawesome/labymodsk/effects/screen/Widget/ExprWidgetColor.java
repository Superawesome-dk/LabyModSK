package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ColorPickerWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.awt.*;

public class ExprWidgetColor extends SimplePropertyExpression<Widget, Color> {

    static {
        register(ExprWidgetColor.class, Color.class,
                "[widget] color",
                "widget"
        );
    }

    @Nullable
    @Override
    public Color convert(Widget widget) {
        if(widget instanceof ColorPickerWidget) {
            return ((ColorPickerWidget) widget).getSelectedColor();
        }
        return null;
    }

    @Override
    public Class<? extends Color> getReturnType() {
        return Color.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget color";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Color.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta[0] == null) return;
        if (!(delta[0] instanceof Color)) return;
        Color value = (Color) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                if(widget instanceof ColorPickerWidget) {
                    ((ColorPickerWidget) widget).setSelectedColor(value);
                } else {
                    return;
                }
            }
        }
    }
}
