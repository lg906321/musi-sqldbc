package com.test.music.util;

import com.test.music.router.UserRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class DeployUtil {

    public static Future<String> deploy(Vertx vertx, AbstractVerticle... verticles) {
        Future<String> retFuture = Future.future();
        Objects.requireNonNull(vertx);
        Objects.requireNonNull(verticles);
        if (verticles.length == 0) {
            val err = "需要部署的 verticle 的长度为0";
//            future.fail(err);
            retFuture.fail(err);
        } else {
            List<Future> list = new ArrayList<>(verticles.length);
//            for (AbstractVerticle verticle : verticles) {
//                Future<String> result = Future.<String>future(ar -> vertx.deployVerticle(verticle, ar));
//                list.add(result);
//            }

            list = Stream.of(verticles)
                    .map(v -> Future.<String>future(ar -> vertx.deployVerticle(v, ar)))
                    .collect(Collectors.toList());

            CompositeFuture.all(list).setHandler(ar -> {
//                val complete = future.isComplete();
//                if (complete) {
//                    val err = "一个Future多次初始化...";
//                    log.error(err);
//                    retFuture.fail(err);
//                    throw new RuntimeException(err);
//                }

                if (ar.succeeded()) {
//                    future.complete();
                    retFuture.complete();
                } else {
                    Throwable cause = ar.cause();
                    log.error(cause.getMessage());
//                    future.fail(cause);
                    retFuture.fail(cause);
                }
            });
        }
        return retFuture;
    }

    public static void deploy(AbstractVerticle verticle) {
        val name = verticle.getClass().getName();

    }
}