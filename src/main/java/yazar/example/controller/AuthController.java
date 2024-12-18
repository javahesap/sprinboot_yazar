package yazar.example.controller;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import yazar.example.model.User;
import yazar.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";  
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User()); 
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        
        // 1️⃣ Formda hata var mı?
        if (result.hasErrors()) {
            return "register"; 
        }

        // 2️⃣ Kullanıcı adı zaten kullanılıyor mu?
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            model.addAttribute("error", "Username is already taken!");
            return "register"; 
        }

        // 3️⃣ Parola boş mu?
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            model.addAttribute("error", "Password cannot be blank!");
            return "register"; 
        }

        // 4️⃣ Parolayı şifrele
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 5️⃣ Kullanıcıya varsayılan bir rol ata
        user.setRole("USER");

        // 6️⃣ Kullanıcıyı kaydet
        userRepository.save(user);

        // 7️⃣ Login sayfasına yönlendir
        return "redirect:/login";
    }
    
    
    
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll(); // Tüm kullanıcıları al
        model.addAttribute("users", users); // Kullanıcı listesini modele ekle
        return "users"; // users.html şablonunu döndür
    }
    
    
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Geçersiz kullanıcı id: " + id));
        model.addAttribute("user", user);
        return "edit-user"; // edit-user.html formunu render et
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "edit-user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }
    
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users"; // Kullanıcı listesini yeniden yükle
    }
    
    
}



