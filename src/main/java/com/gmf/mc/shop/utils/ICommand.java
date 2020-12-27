package com.gmf.mc.shop.utils;

import com.gmf.mc.shop.service.CommandService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.springframework.beans.factory.annotation.Autowired;

public interface ICommand {
    public void run(CommandSender sender, Command command, String label, String[] args);
    public String getCommandName();

    @Autowired
    default void registerMyself(CommandService commandService){
        commandService.register(getCommandName(), this);
    }
}
