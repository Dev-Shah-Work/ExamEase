package project.practice.examease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNo;
    private String role;
}
