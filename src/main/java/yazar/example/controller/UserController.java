package yazar.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import yazar.example.model.Usermakale;
import yazar.example.repository.UsermakaleRepository;

import java.util.List;

@RestController
@RequestMapping("/usermakale")
public class UserController {

	
	
	
	
    @Autowired
    private UsermakaleRepository userRepository;
    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from localhost:3002
    @GetMapping
    public List<Usermakale> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public Usermakale createUser(@RequestBody Usermakale user) {
        return userRepository.save(user);
    }
    
    
    
    
    
    
    
}
