package nitrr.ecell.e_cell.sign_up.presenter;

public interface SignUpData {
    void sendData(String userName, String password, String phone);

    void getUserData();
}
