package gr.aueb.cf.library.dto;

public class BookDeleteDTO extends Base {
    private String title;
    private String author;
    private boolean isLoaned;

    public BookDeleteDTO() {}

    public BookDeleteDTO(int id, String title, String author, boolean isLoaned) {
        this.setId(id);
        this.title = title;
        this.author = author;
        this.isLoaned = isLoaned;
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
}
