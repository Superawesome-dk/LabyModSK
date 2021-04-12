package dk.superawesome.labymodsk.condition;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.player.LabyModPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CondUsingLabymod extends Condition {
    Expression<Player> Target;
    @Override
    public boolean check(Event e) {
        Boolean Usinglaby = false;
        Optional<LabyModPlayer<Object>> LabyPlayer = LabyAPI.getService().getLabyPlayerService().getPlayer(Target.getSingle(e).getUniqueId());
        if(LabyPlayer.isPresent()) {
            Usinglaby = true;
        }
        return Usinglaby;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.Target = (Expression<Player>) exprs[0];
        return true;
    }
}
