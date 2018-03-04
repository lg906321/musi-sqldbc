package com.test.music.service;

import com.alibaba.fastjson.JSONObject;
import com.test.music.dao.UserDao;
import com.test.music.event.UserEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserService extends AbstractVerticle {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(UserEvent.USER_PAGE_LIST, this::getUserList);
        logger.info("UserService init");
    }

    private void getUserList(Message<JSONObject> msg) {
        JSONObject params = msg.body();
        logger.info("getUserList:" + params.toString());
        Future<List<JsonObject>> listFuture = userDao.queryUserList(params);
        listFuture.setHandler(ar -> {
            if(ar.succeeded()){
                logger.info(ar.result().toString());
                msg.reply(new JsonObject().put("data",ar.result()));
            } else {
                msg.reply(new JsonObject().put("err","err"));
            }
        });
    }
}