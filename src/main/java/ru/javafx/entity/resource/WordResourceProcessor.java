
package ru.javafx.entity.resource;

import java.io.File;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import ru.javafx.PathType;
import ru.javafx.entity.Word;
import ru.javafx.utils.ImageUtil;

@Component
public class WordResourceProcessor implements ResourceProcessor<Resource<Word>> {

    @Override
    public Resource<Word> process(Resource<Word> resource) {
        File imageFile = ImageUtil.createImageFile(PathType.WORDS.toString(), resource.getContent().getId(), "jpg");
        if (imageFile.exists()) {
            resource.add(new Link("http://localhost:8080/images/" + PathType.WORDS.toString() + "/" + imageFile.getName(),
                    "get_image"));
        }
        resource.add(new Link(resource.getId().getHref() + "/image", "post_delete_image"));
        return resource;
    }
}
