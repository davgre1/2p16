package com.example.david.a2p16;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    public final static String EXTRA_Name = "com.example.david.a2p16.Name";
    public final static String EXTRA_Email = "com.example.david.a2p16.Email";

    String name, email;
    String image;

    private SignInButton sIgnInButton; //Signing button
    private GoogleSignInOptions googleSignInOptions; //Signing Options
    private GoogleApiClient mGoogleApiClient; //google api client
    private int RC_SIGN_IN = 100; //Signing constant to check the activity result

    //TextViews
    //private TextView textViewName;
    //private TextView textViewEmail;
    private NetworkImageView profileImage;
    //Image Loader
    private ImageLoader imageLoader;
    private ProgressDialog mConnectionProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        //textViewName = (TextView) findViewById(R.id.textViewName);
        //textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        //profileImage = (NetworkImageView) findViewById(R.id.profileImage);

        mConnectionProgressDialog = new ProgressDialog(this);
        mConnectionProgressDialog.setMessage("Signing in...");

        //initializing google signing option
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signInbutton
        sIgnInButton = (SignInButton) findViewById(R.id.sign_in_button);
        sIgnInButton.setSize(SignInButton.SIZE_ICON_ONLY);
        sIgnInButton.setScopes(googleSignInOptions.getScopeArray()); //get an array of al the requested scopes

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this/*FragmentActivity*/, this /*OnConnectionFailedListner*/)
                .addApi (Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        //Setting onClick listner to signing button
        sIgnInButton.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signing
            handleSignInResult(result);
        }
    }

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {

            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            //Displaying name and email
            name  = new String(acct.getDisplayName());
            email  = new String(acct.getEmail());
            //textViewName.setText(acct.getDisplayName());
            //textViewEmail.setText(acct.getEmail());

//            //Initializing image loader
//            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
//                    .getImageLoader();
//
//            imageLoader.get(acct.getPhotoUrl().toString(),
//                    ImageLoader.getImageListener(profileImage,
//                            R.mipmap.ic_launcher,
//                            R.mipmap.ic_launcher));
//            //Loading image
//            profileImage.setImageUrl(acct.getPhotoUrl().toString(), imageLoader);

            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra(EXTRA_Name, name.toString());
            intent.putExtra(EXTRA_Email, email.toString());
            startActivity(intent);



        } else { Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show(); }
    }

    @Override
    public void onClick(View view) {
        if (view == sIgnInButton) {
            //mConnectionProgressDialog.show();
            //Creating an intent
            Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            //Starting intent for result
            startActivityForResult(signIntent, RC_SIGN_IN);
            //mConnectionProgressDialog.dismiss();
        }
        if(mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //do nothing
    }

}