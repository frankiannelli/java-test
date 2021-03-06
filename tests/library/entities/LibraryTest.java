package library.entities;

import library.entities.helpers.BookHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// this testing method uses Stubs
// although some of these tests are done with mocks I
// have left them here for illustration
class LibraryTest {
    ILibrary library;

    @BeforeEach
    void setUp() {
        library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testPatronCanBorrowWhenAtLoanLimit() {
        IPatron patronStub = new PatronStub1();
        boolean patronCanBorrow = library.patronCanBorrow((patronStub));
        assertFalse(patronCanBorrow);
    }

    @Test
    void testPatronCantBorrowWhenoverMaxFines() {
        IPatron patronStub = new PatronStub2();
        boolean patronCanBorrow = library.patronCanBorrow((patronStub));
        assertFalse(patronCanBorrow);
    }
}