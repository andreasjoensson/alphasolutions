package com.ecomcph.inc.Controllers;

import com.ecomcph.inc.Repository.UserRegistrationDto;
import com.ecomcph.inc.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/adminRegistration")
public class AdminRegistrationController {
        private UserService userService;

        public AdminRegistrationController(UserService userService) {
            super();
            this.userService = userService;
        }

        //ModelAttribute binder parameterene fra User objektet sammen med den information vi modtager igennem UserRegistrationDto .
        @ModelAttribute("user")
        public UserRegistrationDto userRegistrationDto() {
            return new UserRegistrationDto();
        }

        //Viser form til oprettelse af admin konto.
        @GetMapping
        public String showAdminRegistrationForm() {
            return "registrationadmin";
        }

        //Post Request som tager sig af oprettelse af en ny admin konto
        @PostMapping()
        public String registerAdminAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
            userService.saveAdmin(registrationDto);
            return "redirect:/registration?success";
        }

}
