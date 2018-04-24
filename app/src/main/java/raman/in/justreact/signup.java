package raman.in.justreact;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static java.security.AccessController.getContext;

public class signup extends AppCompatActivity {

    private EditText username,password,confirmpassword;
    private Button signup;
    private DatabaseReference mDatabase,mydatabase;
    private ProgressDialog mProgress;
    private String usernameinput,passwordinput,confirmpasswordinput;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private Animation in,out,in1;
    private int usercount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1100);
        in1 = new AlphaAnimation(0.0f, 1.0f);
        in1.setDuration(1400);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(300);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        signup = (Button) findViewById(R.id.signupbutton);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mydatabase = FirebaseDatabase.getInstance().getReference();

        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Please wait while we register you as a user");
        mProgress.setCanceledOnTouchOutside(false);

        editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        mydatabase.child("Total Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usercount=dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgress.show();
                signup.startAnimation(in);

                if(!isNetworkAvailable()) {
                    Toast.makeText(getApplicationContext(), "No Internet access!", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
                    return;
                }

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        confirmpassword.getWindowToken(), 0);
                usernameinput=username.getText().toString().trim();
                passwordinput=password.getText().toString().trim();
                confirmpasswordinput=confirmpassword.getText().toString().trim();

               if(usernameinput.equals("")||passwordinput.equals("")||confirmpasswordinput.equals("")) {
                   Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                    mProgress.dismiss();
               }
                else {
                   mProgress.show();
                   mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {



                           if(dataSnapshot.hasChild(usernameinput)) {
                               Toast.makeText(getApplicationContext(), "Username already exist!", Toast.LENGTH_SHORT).show();
                                password.setText("");
                               confirmpassword.setText("");
                               mProgress.dismiss();
                           }
                           else
                           {

                               if(!(passwordinput.equals(confirmpasswordinput))) {
                                   Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                                    mProgress.dismiss();
                               }
                               else{

                                   mDatabase.child(usernameinput).child("password")
                                           .setValue(passwordinput).addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {

                                           mDatabase.child(usernameinput).child("views")
                                                   .setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void aVoid) {

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("1").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("1").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("1").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("1").child("d").setValue(1);


                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("2").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("2").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("2").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("2").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("3").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("3").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("3").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("3").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("4").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("4").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("4").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("4").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("5").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("5").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("5").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("5").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("6").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("6").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("6").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("6").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("7").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("7").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("7").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("7").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("8").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("8").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("8").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("8").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("9").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("9").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("9").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("9").child("d").setValue(1);

                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("10").child("a").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("10").child("b").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("10").child("c").setValue(1);
                                                   mDatabase.child(usernameinput).child("questions")
                                                           .child("10").child("d").setValue(1);

                                                   String ma=getMA();
                                                   mDatabase.child(usernameinput).child("mac").setValue(ma);

                                                   mDatabase.child(usernameinput).child("emoji").child("kiss").setValue(0);
                                                   mDatabase.child(usernameinput).child("emoji").child("smart").setValue(0);
                                                   mDatabase.child(usernameinput).child("emoji").child("poop").setValue(0);
                                                   mDatabase.child(usernameinput).child("emoji").child("lit").setValue(1);

                                                   usercount=usercount+1;
                                                   mydatabase.child("Total Users").setValue(usercount).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                       @Override
                                                       public void onSuccess(Void aVoid) {

                                                           mProgress.dismiss();
                                                           Intent i=new Intent(signup.this, userhome.class);
                                                           i.putExtra("username",usernameinput);
                                                           i.setFlags(FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                           editor.putString("username",usernameinput);
                                                           editor.commit();
                                                           Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                                                                   android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
                                                           startActivity(i,bundle);
                                                           finish();


                                                       }
                                                   });

                                               }
                                           });

                                       }
                                   });

                               }
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

    public static String getMA() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

}
