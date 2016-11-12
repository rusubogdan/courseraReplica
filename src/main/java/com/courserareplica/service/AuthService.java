package com.courserareplica.service;

import com.stormpath.sdk.account.Account;

public interface AuthService {

    Account createUserAccount(String name, String email, String password);

    Account getUserAccount(String email, String password);
}
