package nitrr.ecell.e_cell.sign_up.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import nitrr.ecell.e_cell.sign_up.model.User;
import nitrr.ecell.e_cell.sign_up.view.SignUpInterface;

public class SignUpDataImp implements SignUpData {

    private SignUpInterface signUpInterface;

    private DatabaseReference userDataReference = FirebaseDatabase.getInstance().getReference("users");

    private RegisteredUserData userData = new RegisteredUserData();

    public SignUpDataImp(Activity activity) {
        this.signUpInterface = (SignUpInterface) activity;
    }

    @Override
    public void sendData(String userName, String password, String phone) {
        if (signUpInterface.isNetworkAvailable() && userData.validateUserName(userName)) {
            signUpInterface.showProgressBar(true);

            String token = userDataReference.push().getKey();
            User user = new User(token, userName, password, phone);

            assert token != null;
            userDataReference.child(token).setValue(user);
        } else {
            signUpInterface.showErrorMessage("User Name already exists. Try again.");
        }

        signUpInterface.showProgressBar(false);
    }

    @Override
    public void getUserData() {

        userDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnap : dataSnapshot.getChildren()) {
                    userData.addUser(userDataSnap.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                signUpInterface.showErrorMessage(databaseError.getMessage());
            }
        });
    }
}
