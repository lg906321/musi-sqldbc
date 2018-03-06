package com.test.music.init;

import com.test.music.util.ReadFileUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.Objects;

@Slf4j
public class DBUtil extends AbstractVerticle {
    private static JDBCClient CLIENT = null;
    private final static String DEFAULT_DB_CONFIG = "db.json";
    private final static String DEFAULT_DB_KEY = "dbconfig";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        log.info("dbinit...");
        JsonObject config = config();
        boolean init = init(config.getString(DEFAULT_DB_KEY));
        if (init) {
            log.warn("init db client succ");
            startFuture.complete();
        } else {
            val err = "init db client err";
            log.error(err);
            startFuture.fail(err);
        }
    }

    public boolean init(String path) {
        if (path == null) {
            log.warn("数据库配置文件名未配置,使用默认文件" + DEFAULT_DB_CONFIG);
            path = DEFAULT_DB_CONFIG;
        }
        val dbConfig = ReadFileUtil.readJsonFile(vertx, path);
        if (dbConfig.isEmpty()) {
            log.error("数据库配置文件不存在或者为空[" + path + "]");
            return false;
        }
        CLIENT = JDBCClient.createShared(vertx, dbConfig);
        return true;
    }

    public Future<List<? extends Object>> queryList(String sql, JsonArray param) {
        Objects.requireNonNull(sql);
        boolean noParam = param == null || param.isEmpty();

        Future<ResultSet> resultSetFuture = Future.future();
        Future<List<? extends Object>> retListFuture = Future.future();

        CLIENT.getConnection(connAR -> {
            val succeeded = connAR.succeeded();
            if (succeeded) {
                SQLConnection conn = connAR.result();
                if (noParam) {
                    conn.query(sql, resultSetFuture);
                } else {
                    conn.queryWithParams(sql, param, resultSetFuture);
                }
            }
        });

        resultSetFuture.setHandler(ar -> {
            val succeeded = ar.succeeded();
            if(succeeded){
                ResultSet result = ar.result();
                retListFuture.complete(result.getResults());
            } else {
                retListFuture.fail(ar.cause());
            }
        });
        return null;
    }

    private Future<ResultSet> getList() {
        return null;
    }


    public Future<List<? extends Object>> queryList(String sql) {
        Objects.requireNonNull(sql);
        return null;
    }

}