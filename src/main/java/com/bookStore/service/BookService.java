package com.bookStore.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookStore.repository.BookRepository;
import com.bookStore.entity.*;

@Service
public class BookService {
    @Autowired
    private BookRepository bRepo;
    public void save(Book b){
        bRepo.save(b);
    }
    public List<Book> getAllBooks(){
        return bRepo.findAll();
    }
    public Book getBookById(int id){
        return bRepo.findById(id).get();
    }
    public void deleteBookById(int id){
        bRepo.deleteById(id);
    }
}
