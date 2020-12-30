package com.gmf.mc.shop.service;

import com.gmf.mc.shop.controller.CommandMethodController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.springframework.stereotype.Service;


@Service
public class CommandService {

    private final CommandMethodContainer container;

    public CommandService(CommandMethodContainer container) {
        this.container = container;
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args){
        CommandMethodController controller = container.getBotApiMethodController(label);
        if (controller == null) controller = container.getBotApiMethodController("");
        return controller.process(sender, command, label, args);
    }
}
