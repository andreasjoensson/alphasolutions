package com.ecomcph.inc.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @GetMapping("/login")
    public String renderLogin() {
        return "login.html";
    }

    //Vis Index html side
    @GetMapping("/")
    public String renderIndex() {
        return "index.html";
    }
}

