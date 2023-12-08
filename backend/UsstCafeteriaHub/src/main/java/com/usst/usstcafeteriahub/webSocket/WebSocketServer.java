package com.usst.usstcafeteriahub.webSocket;


import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
//@ServerEndpoint(value = "/imserver/{username}/{avatar}")
@Component
public class WebSocketServer implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("WebSocketServer afterPropertiesSet");
    }
}
