package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.widgets.ButtonWidget;
import net.labymod.serverapi.common.widgets.components.widgets.ColorPickerWidget;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

import java.awt.*;

public class EffAddColorPickerWidget extends Effect {
    static {
        Skript.registerEffect(EffAddColorPickerWidget.class,
                "[LabyModSK] add color picker widget with id %integer% and %anchor% and offsetX %double% and offsetY %double% and width %integer% and height %integer% and title %string% and color %labymodskjavacolor%[ and use rgb %-boolean%] to [widgets of] %screen%"
        );
    }

    private Expression<Integer> exprId;
    private Expression<Anchor> exprAnchor;
    private Expression<Double> exprOffsetX;
    private Expression<Double> exprOffsetY;
    private Expression<Integer> exprWidth;
    private Expression<Integer> exprHeight;
    private Expression<String> exprTitle;
    private Expression<Color> exprColor;
    private Expression<Boolean> exprUseRGB;
    private Expression<WidgetScreen> exprScreen;

    private ColorPickerWidget widget;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprId = (Expression<Integer>) exprs[0];
        exprAnchor = (Expression<Anchor>) exprs[1];
        exprOffsetX = (Expression<Double>) exprs[2];
        exprOffsetY= (Expression<Double>) exprs[3];
        exprWidth = (Expression<Integer>) exprs[4];
        exprHeight = (Expression<Integer>) exprs[5];
        exprTitle = (Expression<String>) exprs[6];
        exprColor = (Expression<Color>) exprs[7];
        exprUseRGB = (Expression<Boolean>) exprs[8];
        exprScreen = (Expression<WidgetScreen>) exprs[9];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Integer id = exprId.getSingle(e);
        Anchor anchor = exprAnchor != null ? exprAnchor.getSingle(e) : null;
        Double offsetX = exprOffsetX != null ? exprOffsetX.getSingle(e) : null;
        Double offsetY = exprOffsetY != null ? exprOffsetY.getSingle(e) : null;
        Integer width = exprWidth != null ? exprWidth.getSingle(e) : null;
        Integer height = exprHeight != null ? exprHeight.getSingle(e) : null;
        String title = exprTitle != null ? exprTitle.getSingle(e) : null;
        Color color = exprColor != null ? exprColor.getSingle(e) : null;
        Boolean useRGB = exprUseRGB != null ? exprUseRGB.getSingle(e) : false;
        WidgetScreen screen = exprScreen.getSingle(e);

        widget = new ColorPickerWidget(id, anchor, offsetX, offsetY, width, height, title, color);
        widget.setRgb(useRGB);
        screen.addWidget(widget);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add color picker widget with id " + exprId.toString(e, debug) + " to widgets of " + exprScreen.toString(e, debug);
    }
}
