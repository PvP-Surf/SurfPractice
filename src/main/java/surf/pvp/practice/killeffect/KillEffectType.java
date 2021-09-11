package surf.pvp.practice.killeffect;

import lombok.Getter;
import surf.pvp.practice.killeffect.impl.ItemsKillEffectAdapter;
import surf.pvp.practice.killeffect.impl.LightningKillEffectAdapter;

@Getter
public enum KillEffectType {

    LIGHTNING("Lightning Kill Effect", "practice.kill.lightning", new LightningKillEffectAdapter()),
    ITEMS("Items Kill Effect", "practice.kill.items", new ItemsKillEffectAdapter());

    private final String name;
    private final String permission;
    private final KillEffectAdapter killEffectAdapter;

    KillEffectType(String name, String permission, KillEffectAdapter killEffectAdapter) {
        this.name = name;
        this.permission = permission;
        this.killEffectAdapter = killEffectAdapter;
    }

}
