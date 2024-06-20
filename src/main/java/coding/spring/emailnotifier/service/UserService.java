package coding.spring.emailnotifier.service;

import coding.spring.emailnotifier.entity.User;
import coding.spring.emailnotifier.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public void createUser(User user){
        this.userRepo.save(user);
    }
    public List<User> getAllUsers(){
        return this.userRepo.findAll();
    }
    public Optional<User> getUserById(UUID userId){
        return this.userRepo.findById(userId);
    }


}
