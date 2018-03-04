package com.test.music;

import com.test.music.init.DBUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("start...");
//        vertx.deployVerticle(DBUtil.class.getName(),ar -> {
//        log.info("start...ssssss");
//            System.out.println(111);
//            if (ar.succeeded()) {
//            System.out.println(222);
//
//                vertx.deployVerticle(AppVerticle.class.getName(), arrAR -> {
//                    if (arrAR.succeeded()) {
//                        startFuture.complete();
//                        log.warn("start succ");
//                    } else {
//                        Throwable cause = arrAR.cause();
//                        log.error(cause.getMessage());
//                        startFuture.fail(cause.getMessage());
//                    }
//                });
//            } else {
//                Throwable cause = ar.cause();
//                log.error(cause.getMessage());
//                startFuture.fail(cause);
//            }
//        });
        Future.<String>future(db -> vertx.deployVerticle(new DBUtil(), db))
                .compose(appInit -> Future.<String>future(app -> vertx.deployVerticle(new AppVerticle(), app)))
                .setHandler(res -> {
                    if (res.succeeded()) {
                        startFuture.complete();
                        log.info("succ");
                    } else {
                        Throwable cause = res.cause();
                        cause.printStackTrace();
                        startFuture.fail(cause.getMessage());
                        log.info("err" + cause.getMessage());
                    }
                });


//        init.compose(dbInit -> Future.<String>future(db -> vertx.deployVerticle(new DBUtil(), db)))
//                .compose(appInit -> Future.<String>future(app -> vertx.deployVerticle(AppVerticle.class.getName(), app)))
//                .setHandler(res -> {
//                    if (res.succeeded()) {
//                        startFuture.complete();
//                        log.info("succ");
//                    } else {
//                        Throwable cause = res.cause();
//                        cause.printStackTrace();
//                        startFuture.fail(cause.getMessage());
//                        log.info("err" + cause.getMessage());
//                    }
//                });
//        UserDao userDao = new UserDao();
//        ReadFileUtil.asyncReadJsonFile(vertx, DB_FILENAME_DEFAULT)
//                .compose(res -> Future.<String>future(db -> vertx.deployVerticle(new DBVerticle(res), db)))
//                .compose(res -> Future.<String>future(dao -> vertx.deployVerticle(userDao, dao)))
//                .compose(res -> Future.<String>future(service -> vertx.deployVerticle(new UserService(userDao), service)))
////                .compose(res -> Future.<String>future(user -> vertx.deployVerticle(new UserRouter(router), user)))
//                .setHandler(res -> {
//                    if (res.succeeded()) {
//                        log.info("listener " + port);
//                        startFuture.complete();
//                        log.info("succ");
//                    } else {
//                        Throwable cause = res.cause();
//                        cause.printStackTrace();
//                        startFuture.fail(cause.getMessage());
//                        log.info("err" + cause.getMessage());
//                    }
//                });

    }


}