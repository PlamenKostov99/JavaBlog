package softuniBlog.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.Article;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface ArticleService {

    void createArticles(ArticleBindingModel articleBindingModel, @RequestParam("image") MultipartFile file) throws IOException;

    Article getById(Integer id);

    void editProcess(ArticleBindingModel articleBindingModel, Integer id, Article article,
                     @RequestParam("image") MultipartFile file) throws IOException;

    void delete(Article article);

    boolean existsById(Integer id);

    String getImgUtility(@PathVariable Integer id) throws UnsupportedEncodingException;

    void countClick(@PathVariable Integer id);
}
