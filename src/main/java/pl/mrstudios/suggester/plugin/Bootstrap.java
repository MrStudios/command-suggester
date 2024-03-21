package pl.mrstudios.suggester.plugin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.velocity.LiteVelocityFactory;
import org.jetbrains.annotations.NotNull;
import pl.mrstudios.commons.inject.Injector;
import pl.mrstudios.commons.reflection.Reflections;
import pl.mrstudios.suggester.config.Configuration;
import pl.mrstudios.suggester.config.ConfigurationFactory;
import pl.mrstudios.suggester.listener.GeneralCommandListener;

import java.nio.file.Path;
import java.util.Objects;

import static com.velocitypowered.api.event.PostOrder.NORMAL;
import static dev.rollczi.litecommands.annotations.LiteCommandsAnnotations.of;
import static dev.rollczi.litecommands.message.LiteMessages.MISSING_PERMISSIONS;
import static dev.rollczi.litecommands.schematic.SchematicFormat.angleBrackets;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@Plugin(
        id = "command-suggester",
        name = "Command Suggester",
        version = "1.0.0",
        description = "A simple plugin to hide selected commands from players.",
        authors = { "MrStudios Industries" },
        dependencies = {
                @Dependency(id = "vpacketevents")
        }
)
public class Bootstrap {

    private final ProxyServer proxyServer;

    private final Configuration configuration;
    private final ConfigurationFactory configurationFactory;

    private final Injector injector;

    @Inject
    public Bootstrap(
            @NotNull ProxyServer proxyServer,
            @DataDirectory Path dataDirectory
    ) {

        this.proxyServer = proxyServer;

        this.configurationFactory = new ConfigurationFactory(dataDirectory);
        this.configuration = this.configurationFactory.produce(Configuration.class, "config.yml");

        this.injector = new Injector()

                .register(ProxyServer.class, this.proxyServer)

                .register(Configuration.class, this.configuration)
                .register(ConfigurationFactory.class, this.configurationFactory);

    }

    @Subscribe(order = NORMAL)
    public void onProxyInitialization(
            @NotNull ProxyInitializeEvent event
    ) {

        this.proxyServer.getEventManager().register(this, this.injector.inject(GeneralCommandListener.class));

        LiteVelocityFactory.builder(this.proxyServer)

                /* Messages */
                .message(MISSING_PERMISSIONS, miniMessage().deserialize(this.configuration.canNotUseThatCommandMessage))

                /* Commands */
                .commands(of(
                        new Reflections<>("pl.mrstudios.suggester")
                                .getClassesAnnotatedWith(Command.class)
                                .stream().filter(Objects::nonNull)
                                .map(this.injector::inject).toArray(Object[]::new)
                ))

                /* Schematic */
                .schematicGenerator(angleBrackets())

                /* Build */
                .build();

    }


}
