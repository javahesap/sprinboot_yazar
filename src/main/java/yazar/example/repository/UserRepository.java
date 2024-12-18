package yazar.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yazar.example.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Kullanıcı adı ile kullanıcıyı bulmak için özel bir sorgu
    Optional<User> findByUsername(String username);
}
