package gr.aueb.cf.library.dto;

public class BookUpdateDTO extends Base {
    private String title;
    private String author;
    private boolean isLoaned;

    public BookUpdateDTO() {}

    public BookUpdateDTO(int id, String title, String author) {
        this.setId(id);
        this.title = title;
        this.author = author;
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
}
