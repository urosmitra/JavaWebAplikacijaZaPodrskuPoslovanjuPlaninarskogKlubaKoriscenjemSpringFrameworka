package com.urosmitrasinovic61017.planinarski_klub_webapp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {


    @RequestMapping(value = "/loginPage")
    public String showLoginForm(){
        return "security/loginForm";
    }


}
