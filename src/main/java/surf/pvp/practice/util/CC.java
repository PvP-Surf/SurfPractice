package surf.pvp.practice.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> list(List<String> list) {
        final List<String> translateList = new ArrayList<String>();
        list.forEach(s -> {
            translateList.add(translate(s));
        });
        return translateList;
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
