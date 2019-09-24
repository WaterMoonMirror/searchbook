package com.wondergsroup.scanninglocationfiles.dao;

import com.wondergsroup.scanninglocationfiles.entity.BookFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Repository
public class BookRedisDao {
    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;
    @Autowired
    private JdbcTemplate  jdbcTemplate;

    private static final String KEY="bookfFileKey";

    /**
     *  添加BookFile
     * @param bookFile
     */
    public void addBookFile(BookFile bookFile) throws InterruptedException {
        ReactiveHashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        CountDownLatch cdl=new CountDownLatch(1);
        // 查询全部数据
        List<BookFile> list=jdbcTemplate.query("select *from t_book_file",
                (rs,i)->BookFile.builder().id(rs.getLong("id")).name(rs.getString("name")).build());
        Flux.fromIterable(list)
                .publishOn(Schedulers.single())
                .doOnComplete(()->log.info("begin on!" ))
                .flatMap(c->{
                        log.info("try put id:{},name:{} redis",c.getId(),c.getName());
                        return opsForHash.put(KEY,c.getName(),(c.getId()+","+c.getName()));
                        })
                .doOnComplete(()->log.info("set ok!"))
                .concatWith(redisTemplate.expire(KEY, Duration.ofMinutes(1)))
                .doOnComplete(()->log.info("expire ok"))
                .onErrorResume(e->{
                    log.info("err message:{}",e.getMessage());
                    return Mono.just(false);
                })
                .subscribe(b->log.info("Boolen{}",b),e->log.info("Exception {}",e.getMessage()),()->cdl.countDown());
        log.info("waitting");
        cdl.await();

    }





}
