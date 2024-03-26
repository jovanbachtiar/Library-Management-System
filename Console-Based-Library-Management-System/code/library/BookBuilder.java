package library;

public interface BookBuilder {
    BookBuilder setTitle(String title);
    BookBuilder setAuthor(String author);
    BookBuilder setPublicationDate(String publicationDate);
    Book build();
}