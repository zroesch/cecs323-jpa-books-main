package csulb.cecs323.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedNativeQuery(
        name="ReturnAuthoringEntities",
        query = "SELECT * " +
                "FROM   AUTHORINGENTITIES " +
                "WHERE  email = ? ",
        resultClass = Publishers.class
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AuthoringEntities {
    @Id
    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 31, nullable = false)
    private String authoringEntityType;

    @OneToMany (mappedBy = "entities",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Books> books = new ArrayList<>();

    public AuthoringEntities() { }

    public AuthoringEntities(String name, String email, String authoringEntityType) {
        this.name = name;
        this.email = email;
        this.authoringEntityType = authoringEntityType;
    }

    public void addBooks(Books book) {
        books.add(book);
        book.setAuthoringEntities(this);
    }

    public void removeBooks(Books book) {
        if (this.books != null) {
            books.remove(book);
            book.setAuthoringEntities(null);
        }
    }

    public List<Books> getBooks() { return books; }

    public void setBooks(List<Books> books) { this.books = books; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthoringEntityType() {
        return authoringEntityType;
    }

    public void setAuthoringEntityType(String authoringEntityType) {
        this.authoringEntityType = authoringEntityType;
    }

    public String toString(){
        return "Author Name: " + this.getName() + " Email: " + this.getEmail() + " EntityType: " + this.getAuthoringEntityType() + " Books: " + this.getBooks();
    }
}
