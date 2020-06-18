package com.example.xmum_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button callBack, callRegister, callLogin;
    ImageView image;
    TextView text;
    Spinner roleSpinner;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ID = "id";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_ROLE = "role";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_EMAIL_ADDRESS = "email_address";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText etId;
    private EditText etFullName;
    private EditText etPhoneNumber;
    private EditText etEmailAddress;
    private EditText etPassword;
    private String id;
    private String fullName;
    private String role;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String register_url = "http://127.0.0.1/xmum_app_server/register.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_registration);
        getWindow().setEnterTransition(null);
        getWindow().setExitTransition(null);

        roleSpinner = findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setOnItemSelectedListener(this);

        callBack = findViewById(R.id.back);
        callRegister = findViewById(R.id.register);
        callLogin = findViewById(R.id.sign_in);

        etId = findViewById(R.id.id_text_input);
        etFullName = findViewById(R.id.name_text_input);
        etPhoneNumber = findViewById(R.id.phone_number_text_input);
        etEmailAddress = findViewById(R.id.email_text_input);
        etPassword = findViewById(R.id.password_text_input);

        image = findViewById(R.id.logo);
        text = findViewById(R.id.slogan);

        callBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        callRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = etId.getText().toString().toLowerCase().trim();
                fullName = etFullName.getText().toString().trim();
                role = roleSpinner.getSelectedItem().toString().trim();
                phoneNumber = etPhoneNumber.getText().toString().trim();
                emailAddress = etEmailAddress.getText().toString().trim();
                password = etPassword.getText().toString().trim();

                if (validateInputs()) {
                    registerUser();
                }
            }
        });

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, LoginSecondary.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "xmum_logo_transition");
                pairs[1] = new Pair<View, String>(text, "xmum_slogan_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Registration.this, pairs);

                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateInputs() {
        if (KEY_EMPTY.equals(id)) {
            etId.setError("ID cannot be empty");
            etId.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("Name cannot be empty");
            etFullName.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(phoneNumber)) {
            etPhoneNumber.setError("Phone number cannot be empty");
            etPhoneNumber.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(emailAddress)) {
            etEmailAddress.setError("E-mail cannot be empty");
            etEmailAddress.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    private void registerUser() {
        final LoadingDialog loadingDialog = new LoadingDialog(Registration.this);
        loadingDialog.startLoadingDialog();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_ID, id);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_EMAIL_ADDRESS, emailAddress);
            request.put(KEY_PHONE_NUMBER, phoneNumber);
            request.put(KEY_ROLE, role);
            request.put(KEY_FULL_NAME, fullName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismissLoadingDialog();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                session.loginUser(id,fullName);

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etId.setError("This ID already had an account.");
                                etId.requestFocus();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissLoadingDialog();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}