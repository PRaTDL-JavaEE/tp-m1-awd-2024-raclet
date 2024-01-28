package doremi.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Album {

    @NotNull
    private String title;

    private Genre genre;

    @Min(1950) @Max(2022)
    private int year;

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
}