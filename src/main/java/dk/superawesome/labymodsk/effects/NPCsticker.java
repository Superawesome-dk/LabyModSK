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


public class NPCsticker extends Effect {
    private Expression<String> stickers;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.stickers = (Expression<String>) expressions[0];
        this.player = (Expression<Player>) expressions[1];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return "show stickers %strings% for %players%";
    }

    @Override
    protected void execute(Event event) {
        JsonArray array = new JsonArray();
        for(String a : stickers.getArray(event)) {
            Gson gson = new Gson();
            JsonObject forcedEmote = gson.fromJson(a, JsonObject.class);

            array.add(forcedEmote);
        }
        for (Player player : player.getArray(event))
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "sticker_api", array);
    }
}
