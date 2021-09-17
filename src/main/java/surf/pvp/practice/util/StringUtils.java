package surf.pvp.practice.util;

import surf.pvp.practice.util.component.Component;

import java.util.List;

public class StringUtils {

    public static String transform(List<String> strings) {
        StringBuilder builder = new StringBuilder();
        strings.forEach(s -> builder.append(s).append("\n"));
        return builder.toString();
    }

}
