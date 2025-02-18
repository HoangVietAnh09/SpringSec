package com.example.springSec.Controller;


import com.example.springSec.Entity.Food;
import com.example.springSec.Service.FoodService;
import com.example.springSec.dto.Request.FoodRequest;
import com.example.springSec.dto.Response.ApiResponse;
import com.example.springSec.dto.Response.FoodResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FoodController {

    FoodService foodService;

//    @PostMapping()
//    public ApiResponse<FoodResponse> addFood(@RequestBody FoodRequest foodRequest) {
//        return ApiResponse.<FoodResponse>builder()
//                .result(foodService.addFood(foodRequest))
//                .build();
//    }

    @GetMapping()
    public ApiResponse<List<Food>> getFoodByName(@RequestParam String name) {
        return ApiResponse.<List<Food>>builder()
                .result(foodService.getFoodByName(name))
                .build();
    }

    @PostMapping()
    public ApiResponse<List<Food>> updateFood(@RequestParam String search) {
        return ApiResponse.<List<Food>>builder()
                .result(foodService.getFoodByName(search))
                .build();
    }
}
