package dk.superawesome.labymodsk.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.labymod.serverapi.LabyModAPI;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.json.simple.parser.JSONParser;


public class NPCemoji extends Effect {
    private Expression<NPC> npc;
    private Expression<Number> emoji;
    private Expression<Player> player;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.npc = (Expression<NPC>) expressions[0];
        this.emoji = (Expression<Number>) expressions[1];
        this.player = (Expression<Player>) expressions[2];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return "play emoji for %citizen% to %number% for %players%";
    }

    @Override
    protected void execute(Event event) {
        if(npc.getSingle(event) == null) {
            Skript.error("The npc does not exist.");
        } else {
            JsonArray array = new JsonArray();

            JsonObject forcedEmote = new JsonObject();
            forcedEmote.addProperty( "uuid", npc.getSingle(event).getUniqueId().toString());
            forcedEmote.addProperty( "emote_id", emoji.getSingle(event) );

            array.add(forcedEmote);
            for (Player player : player.getArray(event))
                LabyModPlugin.getInstance().sendServerMessage(player, "emote_api", array);
        }
    }
}
