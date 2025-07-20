package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bookStore.service.MyBookListService;

@Controller
public class MyBookListController {
    @Autowired
    private MyBookListService service;

    @RequestMapping("/deleteMyList/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public String deleteMyList(@PathVariable("id") int id){
        service.deleteById(id);
        return "redirect:/my_books";
    }
}
