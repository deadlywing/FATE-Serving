package com.webank.ai.fate.serving.core.async;

import com.google.common.collect.Maps;
import com.webank.ai.fate.serving.core.annotation.Subscribe;
import com.webank.ai.fate.serving.core.bean.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AsyncSubscribeRegister implements ApplicationListener<ApplicationReadyEvent> {
    Logger logger = LoggerFactory.getLogger(AsyncSubscribeRegister.class);

    public static final Map<String, Set<Method>> SUBSCRIBE_METHOD_MAP = new HashMap<>();

    public static final Map<Method,Object>  METHOD_INSTANCE_MAP  = Maps.newHashMap();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationEvent) {
        String[] beans = SpringContextUtil.getBeanNamesForType(AbstractAsyncMessageProcessor.class);
        for (String beanName : beans) {
            AbstractAsyncMessageProcessor eventProcessor =  SpringContextUtil.getBean(beanName,AbstractAsyncMessageProcessor.class);
            Method[] methods = eventProcessor.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Subscribe.class)) {
                    Subscribe subscribe = method.getAnnotation(Subscribe.class);
                    if (subscribe != null) {
                        Set<Method> methodList = SUBSCRIBE_METHOD_MAP.get(subscribe.value());
                        if (methodList == null) {
                            methodList = new HashSet<>();
                        }
                        methodList.add(method);
                        METHOD_INSTANCE_MAP.put(method,eventProcessor);
                        SUBSCRIBE_METHOD_MAP.put(subscribe.value(), methodList);
                    }
                }
            }
        }

        logger.info("subscribe register info {}", SUBSCRIBE_METHOD_MAP.keySet());
    }
}