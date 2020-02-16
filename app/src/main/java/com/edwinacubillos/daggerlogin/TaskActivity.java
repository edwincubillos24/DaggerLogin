package com.edwinacubillos.daggerlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edwinacubillos.daggerlogin.http.TwitchAPI;
import com.edwinacubillos.daggerlogin.http.twitch.Twitch;
import com.edwinacubillos.daggerlogin.root.App;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {

    @Inject
    TwitchAPI twitchAPI;

    private EditText nameGame;
    private Button searchButton;
    private TextView idGame, boxGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        ((App) getApplication()).getComponent().inject(this);

        nameGame = findViewById(R.id.et_name);
        searchButton = findViewById(R.id.bt_search);
        idGame = findViewById(R.id.tv_id);
        boxGame = findViewById(R.id.tv_box);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Twitch> call = twitchAPI.getGames("g0t3ju4q8e265peeuad9j7fklrcxtz",nameGame.getText().toString());

                call.enqueue(new Callback<Twitch>() {
                    @Override
                    public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                        idGame.setText(response.body().getGame().get(0).getId());
                        boxGame.setText(response.body().getGame().get(0).getBoxArtUrl());
                    }

                    @Override
                    public void onFailure(Call<Twitch> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });
    }
}
