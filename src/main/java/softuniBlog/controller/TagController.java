package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import softuniBlog.entity.Tag;
import softuniBlog.repository.TagRepository;
import softuniBlog.service.TagServiceImpl;

@Controller
public class TagController  {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    TagServiceImpl tagService;

    @GetMapping("/tag/{name}")
    public String articlesWithTag(Model model, @PathVariable String name){

       Tag tag = tagService.getByName(name);

        if (tag == null){
            return "redirect:/";
        }

        model.addAttribute("view", "tag/articles");
        model.addAttribute("tag", tag);

        return "base-layout";
    }
}
