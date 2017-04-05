
package ru.javafx.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="word")
public class Word implements Serializable {
    
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	private Long id;
    
    @NotNull(message = "error.word.word.empty")
    @Size(max = 255, message = "error.word.word.size")
    @Column(name = "word")
    private String word;
    
    @Size(max = 255, message = "error.word.transcription.size")
    @Column(name = "transcription")
    private String transcription;
      
    @NotNull(message = "error.word.translation.empty")
    @Size(min = 1, max = 1000, message = "error.word.translation.size")
    @Column(name = "translation")
    private String translation;
          
    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
      
    public Word() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
    
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
      
    @Override
    public String toString() {
        return "Word{" 
            + "id=" + id 
            + ", word=" + word 
            + ", transcription=" + transcription 
            + ", translation=" + translation 
            + ", created=" + created 
            + '}';
    }
    
}
