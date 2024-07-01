package com.powerFitness.utils;

import java.sql.*;
import java.util.*;
import java.io.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DBUtils 클래스는 데이터베이스 연결을 관리하는 유틸리티 클래스.
 * 이 클래스는 Singleton 패턴을 사용하여 하나의 HikariDataSource 객체를 공유.
 * 이 클래스는 Singleton 패턴을 사용하여 하나의 HikariDataSource 객체를 공유.
 */
public class DBUtils {
    
    private static HikariDataSource dataSource; // 수정: Connection 객체 대신 HikariDataSource를 사용
    
    private static HikariDataSource dataSource; // 수정: Connection 객체 대신 HikariDataSource를 사용

    // 정적 초기화 블록에서 데이터베이스 연결을 설정
    static {
        try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            
            if (input == null) {
                throw new FileNotFoundException("Sorry, unable to find db.properties");
            }
            
            prop.load(input);
            
            // HikariCP 설정
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));
            
            // 추가 설정: 커넥션 풀의 크기, 타임아웃 등을 설정할 수 있음
            config.setMaximumPoolSize(10); // 풀의 최대 크기 설정
            config.setMinimumIdle(5);      // 최소 유휴 커넥션 수
            config.setIdleTimeout(30000);  // 유휴 커넥션의 타임아웃 시간 (30초)
            config.setConnectionTimeout(30000); // 커넥션 타임아웃 시간 (30초)

            dataSource = new HikariDataSource(config); // 수정: HikariDataSource 인스턴스 생성
        } catch (IOException ex) {
            
            // HikariCP 설정
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(prop.getProperty("db.url"));
            config.setUsername(prop.getProperty("db.username"));
            config.setPassword(prop.getProperty("db.password"));
            
            // 추가 설정: 커넥션 풀의 크기, 타임아웃 등을 설정할 수 있음
            config.setMaximumPoolSize(10); // 풀의 최대 크기 설정
            config.setMinimumIdle(5);      // 최소 유휴 커넥션 수
            config.setIdleTimeout(30000);  // 유휴 커넥션의 타임아웃 시간 (30초)
            config.setConnectionTimeout(30000); // 커넥션 타임아웃 시간 (30초)

            dataSource = new HikariDataSource(config); // 수정: HikariDataSource 인스턴스 생성
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * 데이터베이스 연결 객체를 반환
     * @return Connection 객체
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // 수정: HikariDataSource에서 커넥션을 가져옴
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // 수정: HikariDataSource에서 커넥션을 가져옴
    }
}