package com.wondergsroup.scanninglocationfiles.controller;

import com.wondergsroup.scanninglocationfiles.entity.BookFile;
import com.wondergsroup.scanninglocationfiles.service.BookFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequestMapping("/bookfile")
public class BookfileController {
    @Autowired
    private BookFileService bookFileService;

    @PostMapping("/")
    public Mono<String> addBookfile(BookFile bookFile){
        bookFileService.addBookReids(bookFile);
        bookFileService.addBookMongodb(bookFile);
        return Mono.just("ok");
    }
    @PutMapping("/")
    public Mono<String> updateBookFile(BookFile bookFile){
        bookFileService.updateBookMongodb(bookFile);
        return Mono.just("ok");
    }
    @GetMapping("/")
    public Flux<BookFile> updateBookFile(){
        return bookFileService.queryAll();
    }

}
