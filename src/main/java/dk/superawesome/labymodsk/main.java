package dk.superawesome.labymodsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Getter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dk.superawesome.labymodsk.Expression.ActionEntry;
import dk.superawesome.labymodsk.Expression.ExprNpcFromID;
import dk.superawesome.labymodsk.Expression.Subtitle;
import dk.superawesome.labymodsk.classes.MessageKey;
import dk.superawesome.labymodsk.classes.ModVersion;
import dk.superawesome.labymodsk.commands.labymodsk;
import dk.superawesome.labymodsk.effects.*;
import net.citizensnpcs.api.npc.NPC;
import net.labymod.serverapi.Addon;
import net.labymod.serverapi.bukkit.event.LabyModPlayerJoinEvent;
import net.labymod.serverapi.bukkit.event.MessageReceiveEvent;
import net.labymod.serverapi.bukkit.event.MessageSendEvent;
import net.labymod.serverapi.bukkit.event.PermissionsSendEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ch.njol.skript.registrations.EventValues.registerEventValue;

public final class main extends JavaPlugin {
    public static main instance;
    public static SkriptAddon addonInstance;

    @Override
    public void onEnable() {
        registerClasses();


        instance = this;
        addonInstance = Skript.registerAddon(instance);
        if(Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            this.getCommand("labymodsk").setExecutor(new labymodsk());
            Skript.registerExpression(ExprNpcFromID.class, NPC.class, ExpressionType.COMBINED, "[the] (npc|citizen) (of|from|with id) %number%");
            Skript.registerEffect(NPCemoji.class, "play emoji for %citizen% to %number% for %players%");
            Skript.registerEffect(NPCsticker.class, "play sticker for %citizen% to %number% for %players%");
        }
        Skript.registerEffect(InputPrompt.class, "show input prompt with id %number% and message %string% and value %string% and placeholder %string% and max length %number% to %players%");
        Skript.registerEffect(DiscordRich.class, "set discord rich for %players% to %string%");
        Skript.registerEffect(PlayingNow.class, "set playing now for %players% to %string%");

        Skript.registerExpression(Subtitle.class, String.class, ExpressionType.COMBINED, "[the] subtitle for %players%[ and value %-string%][ and size %-number%]");
        Skript.registerEffect(Subtitles.class, "show subtitles %strings% for %players%");
        Skript.registerEffect(EconomyDisplay.class, "set economy display %string% for %players% to %number%");
        Skript.registerEffect(EconomyDisplay.class, "set economy display %string% for %players% to %number%");
        Skript.registerEffect(Cinescopes.class, "show cinescop with coverage %number% in %number% seconds to %players%");
        Skript.registerEffect(ActionMenu.class, "show actionmenu %strings% to %players%");
        Skript.registerExpression(ActionEntry.class, String.class, ExpressionType.COMBINED, "[the] action entry with displayname %string% and type %string% and value %string%");
        Skript.registerEvent("labymod permissionsend", SimpleEvent.class, PermissionsSendEvent.class, "labymod permissionsend");
        /*registerEventValue(PermissionsSendEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, PermissionsSendEvent>() {
            @Override
            public OfflinePlayer get(PermissionsSendEvent event) {
                return event.getPlayer();
            }
        }, 0); */
        Skript.registerEvent("labymod join", SimpleEvent.class, LabyModPlayerJoinEvent.class, "labymod join");
        registerEventValue(LabyModPlayerJoinEvent.class, Player.class, new Getter<Player, LabyModPlayerJoinEvent>() {
            @Override
            public Player get(LabyModPlayerJoinEvent event) {
                return event.getPlayer();
            }
        }, 0);
        registerEventValue(LabyModPlayerJoinEvent.class, ModVersion.class, new Getter<ModVersion, LabyModPlayerJoinEvent>() {
            @Override
            public ModVersion get(LabyModPlayerJoinEvent event) {
                return new ModVersion(event.getModVersion());
            }
        }, 0);
        registerEventValue(LabyModPlayerJoinEvent.class, String.class, new Getter<String, LabyModPlayerJoinEvent>() {
            @Override
            public String get(LabyModPlayerJoinEvent event) {
                JsonArray addonList = new JsonArray();
                for (Addon addon : event.getAddons()) {
                    JsonObject addonIndex = new JsonObject();
                    addonIndex.addProperty("uuid", String.valueOf(addon.getUuid()));
                    addonIndex.addProperty("name", addon.getName());
                    addonList.add(addonIndex);
                }
                Gson gson = new Gson();
                return gson.fromJson(addonList, JsonArray.class).toString();
            }
        }, 0);
        Skript.registerEvent("labymod message send", SimpleEvent.class, MessageSendEvent.class, "labymod message send");
        registerEventValue(MessageSendEvent.class, Player.class, new Getter<Player, MessageSendEvent>() {
            @Override
            public Player get(MessageSendEvent event) {
                return event.getPlayer();
            }
        }, 0);
        registerEventValue(MessageSendEvent.class, Player.class, new Getter<Player, MessageSendEvent>() {
            @Override
            public Player get(MessageSendEvent event) {
                return event.getPlayer();
            }
        }, 0);
        Skript.registerEvent("labymod message receive", SimpleEvent.class, MessageReceiveEvent.class, "labymod message receive");
        registerEventValue(MessageReceiveEvent.class, Player.class, new Getter<Player, MessageReceiveEvent>() {
            @Override
            public Player get(MessageReceiveEvent event) {
                return event.getPlayer();
            }
        }, 0);
        registerEventValue(MessageReceiveEvent.class, MessageKey.class, new Getter<MessageKey, MessageReceiveEvent>() {
            @Override
            public MessageKey get(MessageReceiveEvent event) {
                return new MessageKey(event.getMessageKey());
            }
        }, 0);
        registerEventValue(MessageReceiveEvent.class, String.class, new Getter<String, MessageReceiveEvent>() {
            @Override
            public String get(MessageReceiveEvent event) {
                Gson gson = new Gson();
                return gson.fromJson(event.getJsonElement(), JsonElement.class).toString();
            }
        }, 0);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerClasses() {
        if(Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            Classes.registerClass(new ClassInfo<NPC>(NPC.class, "citizen")
                    .name("npc")
                    .description("A getter for npc")
                    .parser(new Parser<NPC>() {
                        @Override
                        @Nullable
                        public NPC parse(String obj, ParseContext context) {
                            return null;
                        }

                        @Override
                        public String toString(NPC e, int flags) {
                            return e.toString();
                        }

                        @Override
                        public String toVariableNameString(NPC e) {
                            return e.toString();
                        }

                        public String getVariableNamePattern() {
                            return ".+";
                        }
                    }));
        }
        Classes.registerClass(new ClassInfo<>(ModVersion.class, "modversion")
                .defaultExpression(new EventValueExpression<>(ModVersion.class))
                .user("modversion")
                .name("modversion")
                .parser(new Parser<ModVersion>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public ModVersion parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(ModVersion arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(ModVersion arg0) {
                        return arg0.toString();
                    }

                }));
        Classes.registerClass(new ClassInfo<>(MessageKey.class, "messagekey")
                .defaultExpression(new EventValueExpression<>(MessageKey.class))
                .user("messagekey")
                .name("messagekey")
                .parser(new Parser<MessageKey>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public MessageKey parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(MessageKey arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(MessageKey arg0) {
                        return arg0.toString();
                    }

                }));
    }
}
