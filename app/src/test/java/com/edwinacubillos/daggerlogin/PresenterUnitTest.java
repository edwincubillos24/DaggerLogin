package com.edwinacubillos.daggerlogin;

import com.edwinacubillos.daggerlogin.login.LoginActivityMVP;
import com.edwinacubillos.daggerlogin.login.LoginActivityPresenter;
import com.edwinacubillos.daggerlogin.login.User;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresenterUnitTest {

    private LoginActivityPresenter presenter;
    private User user;

    private LoginActivityMVP.Model mockedModel;
    private LoginActivityMVP.View mockedView;


    @Before
    public void initialization() {
        mockedModel = mock(LoginActivityMVP.Model.class);
        mockedView = mock(LoginActivityMVP.View.class);

        user = new User("Manolo", "Escobar");

        //    when(mockedModel.getUser()).thenReturn(user);

        //   when(mockedView.getFirstName()).thenReturn("Antonio");
        //   when(mockedView.getLastName()).thenReturn("Banderas");

        presenter = new LoginActivityPresenter(mockedModel);
        presenter.setView(mockedView);
    }

    @Test
    public void noExistsInteractionWithView() {
        presenter.getCurrentUser();
        verify(mockedView, times(1)).showUserNotAvailable();
    }

    @Test
    public void loadUserFromTheRepoWhenValidUserIsPresent() {
        when(mockedModel.getUser()).thenReturn(user);
        presenter.getCurrentUser();

        //interaccion con modelo de datos
        verify(mockedModel, times(1)).getUser();

        //interacción con la vista
        verify(mockedView, times(1)).setFirstName("Manolo");
        verify(mockedView, times(1)).setLastName("Escobar");
        verify(mockedView, never()).showUserNotAvailable();
    }

    @Test
    public void showErrorMessageWhenUserIsNull() {
        when(mockedModel.getUser()).thenReturn(null);
        presenter.getCurrentUser();
        verify(mockedModel, times(1)).getUser();
        verify(mockedView, times(1)).showUserNotAvailable();
    }

    @Test
    public void createErrorMessageIfAnyFieldIsEmpty(){
        //primera prueba poniendo el campo firstname vacio
        when(mockedView.getFirstName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockedView,times(1)).getFirstName();
        verify(mockedView,never()).getLastName(); //no se llama por la or dentro del if no llega hasta esta instrucción
        verify(mockedView,times(1)).showInputError();

        //segunda prueba poniendo el lastname vacio
        when(mockedView.getFirstName()).thenReturn("Antonio");
        when(mockedView.getLastName()).thenReturn("");

        presenter.loginButtonClicked();

        verify(mockedView, times(2)).getFirstName(); //son dos porque es acumulativa 1 prueba anterior y otra actual
        verify(mockedView, times(1)).getLastName();
        verify(mockedView, times(2)).showInputError(); //el metodo se llamo antes y de nuevo ahora
    }

    @Test
    public void saveValidUser(){
        when(mockedView.getFirstName()).thenReturn("Antonio");
        when(mockedView.getLastName()).thenReturn("Banderas");

        presenter.loginButtonClicked();

        verify(mockedView, times(2)).getFirstName();
        verify(mockedView, times(2)).getLastName();

        verify(mockedModel, times(1)).createUser("Antonio", "Banderas");
        verify(mockedView,times(1)).showUserSaved();

    }
}
