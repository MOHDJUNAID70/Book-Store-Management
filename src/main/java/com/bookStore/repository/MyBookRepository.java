package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.bookStore.entity.MyBookList;
import com.bookStore.entity.User;

@Repository
public interface MyBookRepository extends JpaRepository<MyBookList, Integer>{
    List<MyBookList> findByUser(User user);
}
