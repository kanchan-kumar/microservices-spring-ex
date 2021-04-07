package com.microservices.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String RESOURCE_ID = "couponservice";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private DataSource dataSource;
	@Value("${alias}")
	private String alias;
	@Value("${keyFile}")
	private String keyFile;
	@Value("${password}")
	private String password;

	@Override public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		/*For OAuth Token */
		/*endpoints.tokenStore(new JdbcTokenStore(dataSource)).
				authenticationManager(authenticationManager).
				userDetailsService(userDetailsService);*/

		/*For Jwt Token.*/
		endpoints.tokenStore(tokenStore()).accessTokenConverter(jwtAccessTokenConverter()).
				authenticationManager(authenticationManager).
				userDetailsService(userDetailsService);
	}

	@Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		// For Grant Type Password
		/*clients.inMemory().withClient("couponClientApp").secret(encoder.encode("9999")).
				authorizedGrantTypes("password", "refresh_token").
				scopes("read", "write").resourceIds(RESOURCE_ID);*/

		//For Grant Type Authorization Code
		clients.inMemory().withClient("couponClientApp").secret(encoder.encode("9999")).
				authorizedGrantTypes("authorization_code", "refresh_token").
				scopes("read", "write").resourceIds(RESOURCE_ID).redirectUris("http://localhost:8081/codeHandlerPage");
	}

	@Override public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()");
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyFile), password.toCharArray());
		KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias);
		jwtAccessTokenConverter.setKeyPair(keyPair);
		return jwtAccessTokenConverter;
	}
}
