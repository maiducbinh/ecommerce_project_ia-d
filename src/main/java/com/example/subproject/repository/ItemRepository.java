package com.example.subproject.repository;

import com.example.subproject.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Implement search functionality
    List<Item> findByNameContainingIgnoreCase(String name);
}
