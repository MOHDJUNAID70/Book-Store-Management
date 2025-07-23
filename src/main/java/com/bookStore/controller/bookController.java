package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import com.bookStore.entity.*;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;
import com.bookStore.service.UserService;
import com.bookStore.entity.User;

import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.security.Principal;


@Controller
public class bookController {

    @Autowired
    private BookService service;
    
    @Autowired
    private MyBookListService myBookservice;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/book_register")
    @PreAuthorize("hasRole('ADMIN')")
    public String bookRegister(Model model){
        model.addAttribute("book", new Book());
        return "bookRegister";
    }
    @GetMapping("/available_books")
    public ModelAndView getAllBooks(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String minPrice,
                                    @RequestParam(required = false) String maxPrice,
                                    @RequestParam(required = false) String category) {
        List<Book> list = service.getAllBooks();
        if (name != null && !name.isEmpty()) {
            list = list.stream().filter(b -> b.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }
        if (author != null && !author.isEmpty()) {
            list = list.stream().filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase())).toList();
        }
        if (minPrice != null && !minPrice.isEmpty()) {
            try {
                double min = Double.parseDouble(minPrice);
                list = list.stream().filter(b -> Double.parseDouble(b.getPrice()) >= min).toList();
            } catch (Exception ignored) {}
        }
        if (maxPrice != null && !maxPrice.isEmpty()) {
            try {
                double max = Double.parseDouble(maxPrice);
                list = list.stream().filter(b -> Double.parseDouble(b.getPrice()) <= max).toList();
            } catch (Exception ignored) {}
        }
        if (category != null && !category.isEmpty()) {
            list = list.stream().filter(b -> category.equals(b.getCategory())).toList();
        }
        // Extract unique, non-null categories
        Set<String> categories = list.stream()
            .map(Book::getCategory)
            .filter(Objects::nonNull)
            .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
        ModelAndView mav = new ModelAndView("bookList", "book", list);
        mav.addObject("categories", categories);
        return mav;
    }
    @GetMapping("/my_books")
    public ModelAndView getMyBooks(Principal principal){
        User currentUser = userService.findByUsernameOrEmail(principal.getName());
        List<MyBookList> list = myBookservice.getAllMyBooks(currentUser);
        return new ModelAndView("MyBook", "book", list);
    }
    
    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id, Principal principal){
        User currentUser = userService.findByUsernameOrEmail(principal.getName());
        Book b = service.getBookById(id);
        MyBookList obj = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice(), b.getImageName(), currentUser);
        myBookservice.saveMyBooks(obj);
        return "redirect:/available_books?added=success";
    }
    
    @GetMapping("/editBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book book = service.getBookById(id);
        model.addAttribute("book", book);
        return "bookRegister";
    }

    @PostMapping("/updateBook")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateBook(@ModelAttribute Book b) {
        service.save(b);
        // Redirect to available_books with category filter
        return "redirect:/available_books?category=" + b.getCategory();
    }
    
    @GetMapping("/deleteBook/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable("id") int id) {
        service.deleteBookById(id);
        return "redirect:/available_books";
    }
}
