package com.wondergsroup.scanninglocationfiles.dao;

import com.wondergsroup.scanninglocationfiles.entity.BookFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Repository
public class BookMongodbDao {
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    /**
     *  添加一条记录
     * @param bookFile
     */
    public void inseartBook(BookFile bookFile){
        mongoTemplate.insert(bookFile)
                .publishOn(Schedulers.single())
                .doOnNext(c->log.info("bookfile{}",c))
                .subscribe(c->log.info("bookfile{}",c));
    }

    /**
     *  添加多条记录
     * @param list
     */
    public void inseartBook(List<BookFile> list){
        mongoTemplate.insertAll(list)
                .publishOn(Schedulers.single())
                .doOnNext(c->log.info("bookfile{}",c))
                .doOnComplete(()->log.info("insert ok"))
                .doFinally(c->log.info("finally{}",c))
                .count()
                .subscribe(c->log.info("insert {} records",c));
    }

    /**
     *  修改记录
     * @param bookFile
     */
    public void updateBook(BookFile bookFile){
        mongoTemplate.updateMulti(Query.query(Criteria.where("id").is(bookFile.getId())),
                new Update().set("name",bookFile.getName()),BookFile.class)
        .doFinally(c->log.info("finally{}",c))
        .subscribe(r->log.info("result {}",r));
    }

    public Flux<BookFile> queryAll(){
       return mongoTemplate.findAll(BookFile.class);
    }


}
