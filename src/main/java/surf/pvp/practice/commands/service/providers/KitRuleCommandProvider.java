package surf.pvp.practice.commands.service.providers;

import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import surf.pvp.practice.kit.KitRule;
import surf.pvp.practice.util.CC;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KitRuleCommandProvider implements BladeProvider<KitRule> {

    @Override
    public @Nullable KitRule provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        final KitRule kitRule = KitRule.valueOf(s.toUpperCase());

        if (s == null || kitRule == null)
            throw new BladeExitMessage(CC.translate("&cThe kit rule &e" + s + " &cdoes not exist!"));

        return kitRule;
    }

    @Override
    public @NotNull List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        return Arrays.stream(KitRule.values()).map(kitRule -> kitRule.name().toLowerCase()).collect(Collectors.toList());
    }
}
