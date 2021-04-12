package dk.superawesome.labymodsk.Expression.player;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.player.LabyModPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ExprLabyVersion extends SimpleExpression<String> {
    private Expression<Player> target;

    @Override
    public Class<? extends String> getReturnType() {
        //1
        return String.class;
    }

    @Override
    public boolean isSingle() {
        //2
        return true;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.target = (Expression<Player>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        //4
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        String version = null;
        Optional<LabyModPlayer<Object>> LabyPlayer = LabyAPI.getService().getLabyPlayerService().getPlayer(target.getSingle(event).getUniqueId());
        if(LabyPlayer.isPresent()) {
            version = LabyPlayer.get().getVersion();
        }
        return new String[] {version};
    }
}
