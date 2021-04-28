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
import dk.superawesome.labymodsk.Expression.*;
import dk.superawesome.labymodsk.Expression.player.ExprLabyAddons;
import dk.superawesome.labymodsk.Expression.player.ExprLabyMods;
import dk.superawesome.labymodsk.Expression.player.ExprLabyPackages;
import dk.superawesome.labymodsk.Expression.player.ExprLabyVersion;
import dk.superawesome.labymodsk.Utils.Util;
import dk.superawesome.labymodsk.classes.*;
import dk.superawesome.labymodsk.commands.labymodsk;
import dk.superawesome.labymodsk.condition.CondUsingLabymod;
import dk.superawesome.labymodsk.effects.*;
import net.citizensnpcs.api.npc.NPC;
import net.labymod.serverapi.api.extension.AddonExtension;
import net.labymod.serverapi.api.extension.ModificationExtension;
import net.labymod.serverapi.api.extension.PackageExtension;
import net.labymod.serverapi.bukkit.event.BukkitLabyModPlayerLoginEvent;
import net.labymod.serverapi.bukkit.event.BukkitMessageReceiveEvent;
import net.labymod.serverapi.common.widgets.util.Anchor;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import javax.management.openmbean.SimpleType;
import java.awt.*;
import java.io.IOException;

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

        try {
            //This will register all our syntax for us. Explained below
            addonInstance.loadClasses("dk.superawesome.labymodsk.effects.screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        Skript.registerEffect(ShowEconomyDisplay.class, "show economy display %string% with balance %number%[ and custom url %-string%][ and decimal format %-string% with divisor %-number%] for %players%");
        Skript.registerEffect(HideEconomyDisplay.class, "hide economy display %string% for %players%");

        // voicechat
        Skript.registerEffect(EnableVoicechat.class, "enable voicechat for %players%");
        Skript.registerEffect(DisableVoicechat.class, "disable voicechat for %players%");
        Skript.registerEffect(muteplayer.class, "voicechat mute %players% for %players%");
        Skript.registerEffect(unmuteplayer.class, "voicechat unmute %players% for %players%");
        Skript.registerExpression(ExprVoicesetting.class, String.class, ExpressionType.COMBINED, "setting %string% with value (%-number%|%-boolean%)");
        Skript.registerEffect(requiredsettings.class, "send required voicechat settings %strings% for %players%");
        Skript.registerEffect(voicechatsettings.class, "send voicechat settings %strings% for %players%");

        // Laby player
        Skript.registerCondition(CondUsingLabymod.class, "%player% is using labymod");
        Skript.registerExpression(ExprLabyVersion.class, String.class, ExpressionType.COMBINED, "[the] labymod version of %player%");
        Skript.registerExpression(ExprLabyAddons.class, String.class, ExpressionType.COMBINED, "[the] labymod addons of %player%");
        Skript.registerExpression(ExprLabyMods.class, String.class, ExpressionType.COMBINED, "[the] labymod mods of %player%");
        Skript.registerExpression(ExprLabyPackages.class, String.class, ExpressionType.COMBINED, "[the] labymod packages of %player%");

        // watermark
        Skript.registerEffect(ShowWatermark.class, "show watermark for %players%");
        Skript.registerEffect(HideWatermark.class, "hide watermark for %players%");
        Skript.registerEffect(ShowServerBanner.class, "show server banner with url %string% for %players%");
        Skript.registerEffect(HideServerBanner.class, "hide server banner for %players%");

        Skript.registerExpression(FlagUser.class, String.class, ExpressionType.COMBINED, "[the] flag for %players% and country %string%");
        Skript.registerEffect(Flags.class, "show flaguser %strings% for %players%");

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
        registerEventValue(BukkitLabyModPlayerLoginEvent.class, addons.class, new Getter<addons, BukkitLabyModPlayerLoginEvent>() {
            @Override
            public addons get(BukkitLabyModPlayerLoginEvent event) {
                JsonArray addonList = new JsonArray();
                for (AddonExtension addon : event.getAddonExtensions()) {
                    JsonObject addonIndex = new JsonObject();
                    addonIndex.addProperty("uuid", String.valueOf(addon.getIdentifier()));
                    addonIndex.addProperty("name", addon.getName());
                    addonList.add(addonIndex);
                }
                Gson gson = new Gson();
                return new addons(gson.fromJson(addonList, JsonArray.class).toString());
            }
        }, 0);
        registerEventValue(BukkitLabyModPlayerLoginEvent.class, mods.class, new Getter<mods, BukkitLabyModPlayerLoginEvent>() {
            @Override
            public mods get(BukkitLabyModPlayerLoginEvent event) {
                JsonArray addonList = new JsonArray();
                for (ModificationExtension mod : event.getModificationExtensions()) {
                    JsonObject addonIndex = new JsonObject();
                    addonIndex.addProperty("uuid", String.valueOf(mod.getIdentifier()));
                    addonIndex.addProperty("name", mod.getName());
                    addonList.add(addonIndex);
                }
                Gson gson = new Gson();
                return new mods(gson.fromJson(addonList, JsonArray.class).toString());
            }
        }, 0);
        registerEventValue(BukkitLabyModPlayerLoginEvent.class, packages.class, new Getter<packages, BukkitLabyModPlayerLoginEvent>() {
            @Override
            public packages get(BukkitLabyModPlayerLoginEvent event) {
                JsonArray addonList = new JsonArray();
                for (PackageExtension mod : event.getPackageExtensions()) {
                    JsonObject addonIndex = new JsonObject();
                    addonIndex.addProperty("uuid", String.valueOf(mod.getIdentifier()));
                    addonIndex.addProperty("name", mod.getName());
                    addonList.add(addonIndex);
                }
                Gson gson = new Gson();
                return new packages(gson.fromJson(addonList, JsonArray.class).toString());
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
        registerEventValue(BukkitMessageReceiveEvent.class, messagecontent.class, new Getter<messagecontent, BukkitMessageReceiveEvent>() {
            @Override
            public messagecontent get(BukkitMessageReceiveEvent event) {
                Gson gson = new Gson();
                return new messagecontent(gson.fromJson(event.getMessageContent(), JsonElement.class).toString());
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
        Classes.registerClass(new ClassInfo<>(messagecontent.class, "messagecontent")
                .defaultExpression(new EventValueExpression<>(messagecontent.class))
                .user("messagecontent")
                .name("messagecontent")
                .parser(new Parser<messagecontent>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public messagecontent parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(messagecontent arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(messagecontent arg0) {
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
        Classes.registerClass(new ClassInfo<>(mods.class, "mods")
                .defaultExpression(new EventValueExpression<>(mods.class))
                .user("mods")
                .name("mods")
                .parser(new Parser<mods>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public mods parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(mods arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(mods arg0) {
                        return arg0.toString();
                    }

                }));
        Classes.registerClass(new ClassInfo<>(packages.class, "packages")
                .defaultExpression(new EventValueExpression<>(packages.class))
                .user("packages")
                .name("packages")
                .parser(new Parser<packages>() {

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public packages parse(String arg0, ParseContext arg1) {
                        return null;
                    }

                    @Override
                    public String toString(packages arg0, int arg1) {
                        return arg0.toString();
                    }

                    @Override
                    public String toVariableNameString(packages arg0) {
                        return arg0.toString();
                    }

                }));
        Classes.registerClass(new ClassInfo<>(Anchor.class, "anchor")
                .user("anchor")
                .name("anchor")
                .parser(new Parser<Anchor>() {

                    @Override
                    public String toString(Anchor o, int flags) {
                        return o.getX() + ", " + o.getY();
                    }

                    @Override
                    public String toVariableNameString(Anchor o) {
                        return ToStringBuilder.reflectionToString(o);
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                    @Nullable
                    @Override
                    public Anchor parse(String s, ParseContext context) {
                        return null;
                    }
                })
        );
        Classes.registerClass(new ClassInfo<>(JsonObject.class, "labymodskjsonobject")
                .user("labymodskjsonobject")
                .name("labymodskjsonobject")
                .parser(new Parser<JsonObject>() {

                    @Override
                    public String toString(JsonObject o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(JsonObject o) {
                        return ToStringBuilder.reflectionToString(o);
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }
                    @Nullable
                    @Override
                    public JsonObject parse(String s, ParseContext context) {
                        return null;
                    }
                })
        );
        if(Bukkit.getPluginManager().getPlugin("Vixio") == null) {
            Classes.registerClass(new ClassInfo<>(Color.class, "labymodskjavacolor")
                .user("labymodskjavacolor")
                .name("labymodskjavacolor")
                .parser(new Parser<Color>() {
                    @Override
                    public String toString(Color o, int flags) {
                        return "color from rgb " + o.getRed() + ", " + o.getGreen() + " and " + o.getBlue();
                    }

                    @Override
                    public String toVariableNameString(Color o) {
                        return null;
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return null;
                    }
                    @Nullable
                    @Override
                    public Color parse(String s, ParseContext context) {
                        return Util.getColorFromString(s);
                    }
                })
        );   
        }
    }
}
