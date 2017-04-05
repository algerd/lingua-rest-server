
package ru.javafx.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.javafx.PathType;
import ru.javafx.utils.ImageUtil;

@RestController
@RequestMapping(value = "api/")
public class ImageController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<String> imageFolderList = Arrays.asList(
            PathType.WORDS.toString()
    );   
    
    @RequestMapping(value = "{folder}/{id}/image", method = RequestMethod.POST, produces = {MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Void> saveArtistImage(
            @PathVariable("id") Long id,
            @PathVariable("folder") String folder,
            HttpEntity<byte[]> requestEntity,
            @RequestHeader HttpHeaders headers) {                          
        
        if (!imageFolderList.contains(folder)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }    
        File file = ImageUtil.createImageFile(folder, id, "jpg");
        byte[] imageInByte = requestEntity.getBody();
        if (ImageUtil.writeImage(imageInByte, "jpg", file)) {            
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
    
    @RequestMapping(value = "{folder}/{id}/image", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteArtistImage(
            @PathVariable("folder") String folder,
            @PathVariable("id") Long id) {

        if (imageFolderList.contains(folder)) {
            ImageUtil.deleteImage(folder, id);
            return new ResponseEntity<>(HttpStatus.OK);                    
        }       
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);     
    }

}
