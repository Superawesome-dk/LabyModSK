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
import dk.superawesome.labymodsk.Expression.ActionMenu;
import dk.superawesome.labymodsk.Expression.npcs.ExprNpcFromID;
import dk.superawesome.labymodsk.classes.Addons;
import dk.superawesome.labymodsk.classes.ModVersion;
import dk.superawesome.labymodsk.commands.labymodsk;
import dk.superawesome.labymodsk.effects.*;
import net.citizensnpcs.api.npc.NPC;
import net.labymod.serverapi.Addon;
import net.labymod.serverapi.bukkit.event.LabyModPlayerJoinEvent;
import net.labymod.serverapi.bukkit.event.MessageSendEvent;
import net.labymod.serverapi.bukkit.event.PermissionsSendEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static ch.njol.skript.registrations.EventValues.registerEventValue;

public final class main extends JavaPlugin {
    public static main instance;
    public static SkriptAddon addonInstance;

    @Override
    public void onEnable() {
        registerClasses();


        instance = this;
        addonInstance = Skript.registerAddon(instance);

        this.getCommand("labymodsk").setExecutor(new labymodsk());
        Skript.registerExpression(ExprNpcFromID.class, NPC.class, ExpressionType.COMBINED, "[the] (npc|citizen) (of|from) %number%");
        Skript.registerEffect(NPCemoji.class, "play emoji for %citizen% to %number% for %players%");
        Skript.registerEffect(NPCsticker.class, "play sticker for %citizen% to %number% for %players%");
        Skript.registerEffect(DiscordRich.class, "set discord rich for %players% to %string%");
        Skript.registerEffect(PlayingNow.class, "set playing now for %players% to %string%");
        Skript.registerEffect(SubleTitle.class, "set subletitle for %players% to %string% with size %number% for %players%");
        Skript.registerExpression(ActionMenu.class, String.class, ExpressionType.COMBINED, "[the] action menu of %players%");
        Skript.registerEvent("labymod permissionsend", SimpleEvent.class, PermissionsSendEvent.class, "labymod permissionsend");
        /*registerEventValue(PermissionsSendEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, PermissionsSendEvent>() {
            @Override
            public OfflinePlayer get(PermissionsSendEvent event) {
                return event.getPlayer();
            }
        }, 0); */
        Skript.registerEvent("labymod join", SimpleEvent.class, LabyModPlayerJoinEvent.class, "labymod join");
        registerEventValue(LabyModPlayerJoinEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, LabyModPlayerJoinEvent>() {
            @Override
            public OfflinePlayer get(LabyModPlayerJoinEvent event) {
                return event.getPlayer();
            }
        }, 0);
        registerEventValue(LabyModPlayerJoinEvent.class, ModVersion.class, new Getter<ModVersion, LabyModPlayerJoinEvent>() {
            @Override
            public ModVersion get(LabyModPlayerJoinEvent event) {
                return new ModVersion(event.getModVersion());
            }
        }, 0);
        /*Skript.registerEvent("labymod message send", SimpleEvent.class, MessageSendEvent.class, "labymod message send");
        registerEventValue(MessageSendEvent.class, OfflinePlayer.class, new Getter<OfflinePlayer, MessageSendEvent>() {
            @Override
            public OfflinePlayer get(MessageSendEvent event) {
                return event.getPlayer();
            }
        }, 0); */
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerClasses() {
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
                    }}));
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
    }
}
