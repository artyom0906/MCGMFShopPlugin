package com.gmf.mc.shop;

import com.gmf.mc.shop.commands.HelloCommand;
import com.gmf.mc.shop.service.CommandService;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

public class ShopPlugin extends JavaPlugin {

    private ConfigurableApplicationContext context;
    private CommandService commandService;
    private final static Logger logger = LoggerFactory.getLogger(HelloCommand.class);


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandService.execute(sender, command, label, args);
    }

    @Override
    @SneakyThrows
    public void onEnable() {
        saveDefaultConfig();
        ResourceLoader loader = new DefaultResourceLoader(getClassLoader());
        SpringApplication application = new SpringApplication(loader, Application.class);
        context = application.run();
        commandService = context.getBean(CommandService.class);
    }

    @Override
    public void onDisable() {
        context.close();
        context = null;
    }
}
