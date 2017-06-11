package joji.dolver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    Button signIn;
    EditText email;
    EditText password;
    ProgressDialog progressDialog;

    //instance of Firebase Auth
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initialize Firebase auth
        mAuth = FirebaseAuth.getInstance();

        //initialize UI attributes
        signIn = (Button)findViewById(R.id.buttonSignUp);
        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        progressDialog = new ProgressDialog(this);

    }

    protected void signUpWithEmail(View v){
        String eml = (String) email.getText().toString();
        String pswd = (String) password.getText().toString();

        if(TextUtils.isEmpty(eml)){
            Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(pswd)){
            Toast.makeText(this,"Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        //start progress dialog
        progressDialog.setMessage("loading, please wait");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(eml,pswd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //dismiss progess dialog
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "signed in", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this,"failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    protected void startLoginActivity(View view){
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}