package project.practice.examease.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.practice.examease.dto.AppUserDto;
import project.practice.examease.entity.AppUser;
import project.practice.examease.mapper.UserMapper;
import project.practice.examease.repo.AppUserRepository;
import project.practice.examease.security.CustomUserDetailService;
import project.practice.examease.security.JwtUtil;
import project.practice.examease.service.AppUserService;
import project.practice.examease.util.ResponseUtil;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseEntity<String> register(AppUser requestMap) {
        try {
            AppUser appUser = appUserRepository.findByEmail(requestMap.getEmail());
            if (Objects.isNull(appUser)) {
                String password = passwordEncoder.encode(requestMap.getPassword());
                requestMap.setPassword(password);
                appUserRepository.save(requestMap);
                return ResponseUtil.getResponseEntity("Registration Successful", HttpStatus.OK);
            } else {
                return ResponseUtil.getResponseEntity("User already exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in service Implementation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(HashMap<String, String> requestBody) {
        try {
            System.out.println(requestBody.toString());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestBody.get("email"), requestBody.get("password")));
            if (authentication.isAuthenticated()) {
                System.out.println(customUserDetailService.getUserDetail().getEmail());
                System.out.println(customUserDetailService.getUserDetail().getRole());
                System.out.println(jwtUtil.generateToken(customUserDetailService.getUserDetail().getEmail(),
                        customUserDetailService.getUserDetail().getRole()));
                return new ResponseEntity<String>("{\"token\":\"" +
                        jwtUtil.generateToken(customUserDetailService.getUserDetail().getEmail(),
                                customUserDetailService.getUserDetail().getRole()) + "\",\"role\":\"" + customUserDetailService.getUserDetail().getRole() + "\", \"id\" : \"" + customUserDetailService.getUserDetail().getId() + "\"}", HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in service Implementation", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<AppUserDto> findById(Integer id) {
        try {
            Optional<AppUser> user = appUserRepository.findById(id);

            if (user.isPresent()) {
                AppUser appUser = user.get();
                return new ResponseEntity<>(userMapper.convertAppUserToUserDTO(appUser), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new AppUserDto(), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new AppUserDto(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateUser(Integer id, HashMap<String, String> requestBody) {

        try {
            Optional<AppUser> user = appUserRepository.findById(id);

            if (user.isPresent()) {
                AppUser appUser = user.get();
                if (requestBody.containsKey("email")) {
                    AppUser dummyUser = appUserRepository.findByEmail(requestBody.get("email"));
                    if (Objects.isNull(dummyUser) || (dummyUser == appUser)) {
                        appUser.setEmail(requestBody.get("email"));

                    } else {
                        return ResponseUtil.getResponseEntity("User already exist", HttpStatus.BAD_REQUEST);
                    }
                }
                if (requestBody.containsKey("firstname")) {
                    appUser.setFirstname(requestBody.get("firstname"));
                }
                if (requestBody.containsKey("lastname")) {
                    appUser.setLastname(requestBody.get("lastname"));
                }

                if (requestBody.containsKey("phoneNo")) {
                    appUser.setPhoneNo(requestBody.get("phoneNo"));
                }
                appUserRepository.save(appUser);
                return ResponseUtil.getResponseEntity("Update Successful", HttpStatus.OK);
            } else {
                return ResponseUtil.getResponseEntity("Bad Request", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ResponseUtil.getResponseEntity("Something went wrong in service Implementation", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
