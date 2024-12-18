package yazar.example.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yazar.example.model.ReadingHistory;
import yazar.example.repository.ArticleRepository;
import yazar.example.repository.ReadingHistoryRepository;
import yazar.example.repository.UsermakaleRepository;
import yazar.example.model.Article;
import yazar.example.model.Usermakale;

@RestController
@RequestMapping("/reading-history")
public class ReadingHistoryController {

    @Autowired
    private ReadingHistoryRepository readingHistoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UsermakaleRepository userRepository;

    @PostMapping("/mark-as-read")
    public ResponseEntity<String> markAsRead(@RequestParam Long userId, @RequestParam Long articleId) {
        try {
            // Kullanıcıyı ve makaleyi bul
        	Usermakale user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Article article = articleRepository.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));

            // Kullanıcı ve makale için okuma geçmişini kontrol et
            ReadingHistory readingHistory = readingHistoryRepository.findByUserAndArticle(user, article);

            if (readingHistory == null) {
                // Eğer okuma geçmişi yoksa, yeni bir kayıt oluştur
                readingHistory = new ReadingHistory();
                readingHistory.setUser(user);
                readingHistory.setArticle(article);
                readingHistory.setReadCount(1); // İlk okuma sayısı
            } else {
                // Eğer okuma geçmişi varsa, okuma sayısını arttır
                readingHistory.setReadCount(readingHistory.getReadCount() + 1);
            }

            // Okuma geçmişini kaydet
            readingHistoryRepository.save(readingHistory);

            // Başarılı cevap döndür
            return new ResponseEntity<>("Article marked as read. Read count: " + readingHistory.getReadCount(), HttpStatus.OK);

        } catch (RuntimeException e) {
            // Hata durumunda anlamlı bir mesaj döndür
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

