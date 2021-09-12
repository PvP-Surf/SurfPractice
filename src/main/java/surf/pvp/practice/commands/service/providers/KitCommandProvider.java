package surf.pvp.practice.commands.service.providers;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.kit.Kit;
import surf.pvp.practice.util.CC;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class KitCommandProvider implements BladeProvider<Kit> {

    private final SurfPractice surfPractice;

    @Override
    public @Nullable Kit provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        final Kit kit = surfPractice.getKitHandler().getKit(s);

        if (s == null || kit == null)
            throw new BladeExitMessage(CC.translate("&cThe kit &e" + s + " &cwas not found!"));

        return kit;
    }

    @Override
    public @NotNull List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        return surfPractice.getKitHandler().getKits().stream().map(Kit::getName).collect(Collectors.toList());
    }

}
