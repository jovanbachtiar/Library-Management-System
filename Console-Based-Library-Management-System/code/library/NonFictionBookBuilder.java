package library;

public class NonFictionBookBuilder implements BookBuilder {
  private String title;
  private String author;
  private String publicationDate;

  @Override
  public BookBuilder setTitle(String title) {
      this.title = title;
      return this;
  }

  @Override
  public BookBuilder setAuthor(String author) {
      this.author = author;
      return this;
  }

  @Override
  public BookBuilder setPublicationDate(String publicationDate) {
      this.publicationDate = publicationDate;
      return this;
  }

  @Override
  public Book build() {
      return new NonFictionBook(title, author, publicationDate);
  }
}