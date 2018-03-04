package com;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;


public class earth {

    public static void main(String[] args) {
        String name = "db.properties";
        URL systemResource = ClassLoader.getSystemResource(name);
        String dbPath = systemResource.toString().substring(6);
        System.out.println(dbPath);
        HikariConfig config = new HikariConfig(dbPath);
        DataSource dataSource = new HikariDataSource(config);
        System.out.println(dataSource);
//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("development", transactionFactory, dataSource);
//        Configuration configuration = new Configuration(environment);
////        configuration.addMapper(BlogMapper.class);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }
}