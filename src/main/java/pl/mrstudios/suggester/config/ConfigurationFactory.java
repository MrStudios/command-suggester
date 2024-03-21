package pl.mrstudios.suggester.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

import static eu.okaeri.configs.ConfigManager.create;

public record ConfigurationFactory(
        @NotNull Path directory
) {

    public <CONFIG extends OkaeriConfig> CONFIG produce(
            @NotNull Class<CONFIG> clazz,
            @NotNull String file
    ) {
        return produce(clazz, new File(this.directory.toFile(), file));
    }

    public <CONFIG extends OkaeriConfig> CONFIG produce(
            @NotNull Class<CONFIG> clazz,
            @NotNull File file
    ) {
        return create(clazz, (initializer) ->
                initializer.withConfigurer(new YamlSnakeYamlConfigurer())
                        .withBindFile(file)
                        .saveDefaults()
                        .load(true)
        );
    }

}