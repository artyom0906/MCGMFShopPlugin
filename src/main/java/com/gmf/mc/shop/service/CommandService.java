package com.gmf.mc.shop.service;

import com.gmf.mc.shop.utils.ICommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandService {

    private Map<String, ICommand> commands = new HashMap<>();

    //public CommandService(List<ICommand> commands) {
    //    commands.forEach(c->register(c.getCommandName(), c));
    //}

    public void register(String command, ICommand executable){
        commands.put(command, executable);
    }

    public void execute(CommandSender sender, Command command, String label, String[] args){
        commands.get(label).run(sender, command, label, args);
    }
}
