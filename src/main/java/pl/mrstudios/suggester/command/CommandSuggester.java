package pl.mrstudios.suggester.command;

import com.velocitypowered.api.command.CommandSource;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.annotation.Inject;
import pl.mrstudios.suggester.config.Configuration;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@Command(name = "suggester")
@Permission("mrstudios.command.suggester")
public class CommandSuggester {

    private final Configuration configuration;

    @Inject
    public CommandSuggester(
            @NotNull Configuration configuration
    ) {
        this.configuration = configuration;
    }

    @Execute
    public void execute(
            @Context CommandSource commandSource
    ) {
        this.configuration.load();
        commandSource.sendMessage(miniMessage().deserialize("<green>Configuration reloaded successfully."));
    }

}
