package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.widgets.ButtonWidget;
import net.labymod.serverapi.common.widgets.components.widgets.TextFieldWidget;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;

public class EffAddTextFieldWidget extends Effect {
    static {
        Skript.registerEffect(EffAddTextFieldWidget.class,
                "[LabyModSK] add text field widget with id %integer% and %anchor% and offsetX %double% and offsetY %double% and value %string% and width %integer% and height %integer% and placeholder %string% and maxlength %integer% and focused %boolean% to [widgets of] %screen%"
        );
    }

    private Expression<Integer> exprId;
    private Expression<Anchor> exprAnchor;
    private Expression<Double> exprOffsetX;
    private Expression<Double> exprOffsetY;
    private Expression<String> exprValue;
    private Expression<Integer> exprWidth;
    private Expression<Integer> exprHeight;
    private Expression<String> exprPlaceholder;
    private Expression<Integer> exprMaxlength;
    private Expression<Boolean> exprFocused;
    private Expression<WidgetScreen> exprScreen;

    private TextFieldWidget widget;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprId = (Expression<Integer>) exprs[0];
        exprAnchor = (Expression<Anchor>) exprs[1];
        exprOffsetX = (Expression<Double>) exprs[2];
        exprOffsetY= (Expression<Double>) exprs[3];
        exprValue = (Expression<String>) exprs[4];
        exprWidth = (Expression<Integer>) exprs[5];
        exprHeight = (Expression<Integer>) exprs[6];
        exprPlaceholder = (Expression<String>) exprs[7];
        exprMaxlength = (Expression<Integer>) exprs[8];
        exprFocused = (Expression<Boolean>) exprs[9];
        exprScreen = (Expression<WidgetScreen>) exprs[10];
        return true;
    }

    @Override
    protected void execute(Event e) {
        Integer id = exprId.getSingle(e);
        Anchor anchor = exprAnchor != null ? exprAnchor.getSingle(e) : null;
        Double offsetX = exprOffsetX != null ? exprOffsetX.getSingle(e) : null;
        Double offsetY = exprOffsetY != null ? exprOffsetY.getSingle(e) : null;
        String value = exprValue != null ? exprValue.getSingle(e) : null;
        Integer width = exprWidth != null ? exprWidth.getSingle(e) : null;
        Integer height = exprHeight != null ? exprHeight.getSingle(e) : null;
        String placeholder = exprPlaceholder != null ? exprPlaceholder.getSingle(e) : null;
        Integer maxlength = exprMaxlength != null ? exprMaxlength.getSingle(e) : null;
        Boolean focused = exprFocused != null ? exprFocused.getSingle(e) : false;
        WidgetScreen screen = exprScreen.getSingle(e);

        widget = new TextFieldWidget(id, anchor, offsetX, offsetY, value, width, height, placeholder, maxlength, focused);
        screen.addWidget(widget);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add text field widget with id " + exprId.toString(e, debug) + " to widgets of " + exprScreen.toString(e, debug);
    }
}
