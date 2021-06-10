package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.superawesome.labymodsk.Utils.EffectSection;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.util.EnumScreenAction;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class scopeScreen extends EffectSection {
    public static WidgetScreen lastScreen;
    private Expression<Integer> exprID;

    static {
        Skript.registerCondition(scopeScreen.class, "make [new] [labymod] screen with id %integer%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprID = (Expression<Integer>) exprs[0];
        if (checkIfCondition()) return false;
        if (!hasSection()) return false;
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        WidgetScreen screen = new WidgetScreen(exprID.getSingle(e));
        lastScreen = screen;
        runSection(e);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new labymod screen with id " + exprID.getSingle(e);
    }

}
