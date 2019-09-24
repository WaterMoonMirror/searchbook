package com.wondergsroup.scanninglocationfiles;

import com.wondergsroup.scanninglocationfiles.entity.BookFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class ScanningLocationFilesApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ScanningLocationFilesApplication.class,args);
    }

    // 添加redis模板
    @Bean
    public ReactiveStringRedisTemplate reactiveStringRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory){
        return  new ReactiveStringRedisTemplate(reactiveRedisConnectionFactory);
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void run(String... args) throws Exception {
        Arrays.asList("a","b","c").forEach(name->
                jdbcTemplate.update("insert into t_book_file(name) values(?)",name)
        );
        jdbcTemplate.query("select * from t_book_file", new RowMapper<BookFile>() {
            @Override
            public BookFile mapRow(ResultSet resultSet, int i) throws SQLException {
                return BookFile.builder().id(resultSet.getLong(1)).name(resultSet.getString(2)).build();
            }
        }).forEach(c->log.info("Bookfile{}",c));
    }
}
