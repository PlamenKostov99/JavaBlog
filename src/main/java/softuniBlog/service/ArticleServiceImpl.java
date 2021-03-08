package softuniBlog.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Category;
import softuniBlog.entity.Tag;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CategoryRepository;
import softuniBlog.repository.UserRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagServiceImpl tagService;

    @Override
    public void createArticles(ArticleBindingModel articleBindingModel,
                               @RequestParam("image") MultipartFile file) throws IOException {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());
        Category category = this.categoryRepository.findCategoriesById(articleBindingModel.getCategoryId());
        HashSet<Tag> tags = this.tagService.getTagsFromString(articleBindingModel.getTagString());

        Article articleEntity = new Article(articleBindingModel.getTitle(),
                articleBindingModel.getContent(),
                userEntity, category, tags, file.getBytes());

        this.articleRepository.saveAndFlush(articleEntity);
    }

    @Override
    public Article getById(Integer id) {
        return this.articleRepository.findById(id).orElse(null);
    }

    @Override
    public void editProcess(ArticleBindingModel articleBindingModel, Integer id, Article article,
                            @RequestParam("image") MultipartFile file) throws IOException {

        Category category = this.categoryRepository.findCategoriesById(articleBindingModel.getCategoryId());
        HashSet<Tag> tags = this.tagService.getTagsFromString(articleBindingModel.getTagString());


        article.setCategory(category);
        article.setContent(articleBindingModel.getContent());
        article.setTitle(articleBindingModel.getTitle());
        article.setTags(tags);
        article.setPhotos(file.getBytes());

        this.articleRepository.saveAndFlush(article);


    }

    @Override
    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    @Override
    public boolean existsById(Integer id) {
        return this.articleRepository.existsById(id);
    }

    @Override
    public String getImgUtility(@PathVariable Integer id) throws UnsupportedEncodingException {


        byte[] encodeBase64 = Base64.encodeBase64(this.articleRepository.getById(id).getPhotos());
        String base64Encoded = new String(encodeBase64, "UTF-8");
        return base64Encoded;
    }

    @Override
    public void countClick(@PathVariable Integer id) {

        Article article = this.articleRepository.getById(id);

        int counter = article.getLikes();

        counter += 1;

        article.setLikes(counter);




        this.articleRepository.saveAndFlush(article);

    }


}
