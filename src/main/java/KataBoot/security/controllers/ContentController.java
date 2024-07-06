package KataBoot.security.controllers;

import KataBoot.security.models.MyUser;
import KataBoot.security.service.UserDetailService;
import KataBoot.security.service.UserRepository;
import KataBoot.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContentController {

    private final UserDetailService userDetailService;

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ContentController(UserDetailService userDetailService, UserService userService, UserRepository userRepository) {
        this.userDetailService = userDetailService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String Welcome() {
        return "home";
    }

    @GetMapping("/user/home")
    public String UserHome() {
        return "userHome";
    }

    @GetMapping("/login")
    public String Login() {
        return "login";
    }
    @GetMapping("admin/new")
    public String NewUser(@ModelAttribute("user") MyUser user) {
        return "new";
    }

    @PostMapping("/save")
    public String CreateUser(@ModelAttribute("user") @Valid MyUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "new";
        }
        userService.saveUser(user);
        return "redirect:/admin/home";
    }
    @GetMapping("/admin/edit")
    public String edit(@RequestParam("id")int id, Model model){
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PostMapping("/admin/update")
    public String updateUser(@RequestParam("id")int id,@ModelAttribute("user") @Valid MyUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user, user.getId());
        return "redirect:/user";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id") int id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }
    @GetMapping("/admin/home")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "adminHome";
    }
}
