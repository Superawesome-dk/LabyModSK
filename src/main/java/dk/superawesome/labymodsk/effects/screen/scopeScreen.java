package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dk.superawesome.labymodsk.Utils.EffectSection;
import net.labymod.serverapi.common.widgets.util.EnumScreenAction;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class scopeScreen extends EffectSection {
    public static JsonObject lastScreen;
    private Expression<Number> exprID;
    private Expression<Number> exprACTION;

    static {
        Skript.registerCondition(scopeScreen.class, "make [new] [labymod] screen with id %number% and action id %number%");
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprID = (Expression<Number>) exprs[0];
        exprACTION = (Expression<Number>) exprs[1];
        if (checkIfCondition()) return false;
        if (!hasSection()) return false;
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        JsonObject screen = new JsonObject();
        screen.addProperty("id", exprID.getSingle(e)); // Screen id. The client will send this id back on an interaction
        screen.addProperty("action", exprACTION.getSingle(e)); // Open the GUI
        screen.add("widgets", new JsonArray());
        lastScreen = screen;
        runSection(e);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "make new labymod screen with id " + exprID.getSingle(e) + " and actionid " + exprACTION.getSingle(e);
    }

}
