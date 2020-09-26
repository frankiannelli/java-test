package library.entities;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    void testBorrowFromLibraryThrowsWhenOnLoan() {
        IBook.BookState state = IBook.BookState.ON_LOAN;
        book = new Book(author, title, callNo, id, state);
        try {
            book.borrowFromLibrary();
            fail("Should have thrown exception but did not!");
        } catch (final RuntimeException e) {
            final String msg = "Book: cannot borrow while book is in state: ON_LOAN";
            assertEquals(msg, e.getMessage());
        }
    }
}