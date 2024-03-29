package doremi.repositories;

import doremi.domain.Article;
import java.util.List;

public interface ArticleRepositoryInt {

    public List<Article> findAllArticles();
    public Article findArticleById(int id);
    public Article saveArticle(Article article);

}