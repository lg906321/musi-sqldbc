package com.test.music;

import com.alibaba.fastjson.JSONObject;
import com.test.music.common.ContentTye;
import com.test.music.router.UserRouter;
import com.test.music.util.ReadFileUtil;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.test.music.Constant.PATH_ALL;
import static com.test.music.Constant.PATH_ERR;

@Slf4j
public class AppVerticle extends AbstractVerticle {

    private static final int PORT_DEFAULT = 8080;
    private static final String PORT_KEY = "port";
    private static Router rootRouter;
    private static Vertx rootVertx;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        rootVertx = vertx;
        rootRouter = Router.router(vertx);

        // 请求体处理
        rootRouter.route(PATH_ALL).handler(BodyHandler.create());

        // 全局已UTF-8的编码方式响应
        rootRouter.route(PATH_ALL).handler(this::serCharsetEncoding);

        // 全局的错误处理
        rootRouter.route(PATH_ALL).failureHandler(this::failHandler);

        rootRouter.route(PATH_ERR).handler(rt -> {
            log.info("err...");
            int i = 1 / 0;
            rt.response().end("Hello World!!" + LocalDateTime.now());
        });

//        String con
        initAPI().setHandler(ar -> {
            val succeeded = ar.succeeded();
            if (succeeded) {
                JsonObject config = config();
                log.info(config.toString());
                Integer port = config.getInteger(PORT_KEY, PORT_DEFAULT);

                vertx.createHttpServer().requestHandler(rootRouter::accept).listen(port);

                log.warn("app listener post：" + port);

                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });


    }

    // 初始化所有的API
    private Future<String> initAPI() {
        return Future.<String>future(ar -> {
            vertx.deployVerticle(new UserRouter(),ar);
        });
    }

    private void serCharsetEncoding(RoutingContext routingContext) {
        routingContext.response().putHeader(ContentTye.KEY, ContentTye.TEXT_UTF8);
        routingContext.next();
    }

    void failHandler(RoutingContext rt) {
        log.info("failHandler...");
        MultiMap headers = rt.request().headers();
        log.info(JSONObject.toJSONString(headers));
        log.info("ContentTye-Type:" + headers.get(ContentTye.KEY));
        rt.response().putHeader(ContentTye.KEY, ContentTye.TEXT_UTF8);
        String str = "Global Err：" + LocalDateTime.now();
        rt.response().end(str);
    }

    /**
     * 挂载子路由
     * @param path
     * @param subRouter
     */
    public static void mountSubRouter(String path, Router subRouter,AbstractVerticle verticle) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(subRouter);
        Objects.requireNonNull(verticle);
        log.warn("挂载子路由：["+ path +"]\t" + verticle.toString());
        rootRouter.mountSubRouter(path, subRouter);
    }
}