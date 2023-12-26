package project.practice.examease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeaderBoardDTO {
    private int id;
    private String fullname;
    private long totalscore;
    private long rank;
}
