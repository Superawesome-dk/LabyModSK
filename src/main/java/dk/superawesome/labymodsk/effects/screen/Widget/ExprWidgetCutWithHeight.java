package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import dk.superawesome.labymodsk.classes.CutWithHeight;
import dk.superawesome.labymodsk.classes.CutXY;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ImageWidget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprWidgetCutWithHeight extends SimplePropertyExpression<Widget, CutWithHeight> {

    static {
        register(ExprWidgetCutWithHeight.class, CutWithHeight.class,
                "[widget] cutwithheight",
                "widget"
        );
    }

    @Nullable
    @Override
    public CutWithHeight convert(Widget widget) {
        if(widget instanceof ImageWidget) {
            return new CutWithHeight(((ImageWidget) widget).getCutWidth(), ((ImageWidget) widget).getHeight());
        }
        return null;
    }

    @Override
    public Class<? extends CutWithHeight> getReturnType() {
        return CutWithHeight.class;
    }

    @Override
    protected String getPropertyName() {
        return "widget CutWithHeight";
    }

    @Nullable
    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(CutWithHeight.class);
        }
        return CollectionUtils.array();
    }

    @Override
    public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
        if (delta == null || delta.length == 0) return;
        if (!(delta[0] instanceof CutWithHeight)) return;
        CutWithHeight value = (CutWithHeight) delta[0];
        if (mode == Changer.ChangeMode.SET) {
            for (Widget widget : getExpr().getArray(e)) {
                if(widget instanceof ImageWidget) {
                    ((ImageWidget) widget).setCutWithHeight(value.getWidth(), value.getHeight());
                } else {
                    return;
                }
            }
        }
    }
}
