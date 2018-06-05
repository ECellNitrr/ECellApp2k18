package nitrr.ecell.e_cell.sign_up.model;

public class User {
    private String token, userName, userPassword, userPhone;

    public User(String token, String userName, String userPassword, String userPhone) {
        this.token = token;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPhone = userPhone;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getToken() {
        return token;
    }
}
