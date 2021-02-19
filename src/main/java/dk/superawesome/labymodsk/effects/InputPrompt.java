package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.JsonObject;
import net.labymod.serverapi.bukkit.LabyModPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class InputPrompt extends Effect {
    private Expression<Player> player;
    private Expression<Integer> id;
    private Expression<String> message;
    private Expression<String> value;
    private Expression<String> placeholder;
    private Expression<Integer> maxLength;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[5];
        this.maxLength = (Expression<Integer>) expressions[4];
        this.message = (Expression<String>) expressions[3];
        this.value = (Expression<String>) expressions[2];
        this.placeholder = (Expression<String>) expressions[1];
        this.id = (Expression<Integer>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        JsonObject object = new JsonObject();
        object.addProperty( "id", id.getSingle(event) );
        object.addProperty( "message", message.getSingle(event) );
        object.addProperty( "value", value.getSingle(event) );
        object.addProperty( "placeholder", placeholder.getSingle(event) );
        object.addProperty( "max_length", maxLength.getSingle(event) );
        for (Player player : player.getArray(event))
            LabyModPlugin.getInstance().sendServerMessage( player, "input_prompt", object );
    }
}
