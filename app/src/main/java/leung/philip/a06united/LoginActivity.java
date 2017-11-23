package leung.philip.a06united;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;
    private Button mLoginBtn;

    private Toolbar mToolbar;

    private ProgressDialog mLoginProgress;

    private FirebaseAuth mAuth;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //firebase connection
        mAuth = FirebaseAuth.getInstance();

        //input from email and password
        mLoginEmail = (TextInputLayout) findViewById(R.id.login_Email);
        mLoginPassword = (TextInputLayout) findViewById(R.id.login_Password);
        mLoginBtn = (Button) findViewById(R.id.login_Button);

        //toolbar setting
        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup login progress message
        mLoginProgress = new ProgressDialog(this);

        //login button click
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                //if email and password not empty
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    //show progress messaging while loading
                    mLoginProgress.setTitle("Logging in ");
                    mLoginProgress.setMessage("Please wait while we check your credentials");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    //login to firebase method
                    loginUser(email,password);

                }

            }
        });

    }

    //login firebase method
    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    mLoginProgress.dismiss();

                    Intent mainIntent = new Intent(LoginActivity.this , MainActivity.class);
                    //clear the task, prevent go back to previous page after login
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                } else{

                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this, "Cannot Sign in. Please check the email and password again.", Toast.LENGTH_LONG).show();


                }


            }
        });


    }
}
