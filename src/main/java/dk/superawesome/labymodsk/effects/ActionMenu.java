package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ActionMenu extends Effect {
    private Expression<String> ActionMenu;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.ActionMenu = (Expression<String>) expressions[0];
        this.player = (Expression<Player>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        Gson gson = new Gson();
        JsonArray menu = new JsonArray();
        for (String actionEntry : ActionMenu.getArray(event)) {
            JsonObject convertedactionEntry = gson.fromJson(actionEntry, JsonObject.class);
            menu.add(convertedactionEntry);
        }
        for (Player player : player.getArray(event))
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "user_menu_actions", menu);
    }
}
