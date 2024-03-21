package pl.mrstudios.suggester.listener;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.command.PlayerAvailableCommandsEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.proxy.protocol.packet.chat.session.SessionPlayerCommandPacket;
import io.github._4drian3d.vpacketevents.api.event.PacketReceiveEvent;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.suggester.config.Configuration;

import java.util.Collection;
import java.util.List;

import static com.velocitypowered.api.event.PostOrder.FIRST;
import static com.velocitypowered.api.event.PostOrder.LAST;
import static com.velocitypowered.api.event.command.CommandExecuteEvent.CommandResult.denied;
import static com.velocitypowered.api.network.ProtocolVersion.MINECRAFT_1_19;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class GeneralCommandListener {

    private final Configuration configuration;

    @Inject
    public GeneralCommandListener(
            @NotNull Configuration configuration
    ) {
        this.configuration = configuration;
    }

    @Subscribe(order = FIRST)
    @SuppressWarnings("UnstableApiUsage")
    public void onPlayerAvailableCommands(
            @NotNull PlayerAvailableCommandsEvent event
    ) {

        if (event.getPlayer().hasPermission("*") || event.getPlayer().hasPermission("mrstudios.suggester.*"))
            return;

        Collection<String> commands = this.commandsOf(event.getPlayer());
        if (commands.isEmpty())
            return;

        event.getRootNode()
                .getChildren()
                .removeIf((children) -> !commands.contains(children.getName()));

    }

    @Subscribe(order = FIRST)
    public void onPlayerCommandExecution(
            @NotNull CommandExecuteEvent event
    ) {

        if (!(event.getCommandSource() instanceof Player player))
            return;

        if (player.getProtocolVersion().compareTo(MINECRAFT_1_19) >= 0)
            return;

        if (player.hasPermission("*") || player.hasPermission("mrstudios.suggester.*"))
            return;

        this.commandsOf(player)
                .stream()
                .map(String::toLowerCase)
                .filter((command) -> event.getCommand().split(" ")[0].equalsIgnoreCase(command))
                .findFirst()
                .ifPresentOrElse(
                        (command) -> {},
                        () -> {
                            player.sendMessage(miniMessage().deserialize(this.configuration.canNotUseThatCommandMessage));
                            event.setResult(denied());
                        }
                );

    }

    @Subscribe(order = LAST)
    public void onPacketReceive(
            @NotNull PacketReceiveEvent event
    ) {

        if (!(event.getPacket() instanceof SessionPlayerCommandPacket packet))
            return;

        if (event.getPlayer().getProtocolVersion().compareTo(MINECRAFT_1_19) < 0)
            return;

        if (event.getPlayer().hasPermission("*") || event.getPlayer().hasPermission("mrstudios.suggester.*"))
            return;

        this.commandsOf(event.getPlayer())
                .stream().map(String::toLowerCase)
                .filter((command) -> packet.getCommand().split(" ")[0].equalsIgnoreCase(command))
                .findFirst()
                .ifPresentOrElse(
                        (command) -> {},
                        () -> {
                            event.getPlayer().sendMessage(miniMessage().deserialize(this.configuration.canNotUseThatCommandMessage));
                            event.setResult(ResultedEvent.GenericResult.denied());
                        }
                );

    }

    public @NotNull Collection<String> commandsOf(
            @NotNull CommandSource commandSource
    ) {
        return this.configuration.commandSuggestingMap.keySet()
                .stream().filter(commandSource::hasPermission)
                .map(this.configuration.commandSuggestingMap::get)
                .flatMap(List::stream).toList();
    }

}
