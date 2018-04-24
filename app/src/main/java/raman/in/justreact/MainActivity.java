package raman.in.justreact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private Button signup,login;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private EditText searchbox;
    private String input;
    private Animation in,out;
    private DatabaseReference mDatabase;
    private Button searchbutton;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = (Button) findViewById(R.id.signupBtn);
        login = (Button) findViewById(R.id.loginBtn);
        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        searchbox = (EditText) findViewById(R.id.searchbox);
        searchbutton = (Button) findViewById(R.id.searchBtn);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("users");

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(700);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Searching...");
        mProgress.setCanceledOnTouchOutside(false);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup.startAnimation(in);
                Intent i=new Intent(MainActivity.this, raman.in.justreact.signup.class);

                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                startActivity(i, bundle);


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!prefs.getString("username","").equals("")){
                    Intent i1=new Intent(MainActivity.this, userhome.class);
                    i1.putExtra("username",prefs.getString("username",""));
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(i1,bundle);

                }
                else {

                    Intent i = new Intent(MainActivity.this, Login.class);
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                            android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                    startActivity(i, bundle);

                }
            }
        });


        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgress.show();
                searchbutton.startAnimation(in);
                if(!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(),"No Internet access!", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    return;
                }

                InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        searchbox.getWindowToken(), 0);

                input = searchbox.getText().toString().trim();

                if(input.equals("")){
                    mProgress.dismiss();
                }
                if(prefs.getString("username","").equals(input)&&!input.equals("")) {
                    Toast.makeText(getApplicationContext(), "You can't answer for yourself!", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                }
                else if (!input.equals("")) {
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.hasChild(input)) {

                                mProgress.show();
                                mDatabase.child(input).child("views").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        int currViews=dataSnapshot.getValue(Integer.class);
                                        currViews++;
                                        mDatabase.child(input).child("views").setValue(currViews).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                mProgress.dismiss();
                                                searchbox.setText("");
                                                Intent i = new Intent(MainActivity.this, questions.class);
                                                i.putExtra("searched",input);
                                                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                        android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                                startActivity(i, bundle);

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            } else {
                                Toast.makeText(getApplicationContext(), "User id is invalid!", Toast.LENGTH_SHORT).show();
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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
