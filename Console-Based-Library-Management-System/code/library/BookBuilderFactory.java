package library;

public class BookBuilderFactory {
    public static BookBuilder createBookBuilder(String type) {
        if ("fiction".equalsIgnoreCase(type)) {
            return new FictionBookBuilder();
        } else if ("nonfiction".equalsIgnoreCase(type)) {
            return new NonFictionBookBuilder();
        } else {
            throw new IllegalArgumentException("Invalid book type: " + type);
        }
    }
}