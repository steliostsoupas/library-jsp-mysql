package gr.aueb.cf.library.dto;

public abstract class Base {
    private Integer id;

    public Base() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
