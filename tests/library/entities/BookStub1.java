package library.entities;

public class BookStub1 implements IBook {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean isOnLoan() {
        return false;
    }

    @Override
    public boolean isDamaged() {
        return false;
    }

    @Override
    public void borrowFromLibrary() {

    }

    @Override
    public void returnToLibrary(boolean isDamaged) {

    }

    @Override
    public void repair() {

    }
}
