package com.ecomcph.inc.Services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.ecomcph.inc.Models.Role;
import com.ecomcph.inc.Models.User;
import com.ecomcph.inc.Models.UserRepository;
import com.ecomcph.inc.Repository.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//Implementere UserService interface, så vi gør brug af Save metoderne til at gemme brugere.
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    //Sørger for at oprette en bruger, med "ROLE_USER" som tildeler brugeren normale bruger rettigheder.
    @Override
    public User save(UserRegistrationDto registrationDto) {
        //passwordEncoder sørger for at brugerens password bliver krypteret.
        //Ved at kryptere koden, sørger vi for at database adminstratoren aldrig har mulighed for at se brugerens PASSWORD
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));

        return userRepository.save(user);
    }

    //Sørger for at oprette en bruger, med "ROLE-ADMIN" som tildeler brugeren adminstrator rettigheder.
    @Override
    public User saveAdmin(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_ADMIN")));
        return userRepository.save(user);
    }

    @Override
    // Herefter bliver findByEmail implementeret i nedestående metode, hvis formål er, at validere
    // om emailen eksisterer i databasen, og herefter smider den en fejl, hvis email er null.
    // Vi etablere forbindelse ved at tildele user objektet metoden.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }


    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}