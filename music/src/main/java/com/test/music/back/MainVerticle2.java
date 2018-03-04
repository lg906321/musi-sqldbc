package com.test.music.back;

import com.alibaba.fastjson.JSONObject;
import com.test.music.common.ContentTye;
import com.test.music.dao.UserDao;
import com.test.music.db.DBVerticle;
import com.test.music.router.UserRouter;
import com.test.music.service.UserService;
import com.test.music.util.ReadFileUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.test.music.Constant.PATH_ALL;
import static com.test.music.Constant.PATH_ERR;

public class MainVerticle2 extends AbstractVerticle {

//    static final String CLASS_NAME = MainVerticle2.class.getName();
//    static Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);
//    private static final int PORT_DEFAULT = 8080;
//    private static final String PORT_KEY = "port";
//    private static final String DB_FILENAME_DEFAULT = "db.json";
//
//    @Override
//    public void start(Future<Void> startFuture) throws Exception {
//        Router router = Router.router(vertx);
//        router.route(PATH_ALL).handler(BodyHandler.create());
//        router.route(PATH_ALL).handler(this::serCharsetEncoding);
//
//        router.route(PATH_ERR).handler(rt -> {
//            LOGGER.info("err...");
//            int i = 1 / 0;
//            rt.response().end("Hello World!!" + LocalDateTime.now());
//        });
//
//        router.route(PATH_ALL).failureHandler(this::failHandler);
//        JsonObject config = config();
//        LOGGER.info(config.toString());
//        Integer port = config.getInteger(PORT_KEY, PORT_DEFAULT);
//
//        vertx.createHttpServer().requestHandler(router::accept).listen(port);
//        UserDao userDao = new UserDao();
//        ReadFileUtil.asyncReadJsonFile(vertx, DB_FILENAME_DEFAULT)
//                .compose(res -> Future.<String>future(db -> vertx.deployVerticle(new DBVerticle(res), db)))
//                .compose(res -> Future.<String>future(dao -> vertx.deployVerticle(userDao, dao)))
//                .compose(res -> Future.<String>future(service -> vertx.deployVerticle(new UserService(userDao), service)))
//                .compose(res -> Future.<String>future(user -> vertx.deployVerticle(new UserRouter(router), user)))
//                .setHandler(res -> {
//                    if (res.succeeded()) {
//                        LOGGER.info("listener " + port);
//                        startFuture.complete();
//                        LOGGER.info("succ");
//                    } else {
//                        Throwable cause = res.cause();
//                        cause.printStackTrace();
//                        startFuture.fail(cause.getMessage());
//                        LOGGER.info("err" + cause.getMessage());
//                    }
//                });
//
//    }
//
//    private void serCharsetEncoding(RoutingContext routingContext) {
//        routingContext.response().putHeader(ContentTye.KEY, ContentTye.TEXT_UTF8);
//        routingContext.next();
//    }
//
//    void failHandler(RoutingContext rt) {
//        LOGGER.info("failHandler...");
//        MultiMap headers = rt.request().headers();
//        LOGGER.info(JSONObject.toJSONString(headers));
//        LOGGER.info("ContentTye-Type:" + headers.get(ContentTye.KEY));
//        rt.response().putHeader(ContentTye.KEY, ContentTye.TEXT_UTF8);
//        String str = "Global：" + LocalDateTime.now();
//        rt.response().end(str);
//    }

}