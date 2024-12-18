package yazar.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yazar.example.model.Usermakale;

public interface UsermakaleRepository extends JpaRepository<Usermakale, Long> {
}