package gr.aueb.cf.library.model;

public class Book {
    private Integer id;
    private String title;
    private String author;
    private boolean isLoaned;

    public Book() {}

    public Book(Integer id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public Book(Integer id, String title, String author, boolean isLoaned) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isLoaned = isLoaned;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public boolean isLoaned() {
        return isLoaned;
    }
    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", " + "Title: " + title + ", " + "Author: " + author;
    }
}
