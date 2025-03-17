package com.example.springSec.DAO;


import com.example.springSec.Entity.Food;
import com.example.springSec.dto.Response.FoodResponse;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
public class FoodDAO {

    EntityManager entityManager;


    public List<Food> getFoodByName(String name) {
        var query = "FROM Food WHERE name = '" + name + "'";
        var exe = entityManager.createQuery(query, Food.class);
        return exe.getResultList();
    }
}
