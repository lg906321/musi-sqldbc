package com.test.music.router;

import com.alibaba.fastjson.JSONObject;
import com.test.music.AppVerticle;
import com.test.music.common.ParamUtil;
import com.test.music.common.res.ResponseUtil;
import com.test.music.event.UserEvent;
import com.test.music.service.RoleService;
import com.test.music.service.UserService;
import com.test.music.util.DeployUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import static com.test.music.common.ContentTye.JSON_UTF8;

@Slf4j
public class UserRouter extends AbstractVerticle {

    private UserService userService;
    private RoleService roleService;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        userService = new UserService();
        roleService = new RoleService();

        DeployUtil.deploy(vertx, startFuture, userService, roleService);
//        DeployUtil.deploy(this);
    }

    public UserRouter() {
        Router userRouter = Router.router(vertx);
        userRouter.get("/users").produces(JSON_UTF8).handler(this::getUserList);
        userRouter.get("/usersNew").produces(JSON_UTF8).handler(this::getUserListNew);
        userRouter.get("/usersRole").produces(JSON_UTF8).handler(this::getRoleList);
        AppVerticle.mountSubRouter("/user", userRouter, this);
    }

    private void getRoleList(RoutingContext routingContext) {
        roleService.getList(routingContext);
    }

    private void getUserList(RoutingContext rc) {
        log.info("getUserList");
        JSONObject params = ParamUtil.defaultGet(rc);
        log.info("params:" + params.toString());

        rc.vertx().eventBus().<JsonObject>send(UserEvent.USER_PAGE_LIST, params, ar -> {
            log.info("ar:" + ar.succeeded());
            if (ar.succeeded()) {
                ResponseUtil.ok(rc, ar.result().body());
            } else {
                ResponseUtil.err(rc, "sendErr");
            }
        });
    }

    private void getUserListNew(RoutingContext rc) {
        log.info("getUserList");
        JSONObject params = ParamUtil.defaultGet(rc);
        log.info("params:" + params.toString());
        ResponseUtil.err(rc, "sendErr");
    }
}
