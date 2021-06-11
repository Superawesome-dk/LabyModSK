package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.superawesome.labymodsk.Utils.EffectSection;
import dk.superawesome.labymodsk.classes.WidgetType;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.awt.*;

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
        } else if(exprTYPE.getSingle(e) == WidgetType.TEXT_FIELD) {
            widget = new TextFieldWidget(exprID.getSingle(e), new Anchor(0, 0), 0, 0, null, 0, 0, null, 0, false);
        } else if(exprTYPE.getSingle(e) == WidgetType.LABEL) {
            widget = new LabelWidget(exprID.getSingle(e), new Anchor(0, 0), 0, 0, "", 0, 0);
        } else if(exprTYPE.getSingle(e) == WidgetType.COLOR_PICKER) {
            widget = new ColorPickerWidget(exprID.getSingle(e), new Anchor(0, 0), 0, 0, 0, 0, null, Color.BLACK);
        } else if(exprTYPE.getSingle(e) == WidgetType.IMAGE) {
            widget = new ImageWidget(exprID.getSingle(e), new Anchor(0, 0), 0, 0, 0, 0, null);
        } else if(exprTYPE.getSingle(e) == WidgetType.INVENTORY_IMAGE) {
            widget = new ImageWidget(exprID.getSingle(e), null);
        }
        lastWidget = widget;
        runSection(e);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new labymod widget with id " + exprID.getSingle(e);
    }

}
