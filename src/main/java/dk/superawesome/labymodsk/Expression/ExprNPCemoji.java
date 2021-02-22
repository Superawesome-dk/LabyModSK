package dk.superawesome.labymodsk.Expression;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprNPCemoji extends SimpleExpression<String> {
    private Expression<NPC> npc;
    private Expression<Number> emoji;

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.npc = (Expression<NPC>) e[0];
        this.emoji = (Expression<Number>) e[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return null;
    }

    @Override
    @Nullable
    protected String[] get(Event event) {
        if(npc.getSingle(event) == null) {
            Skript.error("The npc does not exist.");
        } else {
            JsonObject forcedEmote = new JsonObject();
            forcedEmote.addProperty( "uuid", npc.getSingle(event).getUniqueId().toString());
            forcedEmote.addProperty( "emote_id", emoji.getSingle(event) );
            Gson gson = new Gson();
            return new String[] {gson.fromJson(forcedEmote, JsonObject.class).toString()};
        }
        return new String[] {null};
    }
}
