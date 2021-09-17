package surf.pvp.practice.util.component;

import surf.pvp.practice.util.StringUtils;

import java.util.List;

public class ShowText {

    private final String string;

    public ShowText(String s) {
        this.string = s;
    }

    public ShowText(List<String> s) {
        this.string = StringUtils.transform(s);
    }

    @Override
    public String toString() {
        return string;
    }

}
