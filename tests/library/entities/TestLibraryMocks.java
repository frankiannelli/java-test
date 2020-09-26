package library.entities;

import library.entities.helpers.BookHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestLibraryMocks {
    ILibrary library;

    @Mock IPatron mockPatron;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        library = new Library(new BookHelper(), new PatronHelper(), new LoanHelper());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testPatronCanBorrowWhenAtLoanLimit() {
        when(mockPatron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT);
        boolean patronCanBorrow = library.patronCanBorrow(mockPatron);
        assertFalse(patronCanBorrow);
    }
}