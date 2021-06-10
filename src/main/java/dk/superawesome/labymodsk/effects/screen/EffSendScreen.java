package dk.superawesome.labymodsk.effects.screen;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.common.widgets.WidgetScreen;
import net.labymod.serverapi.common.widgets.components.Widget;
import net.labymod.serverapi.common.widgets.util.EnumScreenAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffSendScreen extends Effect {

    private Expression<WidgetScreen> exprScreen;
    private Expression<Integer> exprActionid;
    private Expression<Player> exprPlayer;

    static {
        Skript.registerEffect(EffSendScreen.class,
                "[LabyModSK] send [screen] %screen% with actionId %integer% to %players%");
    }


    @Override
    protected void execute(Event e) {
        for(Player target : exprPlayer.getArray(e)) {
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(target.getUniqueId(), "screen", exprScreen.getSingle(e).toJsonObject(EnumScreenAction.values()[exprActionid.getSingle(e)]));
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "send screen " + exprScreen.toString(e, debug) + " to player " + exprPlayer.toString(e, debug);
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        exprScreen = (Expression<WidgetScreen>) exprs[0];
        exprActionid = (Expression<Integer>) exprs[1];
        exprPlayer = (Expression<Player>) exprs[2];
        return true;
    }
}
