

package com.hand.hcf.app.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class BaseTokenService extends DefaultTokenServices {
    private boolean supportRefreshToken = false;
    private TokenStore tokenStore;
    private TokenEnhancer accessTokenEnhancer;
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void setSupportRefreshToken(boolean supportRefreshToken) {
        super.setSupportRefreshToken(supportRefreshToken);
        this.supportRefreshToken = supportRefreshToken;
    }

    @Override
    public void setTokenStore(TokenStore tokenStore) {
        super.setTokenStore(tokenStore);
        this.tokenStore = tokenStore;
    }

    @Override
    public void setTokenEnhancer(TokenEnhancer accessTokenEnhancer) {
        super.setTokenEnhancer(accessTokenEnhancer);
        this.accessTokenEnhancer = accessTokenEnhancer;
    }
    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)  {
        if (!supportRefreshToken) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
        } else {
            OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(refreshTokenValue);
            if (refreshToken == null) {
                throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
            } else {
                OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(refreshToken);
                String clientId2 = authentication.getOAuth2Request().getClientId();
                if (clientId2 != null && clientId2.equals(tokenRequest.getClientId())) {
                    if (isExpired(refreshToken)) {
                        tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
                        tokenStore.removeRefreshToken(refreshToken);
                        throw new InvalidTokenException("Invalid refresh token (expired): " + refreshToken);
                    } else {
                        OAuth2AccessToken oldAccessToken = tokenStore.getAccessToken(authentication);
                        if (oldAccessToken != null) {
                            if (!oldAccessToken.isExpired()) {
                                return oldAccessToken;
                            } else {
                                tokenStore.removeAccessTokenUsingRefreshToken(refreshToken);
                            }
                        }
                        authentication = createRefreshedAuthentication(authentication, tokenRequest);
                        OAuth2AccessToken accessToken1 = createAccessToken(authentication, refreshToken);
                        tokenStore.storeAccessToken(accessToken1, authentication);
                        return accessToken1;
                    }
                } else {
                    throw new InvalidGrantException("Wrong client for super refresh token: " + refreshTokenValue);
                }
            }
        }
    }

    private OAuth2Authentication createRefreshedAuthentication(OAuth2Authentication authentication, TokenRequest request) {
        Set scope = request.getScope();
        OAuth2Request clientAuth = authentication.getOAuth2Request().refresh(request);
        if (scope != null && !scope.isEmpty()) {
            Set originalScope = clientAuth.getScope();
            if (originalScope == null || !originalScope.containsAll(scope)) {
                throw new InvalidScopeException("Unable to narrow the scope of the client authentication to " + scope + ".", originalScope);
            }

            clientAuth = clientAuth.narrowScope(scope);
        }

        return new OAuth2Authentication(clientAuth, authentication.getUserAuthentication());
    }

    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = this.getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (long) validitySeconds * 1000L));
        }

        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());
        return this.accessTokenEnhancer != null ? this.accessTokenEnhancer.enhance(token, authentication) : token;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessTokenValue)  {
        OAuth2Authentication oAuth2Authentication = super.loadAuthentication(accessTokenValue);

        ServletRequestAttributes requestAttributes= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if(requestAttributes!=null) {
            String uri = requestAttributes.getRequest().getRequestURI();
            if ("/api/refactor/devicebind/bind".equals(uri)) {
                return oAuth2Authentication;
            }
        }
        BaseTokenStore baseTokenStore = (BaseTokenStore) applicationContext.getBean("tokenStore");
        boolean isValidate = baseTokenStore.getIsValidatedDevice(accessTokenValue);
        if (!isValidate) {
            throw new InvalidTokenException("Invalid token(device not bind)");
        }
        return oAuth2Authentication;
    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication)  {
        return super.createAccessToken(authentication);
    }
}

