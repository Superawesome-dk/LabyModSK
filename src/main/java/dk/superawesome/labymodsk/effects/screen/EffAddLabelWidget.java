package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.widgets.ButtonWidget;
import net.labymod.serverapi.common.widgets.components.widgets.LabelWidget;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

public class EffAddLabelWidget extends Effect {
    static {
        Skript.registerEffect(EffAddLabelWidget.class,
                "[LabyModSK] add label widget with id %integer% and %anchor% and offsetX %double% and offsetY %double% and value %string% and alignment %integer% and scale %double% to [widgets of] %screen%"
        );
    }

    private Expression<Integer> exprId;
    private Expression<Anchor> exprAnchor;
    private Expression<Double> exprOffsetX;
    private Expression<Double> exprOffsetY;
    private Expression<String> exprValue;
    private Expression<Integer> exprAlignment;
    private Expression<Double> exprScale;
    private Expression<WidgetScreen> exprScreen;

    private LabelWidget widget;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprId = (Expression<Integer>) exprs[0];
        exprAnchor = (Expression<Anchor>) exprs[1];
        exprOffsetX = (Expression<Double>) exprs[2];
        exprOffsetY= (Expression<Double>) exprs[3];
        exprValue = (Expression<String>) exprs[4];
        exprAlignment = (Expression<Integer>) exprs[5];
        exprScale = (Expression<Double>) exprs[6];
        exprScreen = (Expression<WidgetScreen>) exprs[7];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Integer id = exprId.getSingle(e);
        Anchor anchor = exprAnchor != null ? exprAnchor.getSingle(e) : null;
        Double offsetX = exprOffsetX != null ? exprOffsetX.getSingle(e) : null;
        Double offsetY = exprOffsetY != null ? exprOffsetY.getSingle(e) : null;
        String value = exprValue != null ? exprValue.getSingle(e) : null;
        Integer alignment = exprAlignment != null ? exprAlignment.getSingle(e) : null;
        Double scale = exprScale != null ? exprScale.getSingle(e) : null;
        WidgetScreen screen = exprScreen.getSingle(e);

        widget = new LabelWidget(id, anchor, offsetX, offsetY, value, alignment, scale);
        screen.addWidget(widget);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add label widget with id " + exprId.toString(e, debug) + " to widgets of " + exprScreen.toString(e, debug);
    }
}
