package com.gmf.mc.shop.commands;

import com.gmf.mc.shop.annotation.CommandController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@CommandController
public class HelloCommand{
    private final static Logger logger = LoggerFactory.getLogger(HelloCommand.class);

    @com.gmf.mc.shop.annotation.Command("shelp")
    public boolean run(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("help");
        return true;
    }
}