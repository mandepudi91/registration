package com.framework.registration.Controller;

import com.framework.registration.Entity.User;
import com.framework.registration.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
//@RequestMapping(path="/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registerService;

    @PostMapping(path="/addUser")
    public
    ModelAndView results (@ModelAttribute User user) {
        registerService.saveUser(user);
        ModelAndView mav = new ModelAndView();
        mav.addObject("name",user.getFirsName()+" "+user.getLastName());
        mav.setViewName("result");
        return mav;
    }

    @GetMapping(path="/getAllEmployees")
    public String getAllUsers(Model model) {
        model.addAttribute("employees",registerService.findAllUsers());
        return "registration";
    }

    @GetMapping("/redirect")
    public String register(Model model)
    {
        model.addAttribute("user", new User());
        return "addemployee";
    }

    @DeleteMapping("/deleteuser")
    public @ResponseBody
    String delete (@Param("id") long id) {
        User user = registerService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        registerService.deleteUser(user);
        return "deleted";
    }

    @PutMapping("/updateuser")
    public @ResponseBody
    String update (@Param("id") long id,@Param("firstname") String firstName,
                   @Param("lastname") String lastName,@Param("email") String email) {
        User user = registerService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setEmail(email);
        user.setFirsName(firstName);
        user.setLastName(lastName);
        registerService.saveUser(user);
        return "updated";
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {

        registerService.saveUser(user);
        ModelAndView mav = new ModelAndView();
        mav.addObject("name",user.getFirsName()+" "+user.getLastName());
        mav.setViewName("result");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") long id, Model model) {
        User user = registerService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        ModelAndView mav = new ModelAndView();
        mav.addObject("name",user.getFirsName()+" "+user.getLastName());
        mav.setViewName("result");
        registerService.deleteUser(user);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = registerService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "updateuser";
    }

}
