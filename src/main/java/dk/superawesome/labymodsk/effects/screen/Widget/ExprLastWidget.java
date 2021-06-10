package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.common.widgets.components.Widget;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprLastWidget extends SimpleExpression<Widget> {

    static {
        Skript.registerExpression(ExprLastWidget.class, Widget.class, ExpressionType.SIMPLE,
                "[the] [last] [(made|created|generated)] widget"
        );
    }
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Nullable
    @Override
    protected Widget[] get(Event e) {
        return new Widget[]{scopeWidget.lastWidget};
    }

    @Override
    public Class<? extends Widget> getReturnType() {
        return Widget.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "the last generated screen";
    }
}
