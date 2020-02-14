package com.edwinacubillos.daggerlogin.root;

import com.edwinacubillos.daggerlogin.TaskActivity;
import com.edwinacubillos.daggerlogin.http.TwitchModule;
import com.edwinacubillos.daggerlogin.login.LoginActivity;
import com.edwinacubillos.daggerlogin.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class, TwitchModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);
    void inject(TaskActivity target);

}

