package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprAnchor extends SimpleExpression<Anchor> {
    static {
        Skript.registerExpression(ExprAnchor.class, Anchor.class, ExpressionType.COMBINED, "[the] anchor %double%, %double%");
    }

    private Expression<Double> exprX;
    private Expression<Double> exprY;

    public Class<? extends Anchor> getReturnType() {
        return Anchor.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        exprX = (Expression<Double>) e[0];
        exprY = (Expression<Double>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected Anchor[] get(Event event) {
        return new Anchor[] {new Anchor(exprX.getSingle(event), exprY.getSingle(event))};
    }

}
