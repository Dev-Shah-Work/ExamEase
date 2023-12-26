package project.practice.examease.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortalStatDto {
    int totalQuizes;
    int totalTests;
    int totalUsers;
}
