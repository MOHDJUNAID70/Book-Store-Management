package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import java.security.Principal;
import com.bookStore.service.MyBookListService;
import com.bookStore.service.UserService;
import com.bookStore.entity.MyBookList;
import com.bookStore.entity.User;

@Controller
public class MyBookListController {
    @Autowired
    private MyBookListService service;
    
    @Autowired
    private UserService userService;

    @RequestMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id") int id, Principal principal){
        User currentUser = userService.findByUsernameOrEmail(principal.getName());
        MyBookList book = service.findById(id);
        if (book != null && currentUser != null && book.getUser().getId().equals(currentUser.getId())) {
            service.deleteById(id);
        }
        return "redirect:/my_books";
    }
    
    @GetMapping("/payment")
    @PreAuthorize("hasRole('USER')")
    public String paymentPage() {
        return "payment";
    }
}
