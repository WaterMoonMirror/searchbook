package com.wondergsroup.scanninglocationfiles.service;

import com.wondergsroup.scanninglocationfiles.controller.BookfileController;
import com.wondergsroup.scanninglocationfiles.dao.BookMongodbDao;
import com.wondergsroup.scanninglocationfiles.dao.BookRedisDao;
import com.wondergsroup.scanninglocationfiles.entity.BookFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Service
public class BookFileService {
    @Autowired
    private BookRedisDao bookRedisDao;

    @Autowired
    private BookMongodbDao bookMongodbDao;


    public Flux<BookFile> queryAll(){
       return bookMongodbDao.queryAll();
    }


    public void addBookReids(BookFile bookFile){
        try {
            bookRedisDao.addBookFile(bookFile);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addBookMongodb(BookFile bookfile){
        bookMongodbDao.inseartBook(bookfile);
        bookMongodbDao.inseartBook(Arrays.asList(bookfile,bookfile,bookfile));
    }
    public void updateBookMongodb(BookFile bookFile){
        bookMongodbDao.updateBook(bookFile);
    }

}
