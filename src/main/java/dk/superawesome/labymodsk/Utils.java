package dk.superawesome.labymodsk;

import org.bukkit.ChatColor;

public class Utils {
    public static String color(String input){
        if(input != null) {
            return ChatColor.translateAlternateColorCodes('&', input);
        }
        return null;
    }
}
