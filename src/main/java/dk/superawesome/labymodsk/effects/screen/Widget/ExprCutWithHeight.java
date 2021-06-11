package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import dk.superawesome.labymodsk.classes.CutWithHeight;
import dk.superawesome.labymodsk.classes.CutXY;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCutWithHeight extends SimpleExpression<CutWithHeight> {
    static {
        Skript.registerExpression(ExprCutWithHeight.class, CutWithHeight.class, ExpressionType.COMBINED, "[the] cutwithheight %number%, %number%");
    }

    private Expression<Number> exprX;
    private Expression<Number> exprY;

    public Class<? extends CutWithHeight> getReturnType() {
        return CutWithHeight.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        exprX = (Expression<Number>) e[0];
        exprY = (Expression<Number>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected CutWithHeight[] get(Event event) {
        return new CutWithHeight[] {new CutWithHeight(exprX.getSingle(event).intValue(), exprY.getSingle(event).intValue())};
    }

}
