package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.*;

@Repository
public interface BookRepository extends JpaRepository <Book,Integer>{

}
