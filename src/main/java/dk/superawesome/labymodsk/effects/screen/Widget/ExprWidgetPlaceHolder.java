package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ButtonWidget;
import net.labymod.serverapi.common.widgets.components.widgets.TextFieldWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetPlaceHolder extends SimplePropertyExpression<Widget, String> {

    static {
        register(ExprWidgetPlaceHolder.class, String.class,
                "[widget] placeholder",
                "widget"
        );
    }

    @Nullable
    @Override
    public String convert(Widget widget) {
        if(widget instanceof TextFieldWidget) {
            return ((TextFieldWidget) widget).getPlaceholder();
        }
        return null;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget placeholder";
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
                if(widget instanceof TextFieldWidget) {
                    ((TextFieldWidget) widget).setPlaceholder(value);
                } else {
                    return;
                }
            }
        }
    }
}
