package com.pms.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "hello")
public class Tut1Receiver {

    @RabbitHandler
    public void receive(String in) {
        Integer no  = Integer.parseInt(in);
        CacheManager manager = CacheManager.getInstance();
        Cache cache = manager.getCache("employees");
        cache.remove(no);
        System.out.println(" [x] Received '" + in + "'");
    }
}
