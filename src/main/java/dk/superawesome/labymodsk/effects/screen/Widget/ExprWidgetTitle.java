package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.ValueContainerWidget;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ColorPickerWidget;
import net.labymod.serverapi.common.widgets.components.widgets.LabelWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetTitle extends SimplePropertyExpression<Widget, String> {

    static {
        register(ExprWidgetTitle.class, String.class,
                "[widget] title",
                "widget"
        );
    }

    @Nullable
    @Override
    public String convert(Widget widget) {
        if(widget instanceof ColorPickerWidget) {
            return ((ColorPickerWidget) widget).getTitle();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget title";
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
                if(widget instanceof ColorPickerWidget) {
                    ((ColorPickerWidget) widget).setTitle(value);
                } else {
                    return;
                }
            }
        }
    }
}
