package dk.superawesome.labymodsk.Expression.player;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.player.LabyModPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ExprLabyAddons extends SimpleExpression<String> {
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
        JsonArray addonList = new JsonArray();
        Optional<LabyModPlayer<Object>> LabyPlayer = LabyAPI.getService().getLabyPlayerService().getPlayer(target.getSingle(event).getUniqueId());
        if(LabyPlayer.isPresent()) {
            List<AddonExtension> addons = LabyPlayer.get().getAddons();
            for (AddonExtension addon : addons) {
                JsonObject addonIndex = new JsonObject();
                addonIndex.addProperty("uuid", String.valueOf(addon.getIdentifier()));
                addonIndex.addProperty("name", addon.getName());
                addonList.add(addonIndex);
            }
        }
        return new String[] {new Gson().fromJson(addonList, JsonArray.class).toString()};
    }
}
