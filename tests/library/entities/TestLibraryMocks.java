package library.entities;

import library.entities.helpers.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestLibraryMocks {
    ILibrary library;

    @Mock
    IPatron mockPatron;
    @Mock
    IBook mockBook;
    @Mock
    ILoan mockLoan;
    @Mock
    IBookHelper mockBookhelper;
    @Mock
    IPatronHelper mockPatronHelper;
    @Mock
    ILoanHelper mockLoanHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        library = new Library(mockBookhelper, mockPatronHelper, mockLoanHelper);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testPatronCanBorrowWhenPatronValid() {
        when(mockPatron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT - 1);
        when(mockPatron.getFinesPayable()).thenReturn(0.0);
        when(mockPatron.hasOverDueLoans()).thenReturn(false);
        boolean patronCanBorrow = library.patronCanBorrow(mockPatron);
        assertTrue(patronCanBorrow);
    }

    @Test
    void testPatronCanNotBorrowWhenAtLoanLimit() {
        when(mockPatron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT);
        when(mockPatron.getFinesPayable()).thenReturn(0.0);
        when(mockPatron.hasOverDueLoans()).thenReturn(false);
        boolean patronCanBorrow = library.patronCanBorrow(mockPatron);
        assertFalse(patronCanBorrow);
    }

    @Test
    void testPatronCanNotBorrowWhenHasFines() {
        when(mockPatron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT - 1);
        when(mockPatron.getFinesPayable()).thenReturn(ILibrary.MAX_FINES_OWED + 0.01);
        when(mockPatron.hasOverDueLoans()).thenReturn(false);
        boolean patronCanBorrow = library.patronCanBorrow(mockPatron);
        assertFalse(patronCanBorrow);
    }

    @Test
    void testPatronCanNotBorrowWhenHasOverdueLoans() {
        when(mockPatron.getNumberOfCurrentLoans()).thenReturn(ILibrary.LOAN_LIMIT - 1);
        when(mockPatron.getFinesPayable()).thenReturn(ILibrary.MAX_FINES_OWED - 0.01);
        when(mockPatron.hasOverDueLoans()).thenReturn(true);
        boolean patronCanBorrow = library.patronCanBorrow(mockPatron);
        assertFalse(patronCanBorrow);
    }

    @Test
    void testIssueLoanReturnsValidLoan() {
        ILoan loan = library.issueLoan(mockBook, mockPatron);
        assertTrue(loan instanceof ILoan);
    }

    // I wanted to test that library.loans is valid
    // but there is no getter method so that i can check it
    @Test
    void testCommitLoanSetsBorrowingRestrictions() {
        library.commitLoan(mockLoan);
        List<ILoan> currentLoans = library.getCurrentLoansList();
    }
}