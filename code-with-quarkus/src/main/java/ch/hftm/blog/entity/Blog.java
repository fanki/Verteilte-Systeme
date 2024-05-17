package ch.hftm.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Blog {

    @Id 
    @GeneratedValue
    private Long id;
    private String _title;
    private String _description;

    public Blog(String title, String description) {
        this._title = title;
        this._description = description;
    }

    public Blog(Long id, String title, String description) {
        this.id = id;
        this._title = title;
        this._description = description;
    }

    public String getTitle() {       
        return _title;
    }

    public String getDescription() {
        return _description;
    }
}
