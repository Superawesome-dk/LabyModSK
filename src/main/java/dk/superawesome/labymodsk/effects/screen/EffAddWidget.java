package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.common.widgets.WidgetSerialization;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.components.widgets.*;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;
import org.checkerframework.checker.units.qual.A;

import java.awt.*;

public class EffAddWidget extends Effect {
    static {
        Skript.registerEffect(EffAddWidget.class,
                "[LabyModSK] add button widget with id %integer%[ and anchorX %-double% and anchorY %-double%] and offsetX %double% and offsetY %double% and value %string% and width %integer% and height %integer%[ and close on click %-boolean%] to [widgets of] %jsonobject%",
                "[LabyModSK] add text field widget with id %integer%[ and anchorX %-double% and anchorY %-double%] and offsetX %double% and offsetY %double% and value %string% and width %integer% and height %integer% and placeholder %string% and maxlength %integer% and focused %boolean% to [widgets of] %jsonobject%",
                "[LabyModSK] add label widget with id %integer%[ and anchorX %-double% and anchorY %-double%] and offsetX %double% and offsetY %double% and value %string% and alignment %integer% and scale %double% to [widgets of] %jsonobject%",
                "[LabyModSK] add color picker widget with id %integer%[ and anchorX %-double% and anchorY %-double%] and offsetX %double% and offsetY %double% and width %integer% and height %integer% and title %string% and color %javacolor%[ and use rgb %-boolean%] to [widgets of] %jsonobject%",
                "[LabyModSK] add image widget with id %integer%[ and anchorX %-double% and anchorY %-double%] and offsetX %double% and offsetY %double% and width %integer% and height %integer% and url %string%[ and cutX %-integer% and cutY %-integer%][ and cutWidth %-integer% and cutHeight %-integer%] to [widgets of] %jsonobject%"
        );
    }

    private Expression<Integer> exprId;
    private Expression<Double> exprOffsetX;
    private Expression<Double> exprOffsetY;
    private Expression<String> exprValue;
    private Expression<Integer> exprWidth;
    private Expression<Integer> exprHeight;
    private Expression<Double> exprAnchorX;
    private Expression<Double> exprAnchorY;
    private Expression<String> exprPlaceholder;
    private Expression<Integer> exprMaxlength;
    private Expression<Boolean> exprFocused;
    private Expression<Integer> exprAlignment;
    private Expression<Double> exprScale;
    private Expression<String> exprTitle;
    private Expression<Color> exprColor;
    private Expression<String> exprUrl;
    private Expression<Boolean> exprCloseonclick;
    private Expression<Boolean> exprUsergb;
    private Expression<Integer> exprCutX;
    private Expression<Integer> exprCutY;
    private Expression<Integer> exprCutWidth;
    private Expression<Integer> exprCutHeight;


