package surf.pvp.practice.commands.service.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import surf.pvp.practice.util.CC;

import java.util.ArrayList;
import java.util.List;

public class MaterialCommandProvider implements BladeProvider<Material> {

    @Override
    public @Nullable Material provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        final Material material = Material.matchMaterial(s.toUpperCase());

        if (s == null || material == null)
            throw new BladeExitMessage(CC.translate("&cA material with the name &e" + s + "&c was not found!"));

        return material;
    }

    @Override
    public @NotNull List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        final List<String> strings = new ArrayList<>();

        for (Material material : Material.values()) {
            strings.add(material.name().toLowerCase());
        }

        return strings;
    }

}
