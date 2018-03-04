package com.test.music;

import com.test.music.util.DateUtils;
import com.test.music.util.ReadFileUtil;
import com.test.music.util.SettleDateUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class TestVerticle extends AbstractVerticle {

//    static log log = LoggerFactory.getLogger(TestVerticle.class.getName());

    private JDBCClient jdbcClient = null;


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        String path = "db.json";
        JsonObject dbconfig = ReadFileUtil.readJsonFile(vertx, path);


        System.out.println(dbconfig);
        if (true) {
            startFuture.complete();
            return;
        }


        Future<JsonObject> fut1 = readConfig(path);
        Future<JsonObject> fut2 = Future.future();
        Future<Void> fut3 = Future.future();
        Future<SQLConnection> futConn = Future.future();
        fut1.compose(ar -> {
            fut2.complete(ar);
        }, fut2);
        fut2.compose(ar -> {
            jdbcClient = JDBCClient.createShared(vertx, ar);
            fut3.complete();
        }, fut3);
        fut3.compose(ar -> {
            jdbcClient.getConnection(futConn);
        }, futConn);

        fut3.setHandler(ar -> {
            if (ar.succeeded()) {
                log.info("jdbc init ...Succ");
                select(startFuture);
            } else {
                log.info("jdbc init ...Err");
                startFuture.fail(ar.cause());
            }
        });
    }

    private Future<JsonObject> readConfig(String path) {
        Future<JsonObject> future = Future.future();
        vertx.fileSystem().readFile(path, ar -> {
            if (ar.succeeded()) {
                future.complete(new JsonObject(ar.result()));
            } else {
                future.fail(ar.cause());
            }
        });
        return future;
    }

    void select(Future<Void> startFuture) {
        System.out.println(111);
        Future<SQLConnection> connFuture = getConn();
        String sql = "SELECT id,settle_accounts days,valid_time_from begin,valid_time_to end\n" +
                " FROM tearth_agreemen\n" +
                " WHERE settle_accounts IS NOT null";
        Future<Void> fvoid = Future.future();
        connFuture.compose(ar -> {
            ar.query(sql, rs -> {
                if (rs.succeeded()) {
                    List<JsonObject> rows = rs.result().getRows();
                    System.out.println(rows);
                    countDate(rows);
                } else {
                    log.error(rs.cause().toString());
                }
            });
        }, fvoid);
        fvoid.setHandler(ar -> {
            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });

    }

    Future<SQLConnection> getConn() {
        Future<SQLConnection> connFuture = Future.future();
        jdbcClient.getConnection(ar -> {
            if (ar.succeeded()) {
                System.out.println(122111);
                connFuture.complete(ar.result());
            } else {
                connFuture.fail(ar.cause());
            }
        });
        return connFuture;
    }

    void countDate(List<JsonObject> rows) {
        List<JsonObject> list = rows.stream()
                .map(e -> countDate(e)).collect(Collectors.toList());
    }

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    JsonObject countDate(JsonObject row) {
        try {

            String begin = row.getString("begin");
            String end = row.getString("end");
            Date beginDate = format.parse(begin);
            val days = row.getInteger("days");
            Date settleBeginDate = SettleDateUtil.countSettleDate(beginDate, days, beginDate);
            Date settleEndDate = SettleDateUtil.countSettleDate(beginDate, days, format.parse(end));
            String bstr = DateUtils.dateFormat(settleBeginDate);
            String estr = DateUtils.dateFormat(settleEndDate);
            row.put("settleBegin", bstr);
            row.put("settleEnd", estr);

            System.out.println("UPDATE tearth_agreemen SET settle_begin_date = " +
                    "'" + bstr + "',settle_end_date=" +
                    "'" + estr + "' " +
                    "WHERE id=" + row.getInteger("id") + ";");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }


}