    private Expression<JsonObject> exprScreen;
    private boolean isButton;
    private boolean isTextField;
    private boolean isLabel;
    private boolean isColorPicker;
    private boolean isImage;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        isButton = matchedPattern == 0;
        isTextField = matchedPattern == 1;
        isLabel = matchedPattern == 2;
        isColorPicker = matchedPattern == 3;
        isImage = matchedPattern == 4;
        exprId = (Expression<Integer>) exprs[0];
        exprAnchorX = (Expression<Double>) exprs[1];
        exprAnchorY = (Expression<Double>) exprs[2];
        exprOffsetX = (Expression<Double>) exprs[3];
        exprOffsetY = (Expression<Double>) exprs[4];
        if(isButton || isTextField) {
            exprValue = (Expression<String>) exprs[5];
            exprWidth = (Expression<Integer>) exprs[6];
            exprHeight = (Expression<Integer>) exprs[7];
        }
        if(isButton) {
            exprCloseonclick = (Expression<Boolean>) exprs[8];
            exprScreen = (Expression<JsonObject>) exprs[9];
        }
        if(isTextField) {
            exprPlaceholder = (Expression<String>) exprs[8];
            exprMaxlength = (Expression<Integer>) exprs[9];
            exprFocused = (Expression<Boolean>) exprs[10];
            exprScreen = (Expression<JsonObject>) exprs[11];
        }
        if(isLabel) {
            exprValue = (Expression<String>) exprs[5];
            exprAlignment = (Expression<Integer>) exprs[6];
            exprScale = (Expression<Double>) exprs[7];
            exprScreen = (Expression<JsonObject>) exprs[8];
        }
        if(isColorPicker) {
            exprWidth = (Expression<Integer>) exprs[5];
            exprHeight = (Expression<Integer>) exprs[6];
            exprTitle = (Expression<String>) exprs[7];
            exprColor = (Expression<Color>) exprs[8];
            exprUsergb = (Expression<Boolean>) exprs[9];
            exprScreen = (Expression<JsonObject>) exprs[10];
        }
        if(isImage) {
            exprWidth = (Expression<Integer>) exprs[5];
            exprHeight = (Expression<Integer>) exprs[6];
            exprUrl = (Expression<String>) exprs[7];
            exprCutX = (Expression<Integer>) exprs[8];
            exprCutY = (Expression<Integer>) exprs[9];
            exprCutWidth = (Expression<Integer>) exprs[10];
            exprCutHeight = (Expression<Integer>) exprs[11];
            exprScreen = (Expression<JsonObject>) exprs[12];
        }
        return true;
    }

    @Override
    protected void execute(Event e) {
        Integer id = exprId.getSingle(e);
        Anchor Finalanchor = exprAnchorX != null ? new Anchor(exprAnchorX.getSingle(e), exprAnchorY.getSingle(e)) : null;
        Double offsetX = exprOffsetX.getSingle(e);
        Double offsetY = exprOffsetY.getSingle(e);
        String value = exprValue != null ? exprValue.getSingle(e) : null;
        Integer width = exprWidth != null ? exprWidth.getSingle(e) : null;
        Integer height = exprHeight != null ? exprHeight.getSingle(e) : null;
        String title = exprTitle != null ? exprTitle.getSingle(e) : null;
        Color finalColor = exprColor != null ? exprColor.getSingle(e) : null;
        String url = exprUrl != null ? exprUrl.getSingle(e) : null;
        JsonObject screen = exprScreen.getSingle(e);

        if(Finalanchor == null) {
            if(screen.get("anchor") == null || (screen.has("anchor") && !(screen.get("anchor").getAsJsonObject().has("x") || screen.get("anchor").getAsJsonObject().has("y")))) {
                return;
            }
            Finalanchor = new Anchor(screen.get("anchor").getAsJsonObject().get("x").getAsDouble(), screen.get("anchor").getAsJsonObject().get("y").getAsDouble());
        }

        if(isButton) {
            ButtonWidget widget = new ButtonWidget(id, Finalanchor, offsetX, offsetY, value, width, height);
            if(exprCloseonclick != null) {
                widget.setCloseScreenOnClick(exprCloseonclick.getSingle(e));
            }
            screen.get("widgets").getAsJsonArray().add(WidgetSerialization.toJsonObject(widget));
        }

        if(isTextField) {
            TextFieldWidget widget = new TextFieldWidget(id, Finalanchor, offsetX, offsetY, value, width, height, exprPlaceholder.getSingle(e), exprMaxlength.getSingle(e), exprFocused.getSingle(e));
            screen.get("widgets").getAsJsonArray().add(WidgetSerialization.toJsonObject(widget));
        }

        if(isLabel) {
            LabelWidget widget = new LabelWidget(id, Finalanchor, offsetX, offsetY, value, exprAlignment.getSingle(e), exprScale.getSingle(e));
            screen.get("widgets").getAsJsonArray().add(WidgetSerialization.toJsonObject(widget));
        }

        if(isColorPicker) {
            ColorPickerWidget widget = new ColorPickerWidget(id, Finalanchor, offsetX, offsetY, width, height, title, finalColor);
            if(exprUsergb != null) {
                widget.setRgb(exprUsergb.getSingle(e));
            }
            screen.get("widgets").getAsJsonArray().add(WidgetSerialization.toJsonObject(widget));
        }

        if(isImage) {
            ImageWidget widget = new ImageWidget(id, Finalanchor, offsetX, offsetY, width, height, url);
            if(exprCutX != null) {
                widget.setCutXY(exprCutX.getSingle(e), exprCutY.getSingle(e));
            }
            if(exprCutWidth != null) {
                widget.setCutXY(exprCutWidth.getSingle(e), exprCutHeight.getSingle(e));
            }
            screen.get("widgets").getAsJsonArray().add(WidgetSerialization.toJsonObject(widget));
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add widget with id " + exprId.toString(e, debug) + " to widgets of " + exprScreen.toString(e, debug);
    }
}
