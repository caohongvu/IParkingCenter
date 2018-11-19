package net.cis.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import net.cis.jpa.entity.security.AuthenticationUser;

@Service("customTokenEnhancer")
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    		AuthenticationUser authenticationUser = (AuthenticationUser) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();
        
        additionalInfo.put("jid", authenticationUser.getJid());
        additionalInfo.put("xmppPassword", authenticationUser.getXmppPassword());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }


}
