package project.practice.examease.mapper;

import org.springframework.stereotype.Service;
import project.practice.examease.dto.ResponseDto;
import project.practice.examease.entity.Response;
@Service
public class ResponseMapper {
    public Response dtoToEntity(ResponseDto responseDto){
        Response response=new Response();
        response.setId(responseDto.getId());
        if(responseDto.getResponseId().getId()!=0 ){
            response.setResponseId(responseDto.getResponseId());

        }
        response.setResponseText(responseDto.getResponseText());
        response.setScore(responseDto.getScore());
        return response;
    }

}
