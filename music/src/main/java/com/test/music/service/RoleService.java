package com.test.music.service;

import com.alibaba.fastjson.JSONObject;
import com.test.music.common.ParamUtil;
import com.test.music.common.res.ResponseUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleService extends AbstractVerticle {


    public void getList(RoutingContext routingContext) {
        log.info("getList");
        JSONObject params = ParamUtil.defaultGet(routingContext);
        log.info("params:" + params.toString());
        ResponseUtil.err(routingContext, "roleList");
    }
}