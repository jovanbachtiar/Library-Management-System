package library;

import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        while (true) {
            System.out.println("\nWelcome to the Library Management System");
            System.out.println("1. Register a new user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner, dbConnection);
                    break;
                case 2:
                    loginUser(scanner, dbConnection);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner, DatabaseConnection dbConnection) {
        System.out.print("Enter user type (Student/Faculty): ");
        String userType = scanner.nextLine();

        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNum = scanner.nextLine().replace("-", "").replace(" ", "");

        // Using Factory Method generate new user
        User newUser = UserFactory.createUser(userType, userName, phoneNum);

        // Using Singleton DatabaseConnection instance generate new user
        dbConnection.addUser(newUser);

        System.out.println("User successfully registered:");
        newUser.displayInfo();
    }

    private static void loginUser(Scanner scanner, DatabaseConnection dbConnection) {
        System.out.print("Enter user name for login: ");
        String userName = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phoneNum = scanner.nextLine().replace("-", "").replace(" ", "");

        User user = dbConnection.getUser(userName);

        if (user != null && user.getPhoneNum().equals(phoneNum)) {
            System.out.println("Login successful:");
            user.displayInfo();
            manageBooks(scanner, dbConnection, user);
        } else {
            System.out.println("Login failed: User not found or phone number does not match.");
        }
    }

    private static void manageBooks(Scanner scanner, DatabaseConnection dbConnection, User user) {
        if(user instanceof Student){
            // Student can only borrow books
            manageBorrowBooks(scanner, dbConnection, user);
        }else if (user instanceof Faculty){
            manageAddRemoveBooks(scanner, dbConnection);
        }else{
            System.out.println("Invalid user type.");
        }
    }

    private static void manageBorrowBooks(Scanner scanner, DatabaseConnection dbConnection, User user) {
        // Display all available books
        dbConnection.displayBooks();
        while (true) {
            System.out.println("\n1. Borrow Book");
            System.out.println("2. Check Book");
            System.out.println("3. Return Books");
            System.out.println("4. Check the Borrowed Time");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    borrowBook(scanner, dbConnection, user);
                    break;
                case 2:
                    checkAvailability(scanner, dbConnection);
                    break;
                case 3:
                    returnBook(scanner, dbConnection, user);
                    break;
                case 4:
                    checkBorrowedTime(scanner,dbConnection);
                    break;
                case 5:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void borrowBook(Scanner scanner, DatabaseConnection dbConnection, User user) {
        System.out.print("Enter the title of the book you want to borrow: ");
        String title = scanner.nextLine();
        
        // Assuming you have a method in DatabaseConnection to borrow a book
        if (dbConnection.borrowBook(title, user)) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book not available or already borrowed.");
        }
    }

    private static void checkAvailability(Scanner scanner, DatabaseConnection dbConnection) {
        System.out.print("Enter the title of the book you want to check: ");
        String title = scanner.nextLine();
        
        // Assuming you have a method in DatabaseConnection to check book availability
        if (dbConnection.checkBookAvailability(title)) {
            System.out.println("The book is available.");
        } else {
            System.out.println("The book is not available.");
        }
    }
    
    private static void returnBook(Scanner scanner, DatabaseConnection dbConnection, User user) {
        System.out.print("Enter the title of the book you want to return: ");
        String title = scanner.nextLine();
        
        // Assuming you have a method in DatabaseConnection to return a book
        if (dbConnection.returnBook(title, user)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found or not borrowed by the user.");
        }
    }

    private static void checkBorrowedTime(Scanner scanner, DatabaseConnection dbConnection, User user) {
        // Retrieve the list of books borrowed by the user
        List<Book> borrowedBooks = dbConnection.getBorrowedBooks(user);

        if (borrowedBooks.isEmpty()) {
            System.out.println("You have not borrowed any books.");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                long borrowedTimeSeconds = book.getSecondsBorrowed();
                System.out.println("Book: " + book.getTitle() + ", Borrowed Time: " + borrowedTimeSeconds + " seconds");
            }
        }
    }

    private static void manageAddRemoveBooks(Scanner scanner, DatabaseConnection dbConnection) {
        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View Books");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    addBook(scanner, dbConnection);
                    break;
                case 2:
                    removeBook(scanner, dbConnection);
                    break;
                case 3:
                    dbConnection.displayBooks();
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addBook(Scanner scanner, DatabaseConnection dbConnection) {
        // Prompt the user for input
        System.out.print("Enter book type (fiction/nonfiction): ");
        String bookType = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        System.out.print("Enter publication date: ");
        String publicationDate = scanner.nextLine();

        // Create a book builder using the Factory pattern
        BookBuilder bookBuilder = BookBuilderFactory.createBookBuilder(bookType);

        // Set attributes of the book using the builder pattern
        Book book = bookBuilder
                    .setTitle(title)
                    .setAuthor(author)
                    .setPublicationDate(publicationDate)
                    .build();

        // Add the constructed book to the database connection
        dbConnection.addBook(book);

        System.out.println("Book added successfully.");
    }
    
    private static void removeBook(Scanner scanner, DatabaseConnection dbConnection) {
        System.out.print("Enter book title to remove: ");
        String titleToRemove = scanner.nextLine();
        if (dbConnection.removeBook(titleToRemove)) {
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }
    
}