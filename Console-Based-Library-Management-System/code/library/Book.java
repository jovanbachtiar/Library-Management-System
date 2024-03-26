package library;

public class Book {
    private String title;
    private String author; 
    private String publicationDate;
    private BookStatus status;

    public Book(String title, String author, String publicationDate){
      this.title = title;
      this.author = author;
      this.publicationDate = publicationDate;
    }

    public String getTitle(){
      return title;
    }

    public String getAuthor(){
      return author;
    }

    public String getPublicationDate(){
      return publicationDate;
    }

    public BookStatus getStatus() {
      return status;
    }

    public void displayInfo(){
      System.out.println("Title: " + title);
      System.out.println("Author: " + author);
      System.out.println("Year Published: " + publicationDate);
    }

}
