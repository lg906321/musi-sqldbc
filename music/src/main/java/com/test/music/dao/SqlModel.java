package com.test.music.dao;


import java.util.List;

public final class SqlModel {

    private final static String _AND = "AND";
    private final static String _LIKE = "LIKE";
    private final static String _OR = "OR";
    private final static String _WHERE = "WHERE";

    private String select;
    private List<String> whereList;
    private String limit;

    public static void main(String[] args) {
        String sql = "select id,name,img,sex,creatTime FROM user WHERE name LIKE ? AND id=? LIMIT ?,?";
    }
}