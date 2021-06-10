package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.superawesome.labymodsk.Utils.EffectSection;
import dk.superawesome.labymodsk.classes.WidgetType;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.ButtonWidget;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class scopeWidget extends EffectSection {
    public static Widget lastWidget;
    private Expression<WidgetType> exprTYPE;
    private Expression<Integer> exprID;

    static {
        Skript.registerCondition(scopeWidget.class, "make [new] [labymod] %widgettype% widget with id %integer%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprTYPE = (Expression<WidgetType>) exprs[0];
        exprID = (Expression<Integer>) exprs[1];
        if (checkIfCondition()) return false;
        if (!hasSection()) return false;
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        Widget widget = null;
        if(exprTYPE.getSingle(e) == WidgetType.BUTTON) {
            widget = new ButtonWidget(exprID.getSingle(e), new Anchor(0, 0), 0, 0, null, 0, 0);
        }
        lastWidget = widget;
        runSection(e);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new labymod widget with id " + exprID.getSingle(e);
    }

}
