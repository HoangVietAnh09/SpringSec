package com.example.springSec.Service;

import com.example.springSec.Entity.Food;
import com.example.springSec.Repository.FoodRepo;
import com.example.springSec.Repository.UserRepo;
import com.example.springSec.dto.Request.FoodRequest;
import com.example.springSec.dto.Response.FoodResponse;
import com.example.springSec.mapper.FoodMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FoodService {
    FoodRepo foodRepo;
    FoodMapper foodMapper;
    private final UserRepo userRepo;

    public FoodResponse addFood(FoodRequest foodRequest) {
        Food food = foodMapper.toFood(foodRequest);
        foodRepo.save(food);
        return foodMapper.toFoodResponse(food);
    }


//    public FoodResponse getFoodById(String id) {
//        return foodMapper.toFoodResponse(foodRepo.findFoodById(id));
//    }

    public List<Food> getFoodById(String id) {
        List<Food> list = new ArrayList<>();
        list.add(foodRepo.findFoodById(id));
        return list;
    }

    public List<Food> getFoodByName(String name) {
        return foodRepo.findFoodByName(name);
    }
}

