package doremi.services;

import doremi.domain.Article;
import doremi.repositories.ArticleRepositoryInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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

    public Article saveArticle(Article article) {
        if (article == null)
            throw new InvalidDataAccessApiUsageException("Article must not be null");
        if (findArticleById(article.getArticleId()) != null)
            throw new InvalidDataAccessApiUsageException("L'id ne doit pas déjà être en base");

        return articleRepository.saveArticle(article);
    }

    public ArticleRepositoryInt getArticleRepository() {
        return articleRepository;
    }

    public void setArticleRepository(ArticleRepositoryInt articleRepository) {
        this.articleRepository = articleRepository;
    }
}
