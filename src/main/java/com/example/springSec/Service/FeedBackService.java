package com.example.springSec.Service;

import com.example.springSec.Entity.FeedBack;
import com.example.springSec.Repository.FeedBackRepo;
import com.example.springSec.dto.Request.FeedBackRequest;
import com.example.springSec.dto.Response.FeedBackResponse;
import com.example.springSec.mapper.FeedBackMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FeedBackService {

    FeedBackRepo feedBackRepo;
    FeedBackMapper feedBackMapper;

    public FeedBackResponse addFeedBack(FeedBackRequest feedBackRequest) {
        FeedBack feedBack = feedBackMapper.toEntity(feedBackRequest);
        return feedBackMapper.toResponse(feedBackRepo.save(feedBack));
    }

    public List<FeedBackResponse> getAllFeedBack() {
        List<FeedBack> feedBackList = feedBackRepo.findAll();
        List<FeedBackResponse> feedBackResponseList = new ArrayList<>();
        for(FeedBack feedBack : feedBackList){
            feedBackResponseList.add(feedBackMapper.toResponse(feedBack));
        }
        return feedBackResponseList;
    }

    public void resetFeebBack() {}
}
