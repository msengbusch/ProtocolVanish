/*
 * Hides you completely from players on your servers by using packets!
 *     Copyright (C) 2019  Azortis
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.azortis.protocolvanish.command;

import com.azortis.azortislib.command.Command;
import com.azortis.azortislib.command.CommandInjector;
import com.azortis.azortislib.command.builders.CommandBuilder;
import com.azortis.azortislib.command.builders.SubCommandBuilder;
import com.azortis.azortislib.command.executors.ICommandExecutor;
import com.azortis.azortislib.command.executors.ITabCompleter;
import com.azortis.protocolvanish.ProtocolVanish;
import com.azortis.protocolvanish.VanishPlayer;
import com.azortis.protocolvanish.command.subcommands.*;
import com.azortis.protocolvanish.settings.CommandSettingsWrapper;
import com.azortis.protocolvanish.settings.MessageSettingsWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class VanishCommand implements ICommandExecutor, ITabCompleter {

    private ProtocolVanish plugin;

    public VanishCommand(ProtocolVanish plugin){
        this.plugin = plugin;
        CommandSettingsWrapper commandSettings = plugin.getSettingsManager().getCommandSettings();
        Command command = new CommandBuilder()
                .setName(commandSettings.getName())
                .setDescription(commandSettings.getDescription())
                .setUsage(commandSettings.getUsage())
                .addAliases(commandSettings.getAliases())
                .setPlugin(plugin)
                .setExecutor(this)
                .setTabCompleter(this)
                .addSubCommands(
                        new SubCommandBuilder()
                                .setName(commandSettings.getSubCommandName("toggleNightVision"))
                                .setExecutor(new ToggleNightVisionSub(plugin))
                                .addAliases(commandSettings.getSubCommandAliases("toggleNightVision")),
                        new SubCommandBuilder()
                                .setName(commandSettings.getSubCommandName("toggleDamage"))
                                .setExecutor(new ToggleDamageSub(plugin))
                                .addAliases(commandSettings.getSubCommandAliases("toggleDamage")),
                        new SubCommandBuilder()
                                .setName(commandSettings.getSubCommandName("toggleHunger"))
                                .setExecutor(new ToggleHungerSub(plugin))
                                .addAliases(commandSettings.getSubCommandAliases("toggleHunger")),
                        new SubCommandBuilder()
                                .setName(commandSettings.getSubCommandName("toggleCreatureTarget"))
                                .setExecutor(new ToggleCreatureTargetSub(plugin))
                                .addAliases(commandSettings.getSubCommandAliases("toggleCreatureTarget")),
                        new SubCommandBuilder()
                                .setName(commandSettings.getSubCommandName("toggleItemPickup"))
                                .setExecutor(new ToggleItemPickupSub(plugin))
                                .addAliases(commandSettings.getSubCommandAliases("toggleItemPickup"))
                ).build();
        CommandInjector.injectCommand(command);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("This command cannot be run from console!");
            return false;
        }else if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            MessageSettingsWrapper messageSettings = plugin.getSettingsManager().getMessageSettings();
            if(plugin.getPermissionManager().hasPermissionToVanish(player)){
                VanishPlayer vanishPlayer = plugin.getVanishPlayer(player);
                if(vanishPlayer.isVanished()){
                    plugin.getVisibilityManager().setVanished(player.getUniqueId(), false);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSettings.getMessage("onReappear")));
                }else {
                    plugin.getVisibilityManager().setVanished(player.getUniqueId(), true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSettings.getMessage("onVanish")));
                }
                return true;
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSettings.getMessage("noPermission")));
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender commandSender, String s, String[] strings, Location location) {
        return null;
    }
}
