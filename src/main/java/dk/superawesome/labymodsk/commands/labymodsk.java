package dk.superawesome.labymodsk.commands;

import dk.superawesome.labymodsk.Utils.Util;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.npc.SimpleNPCDataStore;
import net.citizensnpcs.api.util.YamlStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Random;
import java.util.UUID;

public class labymodsk implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(player.isOp() == false){
                player.sendMessage(Util.color( "&8[&2LabyMod&8] &cDu har ikke adgang til denne kommando."));
                return true;
            }
        }
        Player player = (Player) sender;
        if(args.length >= 1 && args[0].equalsIgnoreCase("npccreate")) {
            if(args.length == 2) {
                NPCRegistry registry = CitizensAPI.getNPCRegistry();
                UUID uuid = new UUID(new Random().nextLong(), 0);
                int npcId = SimpleNPCDataStore.create(new YamlStorage(new File(CitizensAPI.getDataFolder(), "saves.yml"))).createUniqueNPCId(registry);
                NPC newNpc = registry.createNPC(EntityType.PLAYER, uuid, npcId, args[1]);
                newNpc.spawn(player.getLocation());
            } else {
                player.sendMessage(Util.color("&2Du har glemt navnet på npcen."));
            }
            return true;
        }
        player.sendMessage(Util.color("&2/labymodsk npccreate"));
        return true;
    }
}
