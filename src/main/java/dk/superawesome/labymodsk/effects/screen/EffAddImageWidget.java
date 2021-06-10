package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.widgets.ColorPickerWidget;
import net.labymod.serverapi.common.widgets.components.widgets.ImageWidget;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

import java.awt.*;

public class EffAddImageWidget extends Effect {
    static {
        Skript.registerEffect(EffAddImageWidget.class,
                "[LabyModSK] add image widget with id %integer% and %anchor% and offsetX %double% and offsetY %double% and width %integer% and height %integer% and url %string%[ and cutX %-integer% and cutY %-integer%][ and cutWidth %-integer% and cutHeight %-integer%] to [widgets of] %screen%",
                "[LabyModSK] add image widget with slot %integer% and url %string%[ and cutX %-integer% and cutY %-integer%][ and cutWidth %-integer% and cutHeight %-integer%] to [widgets of] %screen%"
        );
    }

    private Expression<Integer> exprId;
    private Expression<Anchor> exprAnchor;
    private Expression<Double> exprOffsetX;
    private Expression<Double> exprOffsetY;
    private Expression<Integer> exprWidth;
    private Expression<Integer> exprHeight;
    private Expression<String> exprUrl;
    private Expression<Integer> exprCutX;
    private Expression<Integer> exprCutY;
    private Expression<Integer> exprCutWidth;
    private Expression<Integer> exprCutHeight;
    private Expression<WidgetScreen> exprScreen;

    private ImageWidget widget;
    private boolean isInventory;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        isInventory = matchedPattern == 1;
        exprId = (Expression<Integer>) exprs[0];
        int length = exprs.length - 1;
        if(!isInventory) {
            exprAnchor = (Expression<Anchor>) exprs[1];
            exprOffsetX = (Expression<Double>) exprs[2];
            exprOffsetY= (Expression<Double>) exprs[3];
            exprWidth = (Expression<Integer>) exprs[4];
            exprHeight = (Expression<Integer>) exprs[5];
        }
        exprUrl = (Expression<String>) exprs[length - 5];
        exprCutX = (Expression<Integer>) exprs[length - 4];
        exprCutY = (Expression<Integer>) exprs[length - 3];
        exprCutWidth = (Expression<Integer>) exprs[length - 2];
        exprCutHeight = (Expression<Integer>) exprs[length - 1];
        exprScreen = (Expression<WidgetScreen>) exprs[length];
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
        String url = exprUrl != null ? exprUrl.getSingle(e) : null;
        Integer cutX = exprCutX != null ? exprCutX.getSingle(e) : null;
        Integer cutY = exprCutY != null ? exprCutY.getSingle(e) : null;
        Integer cutWidth = exprCutWidth != null ? exprCutWidth.getSingle(e) : null;
        Integer cutHeight = exprCutHeight != null ? exprCutHeight.getSingle(e) : null;
        WidgetScreen screen = exprScreen.getSingle(e);
        if(isInventory) {
            widget = new ImageWidget(id, url);
        } else {
            widget = new ImageWidget(id, anchor, offsetX, offsetY, width, height, url);
        }
        if(cutX != null || cutY != null) {
            widget.setCutXY(cutX, cutY);
        }
        if(cutX != null || cutY != null) {
            widget.setCutWithHeight(cutWidth, cutHeight);
        }
        screen.addWidget(widget);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add color picker widget with id " + exprId.toString(e, debug) + " to widgets of " + exprScreen.toString(e, debug);
    }
}
