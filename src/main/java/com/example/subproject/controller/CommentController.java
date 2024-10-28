package com.example.subproject.controller;

import com.example.subproject.entity.*;
import com.example.subproject.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public String addComment(@RequestParam Long itemId,
                             @RequestParam String content,
                             Authentication authentication,
                             Model model){
        String username = authentication.getName();
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Item item = itemService.getItemById(itemId);
        if(item == null){
            model.addAttribute("error", "Item not found");
            return "redirect:/items";
        }

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCustomer(customer);
        comment.setItem(item);
        commentService.addComment(comment);

        return "redirect:/items/" + itemId;
    }

    // Additional comment-related endpoints can be added here
}
