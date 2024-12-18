package yazar.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yazar.example.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}