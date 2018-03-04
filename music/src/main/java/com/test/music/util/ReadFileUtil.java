package com.test.music.util;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ReadFileUtil {

    private static final JsonObject EMPTY = new JsonObject();

//    private static final log log = LoggerFactory.getLogger(ReadFileUtil.class.getName());

    public static Future<JsonObject> asyncReadJsonFile(Vertx vertx, String filePath) {
        Future<JsonObject> retFuture = Future.future();
        if (StringUtils.isBlank(filePath)) {
            retFuture.fail("要读取的文件路径为空");
        }

        vertx.fileSystem().readFile(filePath, asyncRes -> {
            if (asyncRes.succeeded()) {
                Buffer result = asyncRes.result();
                if (result == null || result.length() == 0) {
                    log.debug("异步读取JSON文件[" + filePath + "]内容为空！！！");
                    retFuture.fail("文件内容为空");
                } else {
                    log.debug("异步读取JSON文件[" + filePath + "]成功！！！");
                    retFuture.complete(new JsonObject(result));
                }
            } else {
                String message = asyncRes.cause().getMessage();
                log.debug(message);
                retFuture.fail(message);
            }
        });
        return retFuture;
    }

    /**
     * 同步读取ClassPath下的配置文件
     *
     * @param vertx
     * @param filePath 文件名称
     * @return
     */
    public static JsonObject readJsonFile(Vertx vertx, String filePath) {
        val fileSystem = vertx.fileSystem();
        return fileSystem.existsBlocking(filePath) ? new JsonObject(fileSystem.readFileBlocking(filePath)) : EMPTY;
    }

}
