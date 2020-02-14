package com.edwinacubillos.daggerlogin.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edwinacubillos.daggerlogin.R;
import com.edwinacubillos.daggerlogin.root.App;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    @Inject
    LoginActivityMVP.Presenter presenter;

    EditText firstName, lastName;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((App) getApplication()).getComponent().inject(this);

        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        loginButton = findViewById(R.id.bt_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginButtonClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
       // presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return this.firstName.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName.setText(firstName);
    }

    @Override
    public String getLastName() {
        return this.lastName.getText().toString();
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName.setText(lastName);
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "Error, el usuario no esta disponible", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this, "Error, el nombre ni el apellido pueden estar vacios", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSaved() {
        Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();
    }
}
