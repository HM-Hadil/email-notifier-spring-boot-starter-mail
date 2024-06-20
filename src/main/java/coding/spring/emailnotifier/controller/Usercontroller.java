package coding.spring.emailnotifier.controller;

import coding.spring.emailnotifier.entity.User;
import coding.spring.emailnotifier.repo.UserRepo;
import coding.spring.emailnotifier.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class Usercontroller {
    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public void CreateUser(@RequestBody User user){
        this.userService.createUser(user);
    }

    @GetMapping("/getUserById/{userId}")
    public Optional<User>  findUserById(@PathVariable UUID userId){
        return  this.userService.getUserById(userId);
    }
    @GetMapping("/getAllUser")
    public List<User> findAllUser(){
        return  this.userService.getAllUsers();
    }


}
