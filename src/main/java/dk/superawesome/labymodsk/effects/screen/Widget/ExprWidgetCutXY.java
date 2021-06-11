package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import dk.superawesome.labymodsk.classes.CutXY;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ImageWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetCutXY extends SimplePropertyExpression<Widget, CutXY> {

    static {
        register(ExprWidgetCutXY.class, CutXY.class,
                "[widget] cutxy",
                "widget"
        );
    }

    @Nullable
    @Override
    public CutXY convert(Widget widget) {
        if(widget instanceof ImageWidget) {
            return new CutXY(((ImageWidget) widget).getCutX(), ((ImageWidget) widget).getCutY());
        }
        return null;
    }

    @Override
    public Class<? extends CutXY> getReturnType() {
        return CutXY.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget CutXY";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(CutXY.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0) return;
        if (!(delta[0] instanceof CutXY)) return;
        CutXY value = (CutXY) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                if(widget instanceof ImageWidget) {
                    ((ImageWidget) widget).setCutXY(value.getX(), value.getY());
                } else {
                    return;
                }
            }
        }
    }
}
