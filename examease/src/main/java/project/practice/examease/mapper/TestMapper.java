package project.practice.examease.mapper;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.practice.examease.dto.ResponseDto;
import project.practice.examease.dto.TestDto;
import project.practice.examease.entity.Response;
import project.practice.examease.entity.Test;

import java.util.ArrayList;
import java.util.List;

@Service

public class TestMapper {
    @Autowired
    private final ResponseMapper responseMapper;

    public TestMapper(ResponseMapper responseMapper) {
        this.responseMapper = responseMapper;
    }

    public Test dtoToEntity(TestDto testDto){
        Test test=new Test();
        List<Response> responseList=new ArrayList<>();
        for(ResponseDto resp: testDto.getResponses()){
            if(resp!=null){
                responseList.add(responseMapper.dtoToEntity(resp));

            }
        }
        test.setTestScore(testDto.getTestScore());
        test.setResponses(responseList);
        test.setAttemptTime(testDto.getAttemptTime());
        test.setCompletionTime(testDto.getCompletionTime());
        return test;
    }

}
