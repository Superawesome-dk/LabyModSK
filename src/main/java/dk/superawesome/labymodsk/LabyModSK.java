package dk.superawesome.labymodsk;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Getter;
import ch.njol.yggdrasil.Fields;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dk.superawesome.labymodsk.Expression.*;
import dk.superawesome.labymodsk.classes.MessageKey;
import dk.superawesome.labymodsk.classes.ModVersion;
import dk.superawesome.labymodsk.classes.addons;
import dk.superawesome.labymodsk.commands.labymodsk;
import dk.superawesome.labymodsk.effects.*;
import net.citizensnpcs.api.npc.NPC;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.bukkit.event.BukkitLabyModPlayerLoginEvent;
import net.labymod.serverapi.bukkit.event.BukkitMessageReceiveEvent;
import net.labymod.serverapi.common.extension.DefaultAddonExtension;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static ch.njol.skript.registrations.EventValues.registerEventValue;

public final class LabyModSK extends JavaPlugin {
    public static LabyModSK instance;
    public static SkriptAddon addonInstance;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 10521);
        registerClasses();
        instance = this;
        addonInstance = Skript.registerAddon(instance);
        if(Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            this.getCommand("labymodsk").setExecutor(new labymodsk());
            Skript.registerExpression(ExprNpcFromID.class, NPC.class, ExpressionType.COMBINED, "[the] (npc|citizen) (of|from|with id) %number%");
            Skript.registerExpression(ExprNPCemoji.class, String.class, ExpressionType.COMBINED, "emoji for %citizen% with emojiid %number%");
            Skript.registerEffect(NPCemoji.class, "show emojis %strings% to %players%");
            Skript.registerExpression(ExprNPCsticker.class, String.class, ExpressionType.COMBINED, "sticker for %citizen% with stickerid %number%");
            Skript.registerEffect(NPCsticker.class, "show stickers %strings% to %players%");
        }

        // permissions
        Skript.registerExpression(ExprPermission.class, String.class, ExpressionType.COMBINED, "[the] permission %string% with boolean %boolean%");
        Skript.registerEffect(Permissions.class, "send permissions %strings% to %players%");

        // addons
        Skript.registerExpression(AddonEntry.class, String.class, ExpressionType.COMBINED, "[the] addon with uuid %string% and required %boolean%");
        Skript.registerEffect(recommendAddons.class, "show addon recommendation with addons %strings% to %players%");

        // cinematic
        Skript.registerExpression(point.class, String.class, ExpressionType.COMBINED, "[the] point at %location% with tilt %number%");
        Skript.registerEffect(Cinematic.class, "show cinematic %strings% in %timespan% to %players%");

        // cinescope
        Skript.registerEffect(Cinescopes.class, "show cinescope with coverage %number% in %timespan% to %players%");

        //input prompt
        Skript.registerEffect(InputPrompt.class, "show input prompt with id %number% and message %string% and value %string% and placeholder %string% and max length %number% to %players%");

        //discord rich
        Skript.registerEffect(DiscordRich.class, "set discord rich for %players% to %string%[ with (starttime %-timespan%|endtime %-timespan%)]");
        Skript.registerEffect(DiscordParty.class, "send discord party with id %string% and size %number% and max %number% to %players%");
        Skript.registerEffect(DiscordGame.class, "send discord match [with matchsecret %-string% and spectatesecret %-string% and joinsecret %-string%] to %players%");

        // playing now
        Skript.registerEffect(PlayingNow.class, "set playing now for %players% to %string%");

        // subtitles
        Skript.registerExpression(Subtitle.class, String.class, ExpressionType.COMBINED, "[the] subtitle for %players%[ and value %-string%][ and size %-number%]");
        Skript.registerEffect(Subtitles.class, "show subtitles %strings% for %players%");

        // economy display
        Skript.registerEffect(EconomyDisplay.class, "set economy display %string% for %players% to %number%");

        // voicechat
        Skript.registerEffect(EnableVoicechat.class, "enable voicechat for %players%");
        Skript.registerEffect(DisableVoicechat.class, "disable voicechat for %players%");
        Skript.registerEffect(muteplayer.class, "voicechat mute %players% for %players%");
        Skript.registerEffect(unmuteplayer.class, "voicechat unmute %players% for %players%");
        Skript.registerExpression(ExprVoicesetting.class, String.class, ExpressionType.COMBINED, "setting %string% with value (%-number%|%-boolean%)");
        Skript.registerEffect(requiredsettings.class, "send required voicechat settings %strings% for %players%");
        Skript.registerEffect(voicechatsettings.class, "send voicechat settings %strings% for %players%");

        Skript.registerEffect(EnableWatermark.class, "enable watermark for %players%");
        Skript.registerEffect(DisableVoicechat.class, "disable watermark for %players%");
        // actionmenu
        Skript.registerEffect(ActionMenu.class, "show actionmenu %strings% to %players%");
        Skript.registerExpression(ActionEntry.class, String.class, ExpressionType.COMBINED, "[the] action entry with displayname %string% and type %string% and value %string%");

        // events
        Skript.registerEvent("labymod join", SimpleEvent.class, BukkitLabyModPlayerLoginEvent.class, "labymod join");
        registerEventValue(BukkitLabyModPlayerLoginEvent.class, Player.class, new Getter<Player, BukkitLabyModPlayerLoginEvent>() {
            @Override
            public Player get(BukkitLabyModPlayerLoginEvent event) {
                return event.getPlayer();
            }
        }, 0);
        registerEventValue(BukkitLabyModPlayerLoginEvent.class, ModVersion.class, new Getter<ModVersion, BukkitLabyModPlayerLoginEvent>() {
            @Override
            public ModVersion get(BukkitLabyModPlayerLoginEvent event) {
                return new ModVersion(event.getVersion());
            }
        }, 0);
        registerEventValue(BukkitLabyModPlayerLoginEvent.class, AddonExtension[].class, new Getter<AddonExtension[], BukkitLabyModPlayerLoginEvent>() {
            @Override
            public AddonExtension[] get(BukkitLabyModPlayerLoginEvent event) {
                return event.getAddonExtensions().toArray(new AddonExtension[event.getAddonExtensions().size()]);
            }
        }, 0);
        Skript.registerEvent("labymod message receive", SimpleEvent.class, BukkitMessageReceiveEvent.class, "labymod message receive");
        registerEventValue(BukkitMessageReceiveEvent.class, Player.class, new Getter<Player, BukkitMessageReceiveEvent>() {
            @Override
            public Player get(BukkitMessageReceiveEvent event) {
                return event.getPlayer();
            }
        }, 0);
        registerEventValue(BukkitMessageReceiveEvent.class, MessageKey.class, new Getter<MessageKey, BukkitMessageReceiveEvent>() {
            @Override
            public MessageKey get(BukkitMessageReceiveEvent event) {
                return new MessageKey(event.getMessageKey());
            }
        }, 0);
        registerEventValue(BukkitMessageReceiveEvent.class, String.class, new Getter<String, BukkitMessageReceiveEvent>() {
            @Override
            public String get(BukkitMessageReceiveEvent event) {
                Gson gson = new Gson();
                return gson.fromJson(event.getMessageContent(), JsonElement.class).toString();
            }
        }, 0);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void registerClasses() {
        Classes.registerClass(new ClassInfo<>(AddonExtension[].class, "addonextension")
                .user("addonextension")
                .name("Addonextension")
                .description("Represents a raid from a pillager raid on a village.")
                .examples("on raid start:", "\tbroadcast \"A raid has started at level %omen level of event-raid%\"")
                .defaultExpression(new EventValueExpression<>(AddonExtension[].class))
                .parser(new Parser<AddonExtension[]>() {

                    @Override
                    public AddonExtension[] parse(String input, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toVariableNameString(AddonExtension[] raid) {
                        System.out.println("variablename");
                        return "addonextension";
                    }

                    @Override
                    public String getVariableNamePattern() {
                        System.out.println("namepattern");
                        return ".+";
                    }

                    @Override
                    public String toString(AddonExtension[] addon, int flags) {
                        System.out.println("tostring");
                        return "array";
                    }
                })
        );
        Classes.registerClass(new ClassInfo<>(DefaultAddonExtension.class, "defaultaddonextension")
                .user("defaultaddonextension")
                .name("Defaultaddonextension")
                .description("Represents a raid from a pillager raid on a village.")
                .examples("on raid start:", "\tbroadcast \"A raid has started at level %omen level of event-raid%\"")
                .defaultExpression(new EventValueExpression<>(DefaultAddonExtension.class))
                .parser(new Parser<DefaultAddonExtension>() {

                    @Override
                    public DefaultAddonExtension parse(String input, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toVariableNameString(DefaultAddonExtension raid) {
                        return "addonextension";
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "addon";
                    }

                    @Override
                    public String toString(DefaultAddonExtension addon, int flags) {
                        return String.valueOf(addon.getIdentifier());
                    }
                })
        );
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
        Classes.registerClass(new ClassInfo<>(addons.class, "addons")
                .defaultExpression(new EventValueExpression<>(addons.class))
                .user("addons")
                .name("addons")
                .parser(new Parser<addons>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public addons parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(addons arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(addons arg0) {
                        return arg0.toString();
                    }

                }));
    }
}
