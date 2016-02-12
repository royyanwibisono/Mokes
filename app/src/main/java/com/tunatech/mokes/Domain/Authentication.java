package com.tunatech.mokes.Domain;

import java.io.Serializable;

/**
 * Created by royyan on 2/6/2016.
 */
@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class Authentication implements Serializable {
    private String _email,_username, _saldo;

    public Authentication(String username){
        _username = username;
        _email = "";
        _saldo = "Rp 0,-";
    }

    public String get_saldo() {
        return _saldo;
    }

    public void set_saldo(String _saldo) {
        this._saldo = _saldo;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

}
