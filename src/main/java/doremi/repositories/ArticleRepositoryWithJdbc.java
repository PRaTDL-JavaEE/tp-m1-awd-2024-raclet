package doremi.repositories;

import doremi.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepositoryWithJdbc implements ArticleRepositoryInt {

    @Autowired
    private DataSource dataSource;

    public List<Article> findAllArticles() {
        List<Article> articles = new ArrayList<>();
        ResultSet fetchedArticles;

        Connection connexion = null;
        try {
            connexion = dataSource.getConnection();
            Statement statement = connexion.createStatement();
            fetchedArticles = statement.executeQuery("SELECT * FROM articles");
            while(fetchedArticles.next()) {
                Article art = new Article();
                art.setArticleId(fetchedArticles.getInt("id"));
                art.setTitle(fetchedArticles.getString("title"));
                art.setCategory(fetchedArticles.getString("category"));
                articles.add(art);
            }
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connexion != null)
                    connexion.close();
            } catch(SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return articles;
    }

    public Article findArticleById(int id) {
        Article article = null;
        ResultSet fetchedArticle;
        Connection connexion = null;

        try {
            connexion = dataSource.getConnection();
            PreparedStatement statement =
                    connexion.prepareStatement("SELECT id, title, category FROM articles WHERE id = ?");

            statement.setInt(1, id);
            fetchedArticle = statement.executeQuery();

            if(fetchedArticle.next()) {
                article = new Article();
                article.setArticleId(fetchedArticle.getInt("id"));
                article.setTitle(fetchedArticle.getString("title"));
                article.setCategory(fetchedArticle.getString("category"));
            }
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connexion != null)
                    connexion.close();
            } catch(SQLException sqle) {
                sqle.printStackTrace();
            }
        }

        return article;

    }

}
