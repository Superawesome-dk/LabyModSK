package dk.superawesome.labymodsk.Utils;

import org.bukkit.ChatColor;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

public class Util {
    private static HashMap<String, Color> colors = new HashMap<>();
    static {
        try {
            for (Field color : Color.class.getDeclaredFields()) {
                color.setAccessible(true);
                if (color.getType() == Color.class) {
                    colors.put(color.getName().toLowerCase(Locale.ENGLISH).replace("_", " "), (Color) color.get(null));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static String color(String input){
        if(input != null) {
            return ChatColor.translateAlternateColorCodes('&', input);
        }
        return null;
    }
    public static Color getColorFromString(String str) {
        return str == null ? null : colors.get(str.toLowerCase(Locale.ENGLISH));

    }
}
