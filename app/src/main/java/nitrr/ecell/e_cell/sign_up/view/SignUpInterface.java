package nitrr.ecell.e_cell.sign_up.view;

public interface SignUpInterface {
    void showProgressBar(boolean show);

    boolean checkNetwork();

    void showErrorMessage(String error);

}
