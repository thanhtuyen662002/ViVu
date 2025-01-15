package com.vothanhtuyen.vivu_backend.sevices.account;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsImpl extends User {
    private final Long accountId;

    public UserDetailsImpl(Long accountId, String email, Set<GrantedAuthority> authorities) {
        super(email, "", authorities);
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }
}
