package project.practice.examease.mapper;

import project.practice.examease.dto.ResponseDto;
import project.practice.examease.entity.Response;

public class ResponseMapper {
    public Response dtoToEntity(ResponseDto responseDto){
        Response response=new Response();
        response.setResponseId(responseDto.getResponseId());
        response.setResponseText(responseDto.getResponseText());
        response.setScore(responseDto.getScore());
        return response;
    }
}
