package com.gmf.mc.shop.service;

import com.gmf.mc.shop.controller.CommandMethodController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandMethodContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandMethodContainer.class);

    private final Map<String, CommandMethodController> controllerMap = new HashMap<>();

    public void addBotController(String path, CommandMethodController controller){
        if(controllerMap.containsKey(path)) LOGGER.error("command [/" + path + "] already add");
        controllerMap.put(path, controller);
        LOGGER.info("added command: /" +  path);
    }

    public CommandMethodController getBotApiMethodController(String path) {
        return controllerMap.get(path);
    }
}
