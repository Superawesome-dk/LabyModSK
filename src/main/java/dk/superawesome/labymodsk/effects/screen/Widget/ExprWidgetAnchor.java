package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetAnchor extends SimplePropertyExpression<Widget, Anchor> {

    static {
        register(ExprWidgetAnchor.class, Anchor.class,
                "[widget] anchor",
                "widget"
        );
    }

    @Nullable
    @Override
    public Anchor convert(Widget widget) {
        return widget.getAnchor();
    }

    @Override
    public Class<? extends Anchor> getReturnType() {
        return Anchor.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget anchor";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Anchor.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0) return;
        if (!(delta[0] instanceof Anchor)) return;
        Anchor value = (Anchor) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                widget.setAnchor(value);
            }
        }
    }
}
