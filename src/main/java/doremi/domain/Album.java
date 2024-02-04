package doremi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Album {

    @NotNull
    private String title;

    private Genre genre;

    @Min(1950) @Max(2022)
    private int year;

    @Id
    @GeneratedValue
    private Long id;

    public Album() { }

    public Album(String title, Genre genre, int year) {
        this.setTitle(title);
        this.setGenre(genre);
        this.setYear(year);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }
}