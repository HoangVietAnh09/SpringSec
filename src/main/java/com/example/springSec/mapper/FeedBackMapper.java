package com.example.springSec.mapper;


import com.example.springSec.Entity.FeedBack;
import com.example.springSec.dto.Request.FeedBackRequest;
import com.example.springSec.dto.Response.FeedBackResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedBackMapper {
    FeedBackResponse toResponse(FeedBack feedBack);
    FeedBack toEntity(FeedBackRequest feedBackRequest);
}
