package com.hand.hcf.app.base.service;

import com.hand.hcf.app.client.user.AuthClient;
import com.hand.hcf.core.exception.core.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SSODetailService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(SSODetailService.class);
    @Autowired(required = false)
    private AuthClient authClient;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Authenticating email：{} cas start ", email);
        if (StringUtils.isEmpty(email)) {
            throw new UserNotActivatedException("email.is.empty");
        }

        return authClient.loadUserByEmail(email);
    }
}
