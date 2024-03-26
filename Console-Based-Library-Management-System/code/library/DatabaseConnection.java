package library;

import java.util.HashMap;
import java.util.Map;

// Using singleton pattern 
// The constructor of the class is declared as private to prevent instantiation of the class from the outside
public class DatabaseConnection {
  private static DatabaseConnection instance;
  private Map<String, User> users;
  private Map<String, Book> books;

  private DatabaseConnection(){
    users = new HashMap<>();
    books = new HashMap<>();
  }

  public static DatabaseConnection getInstance(){
    if(instance == null){
      instance = new DatabaseConnection();
    }
    return instance;
  }

  public void addUser(User user){
    users.put(user.getName(), user);
  }

  public User getUser(String name){
    return users.get(name);
  }

  public void addBook(Book book){
    books.put(book.getTitle(), book);
  }

  public boolean removeBook(String title) {
    return books.remove(title) != null;
  } 

  public void displayBooks() {
    if (books.isEmpty()) {
      System.out.println("No books available.");
  } else {
      System.out.println("Listing all books:");
      for (Map.Entry<String, Book> entry : books.entrySet()) {
          Book book = entry.getValue();
          book.displayInfo(); // Call displayInfo() for each book
          System.out.println(); // Add a newline for separation
      }
    }
  }

  public boolean borrowBook(String title, User user) {
    // Logic to borrow a book with the given title for the specified user
    
    Book book = getBookByTitle(title);
    if (book != null && book.getStatus() instanceof Available) {
        // Implement the logic to update book status, user, and borrowing timestamp
        book.setStatus(new Borrowed());
        book.setBorrower(user);
        book.setBorrowedTimestamp(System.currentTimeMillis());
        return true; // Book borrowed successfully
    } else {
        return false; // Book not available or already borrowed
    }
  }
}
