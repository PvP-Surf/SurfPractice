package surf.pvp.practice.menu.view;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.profile.remains.MatchRemains;
import surf.pvp.practice.util.CC;
import surf.pvp.practice.util.menu.Menu;
import surf.pvp.practice.util.menu.buttons.Button;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ViewMenu extends Menu {

    private final Profile profile;

    public ViewMenu(Player player, String title, Profile profile) {
        super(player, title, 54);

        this.profile = profile;
    }

    /**
     * The method to get the buttons for the current inventory tick
     * <p>
     * a button to a slot.
     */

    @Override
    public Map<Integer, Button> getButtons() {
        final Map<Integer, Button> buttonMap = new HashMap<>();
        final MatchRemains matchRemains = profile.getMatchRemains();

        final ItemStack[] contents = matchRemains.getContents(), armor = matchRemains.getArmor();

        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null) {
                buttonMap.put(i, new Button(contents[i]));
            }
        }

        for (int i = 45; i < 49; i++) {
            if (armor[i] != null) {
                buttonMap.put(i, new Button(armor[i]));
            }
        }

        buttonMap.put(50, new Button(Material.COOKED_BEEF).setDisplayName(CC.translate("&bFood Level")).setLore(Collections.singletonList("&f" + matchRemains.getFood())));
        buttonMap.put(51, new Button(Material.EMERALD).setDisplayName(CC.translate("&b&lHealth")).setLore(Collections.singletonList("&f" + matchRemains.getHealth())));
        buttonMap.put(52, new Button(Material.PAPER).setDisplayName(CC.translate("&b&lEffects")).setLore(Collections.singletonList("&f" + matchRemains.getEffectList())));
        buttonMap.put(53, new Button(Material.POTION).setDisplayName(CC.translate("&b&lPotion Count")).setLore(Collections.singletonList("&f" + matchRemains.getPotionCount())));

        return buttonMap;
    }

}
