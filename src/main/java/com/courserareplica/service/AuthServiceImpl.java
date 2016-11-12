package com.courserareplica.service;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * The service handles all the user requests that are supported by the application
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static Client client;
    private static com.stormpath.sdk.application.Application stormpathApplication;

    @Override
    public Account createUserAccount(String name, String email, String password) {
        com.stormpath.sdk.application.Application application = getCurrentClientInstance()
                .getResource("https://api.stormpath.com/v1/applications/2QaziTvNf6JPRKpnE3FQ8a",
                        com.stormpath.sdk.application.Application.class);

        Account account = getCurrentClientInstance().instantiate(Account.class);
        account
                .setGivenName(name)
                .setSurname(name)
                .setUsername(name)
                .setEmail(email)
                .setPassword(password)
                .getCustomData().put("role", "admin");
        account = application.createAccount(account);

        return account;
    }

    @Override
    public Account getUserAccount(String email, String password) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("email", email);
        queryParams.put("username", email);
        AccountList accounts = getCurrentStormpathApplicationInstance()
                .getAccounts(queryParams);

        // warning: will throw error if 0 or more accounts are found
        return accounts.single();
    }

    private static com.stormpath.sdk.application.Application getCurrentStormpathApplicationInstance() {
        if (stormpathApplication == null) {
            stormpathApplication = getCurrentClientInstance()
                    .getResource("https://api.stormpath.com/v1/applications/2QaziTvNf6JPRKpnE3FQ8a",
                            com.stormpath.sdk.application.Application.class);
        }

        return stormpathApplication;
    }

    private static Client getCurrentClientInstance() {
        if (client == null) {
            client = Clients.builder().build();
        }

        return client;
    }
}
