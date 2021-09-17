package surf.pvp.practice.commands.service.providers;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.argument.BladeProvider;
import me.vaperion.blade.command.container.BladeParameter;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.exception.BladeExitMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.arena.Arena;
import surf.pvp.practice.util.CC;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ArenaCommandProvider implements BladeProvider<Arena> {

    private final SurfPractice surfPractice;

    @Override
    public @Nullable Arena provide(@NotNull BladeContext bladeContext, @NotNull BladeParameter bladeParameter, @Nullable String s) throws BladeExitMessage {
        final Arena arena = surfPractice.getArenaHandler().getArena(s);

        if (s == null || arena == null)
            throw new BladeExitMessage(CC.translate("&cThe arena &e" + s + " &cwas not found!"));

        return arena;
    }

    @Override
    public @NotNull List<String> suggest(@NotNull BladeContext context, @NotNull String input) throws BladeExitMessage {
        return surfPractice.getArenaHandler().getArenas().stream().map(Arena::getName).collect(Collectors.toList());
    }

}
