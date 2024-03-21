package pl.mrstudios.suggester.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.Names;

import java.util.List;
import java.util.Map;

import static eu.okaeri.configs.annotation.NameModifier.TO_LOWER_CASE;
import static eu.okaeri.configs.annotation.NameStrategy.HYPHEN_CASE;

@Header({
        " ",
        "--------------------------------------------------------------------------",
        "                                 INFORMATION",
        "--------------------------------------------------------------------------",
        " ",
        " This is configuration file for Command Suggester plugin, if you found ",
        " any issue contact with us through Discord or create issue on GitHub. If ",
        " you need help with configuration visit https://mrstudios.pl/documentation. ",
        " "
}) @Names(strategy = HYPHEN_CASE, modifier = TO_LOWER_CASE)
public class Configuration extends OkaeriConfig {

    @Comment({
            "",
            "--------------------------------------------------------------------------",
            "                                  GENERAL",
            "--------------------------------------------------------------------------",
            ""
    })

    @Comment({
            "A map that contains information about what commands should be available for a",
            "a given permission."
    })
    public Map<String, List<String>> commandSuggestingMap = Map.of(
            "mrstudios.suggester.default", List.of(
                    "msg",
                    "friend",
                    "party"
            ),
            "mrstudios.suggester.admin", List.of(
                    "ban",
                    "kick",
                    "mute"
            )
    );


    @Comment({
            "",
            "--------------------------------------------------------------------------",
            "                                 MESSAGES",
            "--------------------------------------------------------------------------",
            ""
    })
    public String canNotUseThatCommandMessage = "<red>You can't use that command because you dont have permissions.";

}
