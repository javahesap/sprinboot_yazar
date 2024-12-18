package yazar.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yazar.example.model.Article;
import yazar.example.model.ReadingHistory;
import yazar.example.model.Usermakale;

public interface ReadingHistoryRepository extends JpaRepository<ReadingHistory, Long> {
    ReadingHistory findByUserAndArticle(Usermakale user, Article article);
}
