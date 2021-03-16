package softuniBlog.bindingModel;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class ArticleBindingModel {

    @NotNull
    private String title;

    @NotNull
    private String content;

    private Integer categoryId;

    private String tagString;

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }
}
