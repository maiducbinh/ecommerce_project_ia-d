package com.example.subproject.controller;

import com.example.subproject.entity.Item;
import com.example.subproject.service.CommentService;
import com.example.subproject.service.ItemService;
import com.example.subproject.service.RatingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public String listItems(Model model, @RequestParam(value = "search", required = false) String search){
        List<Item> items;
        if(search != null && !search.isEmpty()){
            items = itemService.searchItems(search);
        } else {
            items = itemService.getAllItems();
        }
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/{id}")
    public String getItemDetail(@PathVariable Long id, Model model){
        Item item = itemService.getItemById(id);
        if(item == null){
            return "redirect:/items";
        }
        model.addAttribute("item", item);
        model.addAttribute("comments", commentService.getCommentsByItemId(id));
        model.addAttribute("ratings", ratingService.getRatingsByItemId(id));
        return "item_detail";
    }

    // Additional item-related endpoints can be added here
}

