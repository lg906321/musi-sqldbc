package com.test.music.dao;

import com.alibaba.fastjson.JSONObject;
import com.test.music.db.DBVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class UserDao extends AbstractVerticle {

    JDBCClient jdbcClient = null;

    private String queryUserList = "SELECT id,name,img,sex,creatTime FROM user WHERE name LIKE ? AND id=? LIMIT ?,?";

    @Override
    public void start() throws Exception {
//        jdbcClient = DBVerticle.getClient();
        log.info("UserDao init...");
    }

    public Future<List<JsonObject>> queryUserList(JSONObject params) {
        JsonArray array = new JsonArray();
        String sql_limit = "LIMIT ?,?";

        String baseSql = "SELECT id,name,img,sex,creatTime FROM user ";
        StringBuilder whereSB = new StringBuilder("WHERE 1=1 ");
        String name = params.getString("name");
        if (StringUtils.isNoneBlank(name)) {
            whereSB.append("AND name LIKE ?");
            array.add(name);
        }

        eqInteger(whereSB, array, params, "id");
        like(whereSB, array, params, "name");
//        limit();
        final String sql = baseSql + whereSB.toString();

        return Future.<List<JsonObject>>future(ar ->
                jdbcClient.getConnection(getConnAR -> {
                    if (getConnAR.succeeded()) {
                        SQLConnection conn = getConnAR.result();
                        conn.queryWithParams(sql, array, queryAr -> {
                            if (queryAr.succeeded()) {
                                List<JsonObject> rows = queryAr.result().getRows();
                                ar.complete(rows);
                            } else {
                                log.info("查询失败:" + queryAr.cause().getMessage());
                                ar.fail("查询失败:" + queryAr.cause().getMessage());
                            }
                        });
                    } else {
                        log.info("获取数据库链接失败:" + ar.cause().getMessage());
                        ar.fail("获取数据库链接失败:" + ar.cause().getMessage());
                    }
                }));

    }


    void eqInteger(StringBuilder where, JsonArray array, JSONObject inParam, String key) {
        eqInteger(where, array, inParam, key, key);
    }

    void eqInteger(StringBuilder where, JsonArray array, JSONObject inParam, String paramKey, String sqlKey) {
        eq(where, array, inParam, paramKey, sqlKey, Integer.class);
    }

    void eqString(StringBuilder where, JsonArray array, JSONObject inParam, String key) {
        eqString(where, array, inParam, key, key);
    }

    void eqString(StringBuilder where, JsonArray array, JSONObject inParam, String paramKey, String sqlKey) {
        eq(where, array, inParam, paramKey, sqlKey, String.class);
    }


    void like(StringBuilder where, JsonArray array, JSONObject inParam, String paramKey) {
        like(where, array, inParam, paramKey, paramKey);
    }

    void like(StringBuilder where, JsonArray array, JSONObject inParam, String paramKey, String sqlKey) {
        if (StringUtils.isBlank(paramKey) || inParam.isEmpty() || !inParam.containsKey(paramKey)) {
            return;
        }
        where.append(DBConstant.and).append(sqlKey).append(DBConstant.like).append(DBConstant.wen).append(DBConstant.blank);
        array.add(inParam.getString(paramKey));
    }

    void eq(StringBuilder where, JsonArray array, JSONObject inParam, String paramKey, String sqlKey, Class clazz) {
        if (StringUtils.isBlank(paramKey) || inParam.isEmpty() || clazz == null || !inParam.containsKey(paramKey) || StringUtils.isBlank(sqlKey)) {
            return;
        }
        where.append(DBConstant.and).append(sqlKey).append(DBConstant.eq).append(DBConstant.wen).append(DBConstant.blank);
        if (Integer.class.equals(clazz)) {
            array.add(inParam.getInteger(paramKey));
        } else if (String.class.equals(clazz)) {
            array.add(inParam.getString(paramKey));
        }
    }
}
