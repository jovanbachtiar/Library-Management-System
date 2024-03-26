package library;

public interface BookStatus {
  void check(Book book);
  void borrow(Book book);
  void returnBook(Book book);
}