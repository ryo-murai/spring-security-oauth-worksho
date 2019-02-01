# spring-security-oauth workshop memo

## authorization-server.md

* fix deprecated property keys


```java properties
#security.user.password=password
spring.security.user.password=password

#server.context-path=/uaa
server.servlet.context-path=/uaa
```

* different logs for oauth endpoints.

```Text
2019-01-24 18:08:41.063  INFO 9608 --- [           main] o.s.s.web.DefaultSecurityFilterChain     :
 Creating filter chain: OrRequestMatcher
  [requestMatchers=[Ant [pattern='/oauth/token'], 
  Ant [pattern='/oauth/token_key'], 
  Ant [pattern='/oauth/check_token']]], 
  [
  org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@7f0d96f2, 
  org.springframework.security.web.context.SecurityContextPersistenceFilter@5bf0fe62, 
  org.springframework.security.web.header.HeaderWriterFilter@4f0100a7, 
  org.springframework.security.web.authentication.logout.LogoutFilter@3fed2870, 
  org.springframework.security.web.authentication.www.BasicAuthenticationFilter@36328d33, 
  org.springframework.security.web.savedrequest.RequestCacheAwareFilter@673be18f, 
  org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@6ce86ce1, 
  org.springframework.security.web.authentication.AnonymousAuthenticationFilter@545b995e, 
  org.springframework.security.web.session.SessionManagementFilter@13ad5cd3,
  org.springframework.security.web.access.ExceptionTranslationFilter@66c92293,
  org.springframework.security.web.access.intercept.FilterSecurityInterceptor@34c01041]
```  

## cli-application.md

* fix deprecated property `spring.main.web-environment`

```java properties
cli.client-id=demo
cli.client-secret=demo
cli.access-token-uri=http://localhost:9999/uaa/oauth/token
cli.tweet-api-uri=http://localhost:8082/v1
#spring.main.web-environment=false
spring.main.web-application-type=NONE
spring.main.banner-mode=off
logging.level.root=off
```

