package project.practice.examease.mapper;


import org.springframework.stereotype.Service;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.entity.AppUser;

import java.util.Optional;

@Service
public class UserMapper {
    public AppUserDto convertAppUserToUserDTO(AppUser appUser){
        AppUserDto appUserDto=new AppUserDto();
        appUserDto.setId(appUser.getId());
        appUserDto.setRole(appUser.getRole());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setFirstname(appUser.getFirstname());
        appUserDto.setLastname(appUser.getLastname());
        appUserDto.setPhoneNo(appUser.getPhoneNo());
        return appUserDto;
    }
    public Optional<AppUserDto> convertAppUserToUserDTO(Optional<AppUser> appUser){
        return appUser.map(this::convertAppUserToUserDTO);
    }

  
}
