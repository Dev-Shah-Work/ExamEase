package project.practice.examease.mapper;

import org.springframework.stereotype.Service;
import project.practice.examease.dto.LeaderBoardDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LeaderBoardMapper {

    public static List<LeaderBoardDTO> convertToDTO(List<Map<String, Object>> queryResult) {
        List<LeaderBoardDTO> leaderBoardDTOList = new ArrayList<>();

        for (Map<String, Object> row : queryResult) {
            LeaderBoardDTO leaderBoardDTO = new LeaderBoardDTO();
            leaderBoardDTO.setId((int) row.get("id"));
            leaderBoardDTO.setFullname((String) row.get("fullname"));
            leaderBoardDTO.setTotalscore((long) row.get("totalscore"));
            leaderBoardDTO.setRank((long) row.get("rank"));
            leaderBoardDTOList.add(leaderBoardDTO);
        }

        return leaderBoardDTOList;
    }
}
