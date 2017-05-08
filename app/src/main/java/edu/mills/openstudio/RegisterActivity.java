package edu.mills.openstudio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * User registration page.
 */
public class RegisterActivity extends Activity {
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputFullName = (EditText) findViewById(R.id.name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        db = new SQLiteHandler(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    register(name, email, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.complete_all_fields, Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void register(String name, String email, String password){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiInterface.register(name, email, password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                if (!response.body().getError()) {
                    User user = response.body().getUser();
                    db.addUser(user.getName(), user.getEmail(), response.body().getUid(), user.getCreatedAt());
                    Toast.makeText(getApplicationContext(), R.string.register_success, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getErrorMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.register_fail, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                //Code to run when the about item is clicked
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.action_settings:
                //Code to run when the settings item is clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
