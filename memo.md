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
2019-01-22 16:43:07.471  INFO 5448 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Creating filter chain: OrRequestMatcher [requestMatchers=[Ant [pattern='/oauth/token'], Ant [pattern='/oauth/token_key'], Ant [pattern='/oauth/check_token']]], [org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@1e097d59, org.springframework.security.web.context.SecurityContextPersistenceFilter@3abada5a, org.springframework.security.web.header.HeaderWriterFilter@2fd953a6, org.springframework.security.web.authentication.logout.LogoutFilter@7b420819, org.springframework.security.web.authentication.www.BasicAuthenticationFilter@1c33c17b, org.springframework.security.web.savedrequest.RequestCacheAwareFilter@61fe30, org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@3adcc812, org.springframework.security.web.authentication.AnonymousAuthenticationFilter@673be18f, org.springframework.security.web.session.SessionManagementFilter@141e5bef, org.springframework.security.web.access.ExceptionTranslationFilter@363a52f, org.springframework.security.web.access.intercept.FilterSecurityInterceptor@4f0100a7]
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