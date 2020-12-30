package com.gmf.mc.shop.controller;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandMethodController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandMethodController.class);

    private final Object bean;
    private final Method method;
    private final Process processUpdate;

    public CommandMethodController(Object bean, Method method) {
        this.bean = bean;
        this.method = method;

        processUpdate = this::processSingle;
    }

    public boolean successUpdatePredicate(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }

    public boolean process(CommandSender sender, Command command, String label, String[] args) {
        if (!successUpdatePredicate(sender, command, label, args)) return false;

        try {
            return processUpdate.accept(sender, command, label, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("bad invoke method", e);
        }
        return false;
    }

    private boolean processSingle(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, IllegalAccessException {
        return (boolean) method.invoke(bean, sender, command, label, args);
    }

    private interface Process {
        boolean accept(CommandSender sender, Command command, String label, String[] args) throws InvocationTargetException, IllegalAccessException;
    }
}
