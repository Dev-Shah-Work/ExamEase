package project.practice.examease.mapper;



import org.springframework.stereotype.Service;
import project.practice.examease.dto.TestDto;
import project.practice.examease.entity.Test;
@Service
public class TestMapper {
    public Test dtoToEntity(TestDto testDto){
        Test test=new Test();
        test.setTestScore(testDto.getTestScore());
        test.setResponses(testDto.getResponses());
        test.setAttemptTime(testDto.getAttemptTime());
        test.setCompletionTime(testDto.getCompletionTime());
        return test;
    }

}
