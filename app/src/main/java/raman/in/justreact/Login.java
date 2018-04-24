package raman.in.justreact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class Login extends AppCompatActivity {


    private EditText username,password;
    private Button loginBtn1;
    String usernameinput,passwordinput;
    private DatabaseReference mDatabase,mcurrentuser;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private ProgressDialog mProgress;
    private Animation in,out,in1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginBtn1 = (Button) findViewById(R.id.loginBtn1);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1100);
        in1 = new AlphaAnimation(0.0f, 1.0f);
        in1.setDuration(1400);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Please wait");
        mProgress.setCanceledOnTouchOutside(false);

        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        loginBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginBtn1.startAnimation(in);
                mProgress.show();

                if(!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "No Internet access!", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    return;
                }

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        password.getWindowToken(), 0);

                usernameinput = username.getText().toString().trim();
                passwordinput = password.getText().toString().trim();

                if(usernameinput.equals("")||passwordinput.equals("")) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }
                else{

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(usernameinput)){

                                mcurrentuser=mDatabase.child(usernameinput).child("password");
                                mcurrentuser.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot1) {
                                        if(dataSnapshot1.getValue(String.class).equals(passwordinput)){
                                            Intent i=new Intent(Login.this, userhome.class);
                                            i.putExtra("username",usernameinput);
                                            i.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            editor.putString("username",usernameinput);
                                            editor.commit();
                                            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                            startActivity(i,bundle);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();
                                            password.setText("");
                                            mProgress.dismiss();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Invalid username!", Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        });


    }

    @Override public void onBackPressed() { super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
