package project.practice.examease.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.practice.examease.entity.AppUser;
import project.practice.examease.repo.AppUserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public CustomUserDetailService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }

    AppUser appUser;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password = null;
        List<GrantedAuthority> authorities = null;
        appUser = appUserRepository.findByEmail(username);
        System.out.println("from customer"+ appUser);
        if (appUser == null) {

            throw new UsernameNotFoundException("UserDetails not found");
        } else {
            userName = appUser.getEmail();
            password = appUser.getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(appUser.getRole()));
            return new User(username, password, authorities);
        }

    }
    public AppUser getUserDetail(){
        return appUser;
    }
}
