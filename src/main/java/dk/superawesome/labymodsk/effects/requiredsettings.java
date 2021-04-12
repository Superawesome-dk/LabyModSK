package dk.superawesome.labymodsk.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Map;

public class requiredsettings extends Effect {
    private Expression<Player> targets;
    private Expression<String> settings;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.targets = (Expression<Player>) expressions[1];
        this.settings = (Expression<String>) expressions[0];
        return true;
    }
    @Override
    public String toString(Event arg0, boolean arg1) {
        return null;
    }

    @Override
    protected void execute(Event event) {
        Gson gson = new Gson();
        JsonObject voicechatObject = new JsonObject();

        // "Keep settings" means that the settings are only reset when the player leaves the entire server network.
        // If this setting is set to "false", everything will be reset when the player switches the lobby for example.
        voicechatObject.addProperty( "keep_settings_on_server_switch", false );

        JsonObject requestSettingsObject = new JsonObject();

        // Required means the player MUST accept the settings to access the server. It's no longer a suggestion.
        requestSettingsObject.addProperty("required", true );

        JsonObject settingsObject = new JsonObject();
        for(String a : settings.getArray(event)) {
            JsonObject object = gson.fromJson(a, JsonObject.class);
            for (Map.Entry<String, JsonElement> entry : object.entrySet() ) {
                try {
                    settingsObject.addProperty(entry.getKey(), entry.getValue().getAsNumber());
                } catch (ClassCastException ex) {
                    settingsObject.addProperty(entry.getKey(), entry.getValue().getAsBoolean());
                }
            }
        }

        requestSettingsObject.add("settings", settingsObject);
        voicechatObject.add("request_settings", requestSettingsObject);
        for (Player player : targets.getArray(event)) {
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "voicechat", voicechatObject );
        }
    }
}
