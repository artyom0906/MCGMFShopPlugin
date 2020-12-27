package com.gmf.mc.shop.commands;

import com.gmf.mc.shop.utils.ICommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class HelloCommand implements ICommand {
    private final static Logger logger = LoggerFactory.getLogger(HelloCommand.class);

    @Override
    public void run(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("help");
    }

    @Override
    public String getCommandName() {
        return "shelp";
    }
}