package com.edwinacubillos.daggerlogin.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edwinacubillos.daggerlogin.R;
import com.edwinacubillos.daggerlogin.http.TwitchAPI;
import com.edwinacubillos.daggerlogin.http.streams.Datum;
import com.edwinacubillos.daggerlogin.http.streams.Streams;
import com.edwinacubillos.daggerlogin.http.twitch.Game;
import com.edwinacubillos.daggerlogin.http.twitch.Twitch;
import com.edwinacubillos.daggerlogin.root.App;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View {

    @Inject
    LoginActivityMVP.Presenter presenter;

    @Inject
    TwitchAPI twitchAPI;

    private String TWITCH_KEY="g0t3ju4q8e265peeuad9j7fklrcxtz";

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
/*
        //Ejemplo de uso de la api de twitch con retrofit
        Call<Twitch> call = twitchAPI.getTopGames("g0t3ju4q8e265peeuad9j7fklrcxtz");

        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Game> topGames = response.body().getGame();
                for (Game game: topGames){
                    System.out.println(game.getName());
                }
            }

            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });*/

        twitchAPI.getStreamsObservable(TWITCH_KEY)
                .flatMap((Function<Streams, Observable<Datum>>) streams ->
                        Observable.fromIterable(streams.getData()))
                .filter (datum -> datum.getLanguage().equals("en") && datum.getViewerCount()>10)
                .flatMap((Function<Datum, ObservableSource<Twitch>>) datum ->
                        twitchAPI.getGamesObservable(TWITCH_KEY, datum.getGameId()))
                .flatMap((Function<Twitch, Observable<Game>>) twitch -> Observable.fromIterable(twitch.getGame()))
                .flatMap((Function<Game, Observable<String>>) game -> Observable.just(game.getName()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println("Streams RxJava says: " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        twitchAPI.getTopGamesObservable("g0t3ju4q8e265peeuad9j7fklrcxtz")
                .flatMap((Function<Twitch, Observable<Game>>) twitch ->
                        Observable.fromIterable(twitch.getGame()))
                /*  .flatMap(new Function<Game, Observable<String>>() { //Sin lambda Functions
                      @Override
                      public Observable<String> apply(Game game) {
                          return Observable.just(game.getName());
                      }
                  })
                 .filter(new Predicate<String>() {
                      @Override
                      public boolean test(String s){
                          return s.contains("W");
                      }
                  })*/
                .flatMap((Function<Game, Observable<String>>) game -> Observable.just(game.getName()))
                .filter(s -> s.contains("W"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String name) {
                        System.out.println("Twitch RxJava says: " + name);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
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
