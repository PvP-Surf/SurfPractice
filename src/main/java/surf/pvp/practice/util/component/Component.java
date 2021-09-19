package surf.pvp.practice.util.component;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import surf.pvp.practice.util.CC;
import surf.pvp.practice.util.StringUtils;

import java.util.List;

public class Component {

    private final TextComponent textComponent;

    public Component(String s) {
        this.textComponent = new TextComponent(CC.translate(s));
    }

    public Component(List<String> s) {
        this.textComponent = new TextComponent(StringUtils.transform(CC.translate(s)));
    }

    public static Component of(String s) {
        return new Component(s);
    }

    public static Component of(List<String> s) {
        return new Component(s);
    }

    public final Component add(Component component) {
        this.textComponent.addExtra(component.get());
        return this;
    }

    public final Component setHoverText(ShowText showText) {
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(showText.toString()).create()));
        return this;
    }

    public final Component setClickEvent(ClickEvent clickEvent) {
        this.textComponent.setClickEvent(clickEvent);
        return this;
    }

    public final TextComponent get() {
        return textComponent;
    }

}
