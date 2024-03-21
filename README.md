### What is that?
Command Suggester is an open source plugin for Velocity that allows you to hide commands from players basing on permissions.

### Information
This plugin works on ``1.13+`` servers with ``Velocity`` proxy software, ``Java 17`` and ``vPacketEvents (v1.1.0+)`` plugin.

### Support
If you need support contact us on our [Discord](https://discord.gg/C8dF6zkYff) server or visit our [Documentation](https://www.mrstudios.pl/documentation) page. If you want to contribute this repository please read [CONTRIBUTING](CONTRIBUTING.md) file.

### Configuration

Plugin configuration files using [YAML](https://yaml.org/) format. Configuration files is described bellow.

<details>
    <summary>File: <code>config.yml</code></summary>

```yaml
#  
# --------------------------------------------------------------------------
#                                  INFORMATION
# ---------------------------------------------------------------------------
#  
#  This is configuration file for Command Suggester plugin, if you found 
#  any issue contact with us through Discord or create issue on GitHub. If 
#  you need help with configuration visit https://mrstudios.pl/documentation. 
#  

# ---------------------------------------------------------------------------
#                                   GENERAL
# ---------------------------------------------------------------------------

# A map that contains information about what commands should be available for
# a given permission.
command-suggesting-map:
  mrstudios.suggester.default:
    - msg
    - friend
    - party
  mrstudios.suggester.admin:
    - ban
    - mute
    - kick

# ---------------------------------------------------------------------------
#                                  MESSAGES
# ---------------------------------------------------------------------------
can-not-use-that-command-message: '<red>You can't use that command because you dont have permissions.'
```

</details>

### Sponsoring
If you want to sponsor this project you can do it by clicking ``Sponsor`` button. You can also support us by clicking on the star button on the top of this page.

### Used Libraries
Libraries that is used in this project, most of them are open source libraries.
- [LiteCommands](https://github.com/Rollczi/LiteCommands) by Rollczi
- [Java Annotations](https://github.com/JetBrains/java-annotations) by JetBrains
- [okaeri-configs](https://github.com/OkaeriPoland/okaeri-configs) by Okaeri
- [java-commons](https://github.com/MrStudios/java-commons) by MrStudios Industries