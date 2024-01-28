package doremi.services;

import doremi.domain.Article;
import doremi.repositories.ArticleRepositoryInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepositoryInt articleRepository;

    public List<Article> findAllArticles() {
        return articleRepository.findAllArticles();
    }

    public Article findArticleById(int id) {
        return articleRepository.findArticleById(id);
    }

    public ArticleRepositoryInt getArticleRepository() {
        return articleRepository;
    }

    public void setArticleRepository(ArticleRepositoryInt articleRepository) {
        this.articleRepository = articleRepository;
    }
}
