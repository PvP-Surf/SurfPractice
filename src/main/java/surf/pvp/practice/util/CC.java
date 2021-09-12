package surf.pvp.practice.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CC {

    public static final String CHAT_BAR = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------------";

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> translate(List<String> strings) {
        return strings.stream().map(CC::translate).collect(Collectors.toList());
    }

    public static <T> List<List<T>> split(List<T> list) {
        List<T> first = new ArrayList<>();
        List<T> second = new ArrayList<>();

        int size = list.size();

        for (int i = 0; i < size / 2; i++)
            first.add(list.get(i));

        for (int i = size / 2; i < size; i++)
            second.add(list.get(i));

        List<List<T>> ts = new ArrayList<>();
        ts.add(first);
        ts.add(second);

        return ts;
    }

}
