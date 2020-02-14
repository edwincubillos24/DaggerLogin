package com.edwinacubillos.daggerlogin.root;

import com.edwinacubillos.daggerlogin.login.LoginActivity;
import com.edwinacubillos.daggerlogin.login.LoginModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, LoginModule.class})
public interface ApplicationComponent {

    void inject(LoginActivity target);

}
