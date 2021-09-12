package surf.pvp.practice.commands.service.help;

import me.vaperion.blade.command.container.BladeCommand;
import me.vaperion.blade.command.context.BladeContext;
import me.vaperion.blade.command.help.HelpGenerator;
import org.apache.commons.lang.WordUtils;
import org.jetbrains.annotations.NotNull;
import surf.pvp.practice.util.CC;

import java.util.ArrayList;
import java.util.List;

public class HelpCommandService implements HelpGenerator {

    @Override
    public @NotNull List<String> generate(@NotNull BladeContext bladeContext, @NotNull List<BladeCommand> list) {
        final List<String> strings = new ArrayList<>();
        final String commandName = bladeContext.alias();

        strings.add(CC.CHAT_BAR);
        strings.add("&b&l" + WordUtils.capitalizeFully(commandName) + "&f's Commands");
        strings.add(CC.CHAT_BAR);

        for (BladeCommand bladeCommand : list) {
            strings.add("&f* &b" + bladeCommand.getAliases()[0] + " " + bladeCommand.getExtraUsageData() + "&f - " + bladeCommand.getDescription());
        }

        return CC.translate(strings);
    }

}
