package dk.superawesome.labymodsk.effects.screen.Widget;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import dk.superawesome.labymodsk.effects.screen.EffSetLayout;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.Widget;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffAddWidget extends Effect {
    static {
        Skript.registerEffect(EffAddWidget.class,
                "[LabyModSK] add %widget% to widgets of %screen%"
        );
    }

    private Expression<Widget> exprWidget;
    private Expression<WidgetScreen> exprScreen;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprWidget = (Expression<Widget>) exprs[0];
        exprScreen = (Expression<WidgetScreen>) exprs[1];
        return true;
    }

    @Override
    protected void execute(Event e) {
        exprScreen.getSingle(e).addWidget(exprWidget.getSingle(e));
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }
}
