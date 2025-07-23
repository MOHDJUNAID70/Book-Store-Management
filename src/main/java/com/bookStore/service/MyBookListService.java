package com.bookStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.bookStore.entity.MyBookList;
import com.bookStore.repository.MyBookRepository;
import com.bookStore.entity.User;

@Service
public class MyBookListService {
    @Autowired
    private MyBookRepository mybook;
    
    public void saveMyBooks(MyBookList book){
        mybook.save(book);
    }
    
    public List<MyBookList> getAllMyBooks(User user){
        return mybook.findByUser(user);
    }
    
    public MyBookList findById(int id) {
        return mybook.findById(id).orElse(null);
    }
    
    public void deleteById(int id) {
        mybook.deleteById(id);
    }
}
