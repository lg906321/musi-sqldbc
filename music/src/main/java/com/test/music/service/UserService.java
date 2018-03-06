package com.test.music.service;

import com.alibaba.fastjson.JSONObject;
import com.test.music.dao.UserDao;
import com.test.music.event.UserEvent;
import com.test.music.util.DeployUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserService extends AbstractVerticle {

    private UserDao userDao;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        userDao = new UserDao();
        DeployUtil.deploy(vertx, startFuture, userDao);
    }

    @Override
    public void start() throws Exception {
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer(UserEvent.USER_PAGE_LIST, this::getUserList);
        log.info("UserService init");
    }

    private void getUserList(Message<JSONObject> msg) {
        JSONObject params = msg.body();
        log.info("getUserList:" + params.toString());
        Future<List<JsonObject>> listFuture = userDao.queryUserList(params);
        listFuture.setHandler(ar -> {
            if(ar.succeeded()){
                log.info(ar.result().toString());
                msg.reply(new JsonObject().put("data",ar.result()));
            } else {
                msg.reply(new JsonObject().put("err","err"));
            }
        });
    }
}