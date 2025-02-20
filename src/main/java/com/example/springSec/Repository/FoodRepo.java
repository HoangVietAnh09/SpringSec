package com.example.springSec.Repository;

import com.example.springSec.Entity.Food;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<Food, String> {

    @Query(value = "SELECT * FROM food WHERE id='" + "?1" + "'", nativeQuery = true)
    Food findFoodById(String id);

//    @Query(value = "SELECT * FROM food WHERE name LIKE %:name%", nativeQuery = true)
//    @Query(value = "SELECT * FROM food WHERE name = :name", nativeQuery = true)
////    @Query("SELECT u FROM Food u WHERE u.name = ?1")
//    List<Food> findFoodByName(String name);

    List<Food> findFoodByName(String name);


}
