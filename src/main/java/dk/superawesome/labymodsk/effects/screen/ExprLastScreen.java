package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class ExprLastScreen extends SimpleExpression<WidgetScreen> {

    static {
        Skript.registerExpression(ExprLastScreen.class, WidgetScreen.class, ExpressionType.SIMPLE,
                "[the] [last] [(made|created|generated)] screen"
        );
    }
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Nullable
    @Override
    protected WidgetScreen[] get(Event e) {
        return new WidgetScreen[]{scopeScreen.lastScreen};
    }

    @Override
    public Class<? extends WidgetScreen> getReturnType() {
        return WidgetScreen.class;
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
