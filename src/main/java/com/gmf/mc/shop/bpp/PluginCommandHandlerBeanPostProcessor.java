package com.gmf.mc.shop.bpp;

import com.gmf.mc.shop.annotation.Command;
import com.gmf.mc.shop.annotation.CommandController;
import com.gmf.mc.shop.controller.CommandMethodController;
import com.gmf.mc.shop.service.CommandMethodContainer;
import org.bukkit.command.CommandSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class PluginCommandHandlerBeanPostProcessor implements BeanPostProcessor, Ordered {
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginCommandHandlerBeanPostProcessor.class);

    private final CommandMethodContainer container;
    private final Map<String, Class<?>> botControllerMap = new HashMap<>();

    public PluginCommandHandlerBeanPostProcessor(CommandMethodContainer container) {
        this.container = container;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(CommandController.class))
            botControllerMap.put(beanName, beanClass);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(!botControllerMap.containsKey(beanName)) return bean;
        Class<?> original = botControllerMap.get(beanName);
        Arrays.stream(original.getMethods())
                .filter(method -> method.isAnnotationPresent(Command.class))
                .forEach((Method method) -> generateController(bean, method));
        return bean;
    }

    private void generateController(Object bean, Method method) {
        CommandController botController = bean.getClass().getAnnotation(CommandController.class);
        Command botRequestMapping = method.getAnnotation(Command.class);

        String path = (botController.value().length != 0 ? botController.value()[0] : "")
                + (botRequestMapping.value().length != 0 ? botRequestMapping.value()[0] : "");

        CommandMethodController controller = createControllerCommand(bean, method);
        container.addBotController(path, controller);
    }

    private CommandMethodController createControllerCommand(Object bean, Method method){
        return new CommandMethodController(bean, method) {
            @Override
            public boolean successUpdatePredicate(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
                return sender!=null && command != null && label != null;
            }
        };
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
