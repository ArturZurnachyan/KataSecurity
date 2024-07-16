package KataBoot.security.controllers;

import KataBoot.security.models.User;
import KataBoot.security.service.RoleService;
import KataBoot.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ContentController {



    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public ContentController( UserService userService, RoleService roleService) {
        this.roleService=roleService;
        this.userService = userService;

    }

    @GetMapping("/home")
    public String Welcome() {
        return "home";
    }

    @GetMapping("user/home")
    public String userHome(@AuthenticationPrincipal User user, Model model) {
        if (user == null) {
            throw new UsernameNotFoundException("User is not authenticated");
        }
        model.addAttribute("page","user-home");
        model.addAttribute("user", user);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Получаем строковое представление каждой роли
                .toList();
        model.addAttribute("roleList", roles);
        return "userHome";
    }

    @GetMapping("/admin/home")
    public String getUsers(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", user.getRoleList());
        model.addAttribute("page","admin-home");
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // Получаем строковое представление каждой роли
                .toList();
        model.addAttribute("roleList", roles);
        return "adminHome";
    }


    @GetMapping("/login")
    public String Login() {
        return "login";
    }


    @GetMapping("admin/new")
    public String NewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "new";
    }

    @PostMapping("/admin/register")
    public String CreateUser(@ModelAttribute("user") @Valid User user, BindingResult result,
                             @RequestParam("selectedRoles") List<Long> selectedRoles) {
        if (result.hasErrors()) {
            return "new";
        }

        if (selectedRoles.isEmpty()) {
            result.rejectValue("selectedRoles", "error.user", "Выберите хотя бы одну роль");
            return "new";
        }

        userService.saveUserWithRoles(user,selectedRoles);
        return "redirect:/admin/home";
    }


    @GetMapping("/admin/edit")
    public String edit(Model model, @RequestParam(value = "id") long id){
        model.addAttribute("editUser", userService.getUserById(id));
        model.addAttribute("roles", roleService.findAll());
        return "edit";
    }

    @PostMapping("/admin/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result,
                             @RequestParam("selectedRoles")List<Long> selectedRoles) {
        if (result.hasErrors()) {
            return "edit";
        }
        userService.updateUser(user, selectedRoles);
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam(name = "id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/home";
    }
}
