package library.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    IBook book;
    String author = "Frank";
    String title = "Become a Java dev in 2 weeks";
    String callNo = "c123";
    int id = 123;

    @BeforeEach
    void setUp() {
        book = new Book(author, title, callNo, id);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAvailableTrueWhenAvailable() {
        boolean isAvailable = book.isAvailable();
        assertTrue(isAvailable);
    }

    @Test
    void testAvailableFalseWhenOnLoan() {
        IBook.BookState state = IBook.BookState.ON_LOAN;
        book = new Book(author, title, callNo, id, state);
        boolean isAvailable = book.isAvailable();
        assertFalse(isAvailable);
    }

    @Test
    void testAvailableFalseWhenDamaged() {
        IBook.BookState state = IBook.BookState.DAMAGED;
        book = new Book(author, title, callNo, id, state);
        boolean isAvailable = book.isAvailable();
        assertFalse(isAvailable);
    }

    @Test
    void testBorrowFromLibraryUpdatesStateWhenAvailable() {
        book.borrowFromLibrary();
        boolean isAvailable = book.isAvailable();
        assertFalse(isAvailable);
    }

    @Test
    void testBorrowFromLibraryThrowsWhenOnLoan() {
        IBook.BookState state = IBook.BookState.ON_LOAN;
        book = new Book(author, title, callNo, id, state);
        Executable e = () ->  book.borrowFromLibrary();
        Throwable t = assertThrows(RuntimeException.class, e);
        final String msg = "Book: cannot borrow while book is in state: ON_LOAN";
        assertEquals(msg, t.getMessage());
    }
}