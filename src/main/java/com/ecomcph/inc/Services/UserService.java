package com.ecomcph.inc.Services;

import com.ecomcph.inc.Models.User;
import com.ecomcph.inc.Repository.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    //Metode til at gemme en bruger
    User save(UserRegistrationDto registrationDto);

    //Metode til at gemme en admin bruger
    User saveAdmin(UserRegistrationDto registrationDto);
}