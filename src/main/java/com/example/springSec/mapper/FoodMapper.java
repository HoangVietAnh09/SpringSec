package com.example.springSec.mapper;

import com.example.springSec.Entity.Food;
import com.example.springSec.dto.Request.FoodRequest;
import com.example.springSec.dto.Response.FoodResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    FoodResponse toFoodResponse(Food food);
    Food toFood(FoodRequest foodRequest);

}
