package surf.pvp.practice.commands;

import lombok.AllArgsConstructor;
import me.vaperion.blade.command.annotation.Command;
import me.vaperion.blade.command.annotation.Name;
import me.vaperion.blade.command.annotation.Sender;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.entity.Player;
import surf.pvp.practice.SurfPractice;
import surf.pvp.practice.party.Party;
import surf.pvp.practice.profile.Profile;
import surf.pvp.practice.util.CC;
import surf.pvp.practice.util.component.Component;

@AllArgsConstructor
public class PartyCommand {

    private final SurfPractice surfPractice;

    @Command(value = "party create", description = "Creates a party", quoted = false, async = true)
    public final void partyCreateCommand(@Sender Player player) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        if (profile.getParty() != null) {
            player.sendMessage(CC.translate("&cYou are already inside of a party!"));
            return;
        }

        new Party(player);
    }

    @Command(value = "party leave", description = "Leaves a party", quoted = false, async = true)
    public final void partyLeaveCommand(@Sender Player player) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());
        final Party party = profile.getParty();

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to do this command!"));
            return;
        }

        party.removeMember(player);

        party.broadcast("&fThe player &b" + player.getName() + "&f has left the party!");
        player.sendMessage(CC.translate("&fYou have left &b" + party.getLeader().getName() + "&f's party!"));
    }

    @Command(value = "party disband", description = "Disbands a party", quoted = false, async = true)
    public final void partyDisbandCommand(@Sender Player player) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());

        final Party party = profile.getParty();

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to execute this command!"));
            return;
        }

        if (!party.getLeader().equals(player)) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to execute this command!"));
            return;
        }

        party.broadcast("&fThis party has been &bdisbanded&f!");
        party.disband();
    }

    @Command(value = "party invite", description = "Invites a player to the party", quoted = false, async = true, extraUsageData = "<player>")
    public final void partyInviteCommand(@Sender Player player, @Name("player") Player target) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());
        final Profile targetProfile = surfPractice.getProfileHandler().getProfile(target.getUniqueId());

        final Party party = profile.getParty();

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to execute this command!"));
            return;
        }

        if (!party.getLeader().equals(player)) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to execute this command!"));
            return;
        }

        if (targetProfile.getParty() != null) {
            player.sendMessage(CC.translate("&cThe player you sent an invite to is already in a party!"));
            return;
        }

        targetProfile.getPartyInvites().add(profile.getParty());
        player.sendMessage(CC.translate("&fYou have invited &b" + target.getName() + " &fto the party!"));

        final Component component = Component.of("&fYou have been invited to &b" +
                player.getName() + "&f's party!")
                .add(Component.of(" &b[Click here to join]")
                        .setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept " + player.getName())));

        target.spigot().sendMessage(component.get());
    }

    @Command(value = "party accept", description = "Accepts a party invite", extraUsageData = "<player>", async = true, quoted = false)
    public final void partyAccept(@Sender Player player, @Name("player") Player target) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());
        final Profile targetProfile = surfPractice.getProfileHandler().getProfile(target.getUniqueId());

        final Party party = targetProfile.getParty();

        if (profile.getParty() != null) {
            player.sendMessage(CC.translate("&cYou are already inside of a party!"));
            return;
        }

        if (party == null || !profile.getPartyInvites().contains(party)) {
            player.sendMessage(CC.translate("&cThis party has not invited you!"));
            return;
        }

        profile.getPartyInvites().remove(party);
        party.addMember(player);
    }

    @Command(value = "party kick", extraUsageData = "<player>", description = "Kicks a player from the party", quoted = false, async = true)
    public final void partyKick(@Sender Player player, @Name("player") Player target) {
        final Profile profile = surfPractice.getProfileHandler().getProfile(player.getUniqueId());
        final Profile targetProfile = surfPractice.getProfileHandler().getProfile(target.getUniqueId());

        final Party party = profile.getParty();

        if (party == null) {
            player.sendMessage(CC.translate("&cYou must be in a party to execute this command!"));
            return;
        }

        if (!party.getLeader().equals(player)) {
            player.sendMessage(CC.translate("&cYou must be the leader of the party to execute this command!"));
            return;
        }

        if (targetProfile.getParty() == null || !targetProfile.getParty().getLeader().equals(player)) {
            player.sendMessage(CC.translate("&cThis player is not inside your party!"));
            return;
        }

        party.removeMember(target);
        party.broadcast("&fThe player &b" + target.getName() + " &fhas been kicked from the party!");
        target.sendMessage(CC.translate("&fYou have been kicked from &b" + player.getName() + "&f's party!"));
    }


}
