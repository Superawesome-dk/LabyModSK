package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.WidgetLayout;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffSetLayout extends Effect {
    static {
        Skript.registerEffect(EffSetLayout.class,
                "set [inventory] layout of %screen% to slotWidth %integer% and slotHeight %integer% and slotMarginX %integer% and slotMarginY %integer% and borderPaddingX %integer% and borderPaddingY %integer% [and font size %-double%]"
        );
    }

    private Expression<WidgetScreen> exprScreen;
    private Expression<Integer> exprSlotWidth;
    private Expression<Integer> exprSlotHeight;
    private Expression<Integer> exprSlotMarginX;
    private Expression<Integer> exprSlotMarginY;
    private Expression<Integer> exprBorderPaddingX;
    private Expression<Integer> exprBorderPaddingY;
    private Expression<Double> exprFontSize;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprScreen = (Expression<WidgetScreen>) exprs[0];
        exprSlotWidth = (Expression<Integer>) exprs[1];
        exprSlotHeight = (Expression<Integer>) exprs[2];
        exprSlotMarginX = (Expression<Integer>) exprs[3];
        exprSlotMarginY = (Expression<Integer>) exprs[4];
        exprBorderPaddingX = (Expression<Integer>) exprs[5];
        exprBorderPaddingY = (Expression<Integer>) exprs[6];
        exprFontSize = (Expression<Double>) exprs[7];
        return true;
    }

    @Override
    protected void execute(Event e) {
        WidgetLayout layout = new WidgetLayout(exprSlotWidth.getSingle(e), exprSlotHeight.getSingle(e), exprSlotMarginX.getSingle(e), exprSlotMarginY.getSingle(e), exprBorderPaddingX.getSingle(e), exprBorderPaddingY.getSingle(e));
        if(exprFontSize != null) {
            layout.setFontSize(exprFontSize.getSingle(e));
        }
        exprScreen.getSingle(e).setLayout(layout);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }
}
