```
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ------------- saveAuthorizationRequest  
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ____authorization request :{registration_id=github}
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ____add authorization request cookie
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :  *----> serialize data
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :  *----> CookieUtils addCookie
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Value: ...
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Name: oauth2_auth_request
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ___redirectUriAfterLogin: null
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ------------- End Of saveAuthorizationRequest
INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : ------------- removeAuthorizationRequest --------------
INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : ------------- loadAuthorizationRequest   -------------
INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@25aef2b0        
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  *----> CookieUtils getCookie
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  *----> Request :/oauth2/callback/github
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  '___Get Cookie : oauth2_auth_request
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  '___Cookie's value 
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   *----> deserialize data
INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : ___Remove the Cookies in HTTP the session
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : ------------- End Of removeAuthorizationRequest --------------
INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ------------- CustomOauth2User Service -------------
INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Third Party User Protected Resources (Attributes): Name: [68631186], Granted Authorities: [[ROLE_USER, SCOPE_user:email]], User Attributes: [{login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}]    


INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Authentication Provider is: github
INFO 15972 --- [nio-8080-exec-2] c.p.p.factory.OAuth2UserInfoFactory      : Login via Github Account
INFO 15972 --- [nio-8080-exec-2] c.p.p.factory.OAuth2UserInfoFactory      : Claims : {login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}

INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ____RETURN OAuth2UserPrincipal for the 
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  *----> CookieUtils getCookie
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  *----> Request :/oauth2/callback/github
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :  *__There are no cookies in this HttpServletRequest
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.security.JwtProvider        : ** TokenBuilderByOauth2User Generates the JWT for a Oauth2User
INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.security.JwtProvider        : ** getPrivateKey of the instance `keystore`
INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler : ---------------Execute Success Handler ---------------
INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :    '-------Get Mail: null
INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :       '---- fetch github private mail
INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '____oauthToken: maxwolf621
INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '___authorizedClient: maxwolf621
INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '____TOKEN: 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
2021-09-27 16:11:53.149  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '____Private Email: jervismayer@gmail.com
2021-09-27 16:11:53.149  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.service.OAuth2Service       :       '--- Process Oauth2 User
Hibernate: select user0_.user_id as user_id1_9_, user0_.about_me as about_me2_9_, user0_.auth_provider as auth_pro3_9_, user0_.avatar as avatar4_9_, user0_.created_date as created_5_9_, user0_.legit as legit6_9_, user0_.mail as mail7_9_, user0_.password as password8_9_, user0_.user_name as user_nam9_9_ from user user0_ where user0_.mail=?
2021-09-27 16:11:53.189  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.service.OAuth2Service       :   '--- Update The Member Data From the Third Party Application
Hibernate: select user0_.user_id as user_id1_9_1_, user0_.about_me as about_me2_9_1_, user0_.auth_provider as auth_pro3_9_1_, user0_.avatar as avatar4_9_1_, user0_.created_date as created_5_9_1_, 
user0_.legit as legit6_9_1_, user0_.mail as mail7_9_1_, user0_.password as password8_9_1_, user0_.user_name as user_nam9_9_1_, posts1_.user_id as user_id8_3_3_, posts1_.post_id as post_id1_3_3_, posts1_.post_id as post_id1_3_0_, posts1_.description as descript2_3_0_, posts1_.created_date as created_3_3_0_, posts1_.post_name as post_nam4_3_0_, posts1_.sub_id as sub_id7_3_0_, posts1_.url as 
url5_3_0_, posts1_.user_id as user_id8_3_0_, posts1_.vote_count as vote_cou6_3_0_ from user user0_ left outer join post posts1_ on user0_.user_id=posts1_.user_id where user0_.user_id=?
2021-09-27 16:11:53.208  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.service.OAuth2Service       : '--- Show Member Name:maxwolf621
2021-09-27 16:11:53.208  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : ___Remove the Cookies in HTTP the session
2021-09-27 16:11:53.208  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
2021-09-27 16:11:53.208  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
2021-09-27 16:11:53.216  INFO 15972 --- [nio-8080-exec-3] c.p.p.filter.JwtAuthenticationFilter     : get Jwt from Request
2021-09-27 16:11:53.216  INFO 15972 --- [nio-8080-exec-3] c.p.p.filter.JwtAuthenticationFilter     : return UnFormat Token : null



INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ------------- saveAuthorizationRequest
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ____authorization request :{registration_id=google}
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ____add authorization request cookie
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :   *----> serialize data
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :  *-----> CookieUtils addCookie
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Value: 
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Name: oauth2_auth_request
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ___redirectUriAfterLogin: http://locahost:4200/oauth2/
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ___add Redirect Uri Cookie
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :  *-----> CookieUtils addCookie
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Value: http://locahost:4200/oauth2/
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Name: redirect_uri
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ------------- End Of saveAuthorizationRequest
INFO 15972 --- [nio-8080-exec-2] c.p.p.filter.JwtAuthenticationFilter     : get Jwt from Request
INFO 15972 --- [nio-8080-exec-2] c.p.p.filter.JwtAuthenticationFilter     : return UnFormat Token : null
ERROR 15972 --- [nio-8080-exec-2] c.p.p.h.CustomAuthenticationEntryPoint   : Responding with unauthorized error. Message - Full authentication is required to access this resource









INFO 15972 --- [nio-8080-exec-5] stomOAuth2AuthorizationRequestRepository : ____authorization request :{registration_id=github}
INFO 15972 --- [nio-8080-exec-5] stomOAuth2AuthorizationRequestRepository : ____add authorization request cookie
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :  *----> serialize data
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :  *-----> CookieUtils addCookie
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :  '_______Cookie Value: ;;;
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Name: oauth2_auth_request
INFO 15972 --- [nio-8080-exec-5] stomOAuth2AuthorizationRequestRepository : ___redirectUriAfterLogin: http://locahost:4200/oauth2/
INFO 15972 --- [nio-8080-exec-5] stomOAuth2AuthorizationRequestRepository : ___add Redirect Uri Cookie
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :  *-----> CookieUtils addCookie
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Value: http://
INFO 15972 --- [nio-8080-exec-5] c.p.pttclone.utility.CookieUtils         :    '_______Cookie Name: redirect_uri
INFO 15972 --- [nio-8080-exec-5] stomOAuth2AuthorizationRequestRepository : ------------- End Of saveAuthorizationRequest
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : ------------- removeAuthorizationRequest --------------
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : *** loadAuthorizationRequest
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@1975b3bf        
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :  *----> CookieUtils getCookie
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :  *----> Request :/oauth2/callback/github
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   '___Get Cookie : oauth2_auth_request
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   '___Cookie's value ....
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   *----> deserialize data
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : ___Remove the Cookies in HTTP the session
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : ------------- End Of removeAuthorizationRequest --------------
INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ------------- CustomOauth2User Service -------------
INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Third Party User Protected Resources (Attributes): Name: [68631186], Granted Authorities: [[ROLE_USER, SCOPE_user:email]], User Attributes: [{login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}]    
2021-09-27 16:40:11.048  INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Authentication Provider is: github
2021-09-27 16:40:11.051  INFO 15972 --- [nio-8080-exec-6] c.p.p.factory.OAuth2UserInfoFactory      : Login via Github Account
2021-09-27 16:40:11.051  INFO 15972 --- [nio-8080-exec-6] c.p.p.factory.OAuth2UserInfoFactory      : Claims : {login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}

INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ____RETURN OAuth2UserPrincipal for the Authentication
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :  *----> CookieUtils getCookie
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :  *----> Request :/oauth2/callback/github
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   '___Get Cookie : redirect_uri
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :       '___Cookie's value :
INFO 15972 --- [nio-8080-exec-6] h.OAuth2UserAuthenticationSuccessHandler : client's redirect URI is :
INFO 15972 --- [nio-8080-exec-6] h.OAuth2UserAuthenticationSuccessHandler : clientRedirectUri.getHost() :null
INFO 15972 --- [nio-8080-exec-6] h.OAuth2UserAuthenticationSuccessHandler : redirectOptional[]
ERROR 15972 --- [nio-8080-exec-6] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception




INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : -------------Start of saveAuthorizationRequest-------------
INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : add authorization-request cookie from OAuth2AuthorizationRequest
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         : *----> serialize data : org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest@6cb6a7fe
INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         : *-----CookieUtils addCookie:
Name: oauth2_auth_request
Cookie Value: 
2021-09-27 21:14:41.497  INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : ___Add redirect_uri Cookie from frontend's request
2021-09-27 21:14:41.498  INFO 15972 --- [nio-8080-exec-1] c.p.pttclone.utility.CookieUtils         : *-----CookieUtils addCookie:
Name: redirect_uri
Cookie Value: http://localhost:4200/oauth2/
2021-09-27 21:14:41.500  INFO 15972 --- [nio-8080-exec-1] stomOAuth2AuthorizationRequestRepository : -------------End Of saveAuthorizationRequest-------------
2021-09-27 21:14:42.038  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : -------------Start of removeAuthorizationRequest-------------
2021-09-27 21:14:42.039  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : -------------loadAuthorizationRequest from HttpServletRequest-------------
2021-09-27 21:14:42.040  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@57366549
2021-09-27 21:14:42.040  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         : *-----CookieUtils getCookie form Request : /oauth2/callback/github
2021-09-27 21:14:42.041  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   '___Get Cookie Name : oauth2_auth_request
2021-09-27 21:14:42.043  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   *----> deserialize data
2021-09-27 21:14:42.045  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : -------------Remove the Cookies in HTTP the session-------------
2021-09-27 21:14:42.046  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : -------------End Of removeAuthorizationRequest-------------
2021-09-27 21:14:42.480  INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ------------- CustomOauth2User Service -------------
2021-09-27 21:14:42.932  INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Third Party User Protected Resources (Attributes): Name: [68631186], Granted Authorities: [[ROLE_USER, SCOPE_user:email]], User Attributes: [{login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}]
2021-09-27 21:14:42.934  INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Authentication Provider is: github
2021-09-27 21:14:42.937  INFO 15972 --- [nio-8080-exec-2] c.p.p.factory.OAuth2UserInfoFactory      : Login via Github Account
2021-09-27 21:14:42.937  INFO 15972 --- [nio-8080-exec-2] c.p.p.factory.OAuth2UserInfoFactory      : Claims : {login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}
2021-09-27 21:14:42.941  INFO 15972 --- [nio-8080-exec-2] c.p.p.s.CustomOAuth2UserPrincipalService : ____RETURN OAuth2UserPrincipal for the Authentication
2021-09-27 21:14:42.947  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler : Get Request path Info : http://localhost:8080/oauth2/callback/github
2021-09-27 21:14:42.947  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         : *-----CookieUtils getCookie form Request : /oauth2/callback/github
2021-09-27 21:14:42.948  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.utility.CookieUtils         :   '___Get Cookie Name : redirect_uri
2021-09-27 21:14:42.949  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler : client's redirect URI is : http://localhost:4200/oauth2/
2021-09-27 21:14:42.951  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler : clientRedirectUri.getHost() :localhost localhost
2021-09-27 21:14:42.951  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler : clientRedirectUri.getPort() :4200 4200
2021-09-27 21:14:42.952  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.security.JwtProvider        : ** TokenBuilderByOauth2User Generates the JWT for a Oauth2User
2021-09-27 21:14:42.954  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.security.JwtProvider        : ** getPrivateKey of the instance `keystore`
2021-09-27 21:14:43.310  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler : ---------------Execute Success Handler ---------------
2021-09-27 21:14:43.311  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :    '-------Get Mail: null
2021-09-27 21:14:43.311  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :       '---- fetch github private mail
2021-09-27 21:14:43.312  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '____oauthToken: maxwolf621
2021-09-27 21:14:43.313  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '___authorizedClient: maxwolf621
2021-09-27 21:14:43.314  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '____TOKEN: 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
2021-09-27 21:14:43.567  INFO 15972 --- [nio-8080-exec-2] h.OAuth2UserAuthenticationSuccessHandler :           '____Private Email: jervismayer@gmail.com
2021-09-27 21:14:43.568  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.service.OAuth2Service       :       '--- Process Oauth2 User
Hibernate: select user0_.user_id as user_id1_9_, user0_.about_me as about_me2_9_, user0_.auth_provider as auth_pro3_9_, user0_.avatar as avatar4_9_, user0_.created_date as created_5_9_, user0_.legit as legit6_9_, user0_.mail as mail7_9_, user0_.password as password8_9_, user0_.user_name as user_nam9_9_ from user user0_ where user0_.mail=?
2021-09-27 21:14:43.609  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.service.OAuth2Service       :   '--- Update The Member Data From the Third Party Application
Hibernate: select user0_.user_id as user_id1_9_1_, user0_.about_me as about_me2_9_1_, user0_.auth_provider as auth_pro3_9_1_, user0_.avatar as avatar4_9_1_, user0_.created_date as created_5_9_1_, user0_.legit as legit6_9_1_, user0_.mail as mail7_9_1_, user0_.password as password8_9_1_, user0_.user_name as user_nam9_9_1_, posts1_.user_id as user_id8_3_3_, posts1_.post_id as post_id1_3_3_, posts1_.post_id as post_id1_3_0_, posts1_.description as descript2_3_0_, posts1_.created_date as created_3_3_0_, posts1_.post_name as post_nam4_3_0_, posts1_.sub_id as sub_id7_3_0_, posts1_.url as url5_3_0_, posts1_.user_id as user_id8_3_0_, posts1_.vote_count as vote_cou6_3_0_ from user user0_ left outer join post posts1_ on user0_.user_id=posts1_.user_id where user0_.user_id=?  
2021-09-27 21:14:43.629  INFO 15972 --- [nio-8080-exec-2] c.p.pttclone.service.OAuth2Service       :           '--- Show Member Name:maxwolf621
2021-09-27 21:14:43.630  INFO 15972 --- [nio-8080-exec-2] stomOAuth2AuthorizationRequestRepository : -------------Remove the Cookies in HTTP the session-------------
2INFO 15972 --- [nio-8080-exec-3] c.p.p.filter.JwtAuthenticationFilter     : get Jwt from Request
INFO 15972 --- [nio-8080-exec-3] c.p.p.filter.JwtAuthenticationFilter      : return UnFormat Token : null
ERROR 15972 --- [nio-8080-exec-3] c.p.p.h.CustomAuthenticationEntryPoint   : Responding with unauthorized error. Message - Full authentication is required to access this resource


INFO 15972 --- [nio-8080-exec-4] stomOAuth2AuthorizationRequestRepository : -------------Start of saveAuthorizationRequest-------------
INFO 15972 --- [nio-8080-exec-4] stomOAuth2AuthorizationRequestRepository : ___Add authorization-request cookie (From OAuth2AuthorizationRequest)
INFO 15972 --- [nio-8080-exec-4] c.p.pttclone.utility.CookieUtils         : *----serialize data : org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest@378f8ddc
INFO 15972 --- [nio-8080-exec-4] c.p.pttclone.utility.CookieUtils         :
*-----CookieUtils addCookie :
Name: oauth2_auth_request
Cookie Value: ....
INFO 15972 --- [nio-8080-exec-4] stomOAuth2AuthorizationRequestRepository : ___Add redirect_uri Cookie (From Frontend's Request)
INFO 15972 --- [nio-8080-exec-4] c.p.pttclone.utility.CookieUtils         :
*-----CookieUtils addCookie :
Name: redirect_uri
Cookie Value: http://localhost:4200/oauth2/
INFO 15972 --- [nio-8080-exec-4] stomOAuth2AuthorizationRequestRepository : -------------End Of saveAuthorizationRequest-------------
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : -------------Start of removeAuthorizationRequest-------------
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : -------------loadAuthorizationRequest from HttpServletRequest-------------
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : org.springframework.security.web.header.HeaderWriterFilter$HeaderWriterRequest@5349b1d
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :
*-----CookieUtils getCookie from Request : /oauth2/callback/github
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   '___Get Cookie Name : oauth2_auth_request
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   *----deserialize data
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : -------------Remove the Cookies in HTTP the session-------------
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   *----> CookieUtils deleteCookie
INFO 15972 --- [nio-8080-exec-6] stomOAuth2AuthorizationRequestRepository : -------------End Of removeAuthorizationRequest-------------
INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ------------- CustomOauth2User Service -------------
INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Third Party User Protected Resources (Attributes): Name: [68631186], Granted Authorities: [[ROLE_USER, SCOPE_user:email]], User Attributes: [{login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}]
INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ___The Authentication Provider is: github
INFO 15972 --- [nio-8080-exec-6] c.p.p.factory.OAuth2UserInfoFactory      : Login via Github Account
INFO 15972 --- [nio-8080-exec-6] c.p.p.factory.OAuth2UserInfoFactory      : Claims : {login=maxwolf621, id=68631186, node_id=MDQ6VXNlcjY4NjMxMTg2, avatar_url=https://avatars.githubusercontent.com/u/68631186?v=4, gravatar_id=, url=https://api.github.com/users/maxwolf621, html_url=https://github.com/maxwolf621, followers_url=https://api.github.com/users/maxwolf621/followers, following_url=https://api.github.com/users/maxwolf621/following{/other_user}, gists_url=https://api.github.com/users/maxwolf621/gists{/gist_id}, starred_url=https://api.github.com/users/maxwolf621/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/maxwolf621/subscriptions, organizations_url=https://api.github.com/users/maxwolf621/orgs, repos_url=https://api.github.com/users/maxwolf621/repos, events_url=https://api.github.com/users/maxwolf621/events{/privacy}, received_events_url=https://api.github.com/users/maxwolf621/received_events, type=User, site_admin=false, name=maxwolf621, company=null, blog=, location=null, email=null, hireable=null, bio=null, twitter_username=null, public_repos=18, public_gists=0, followers=0, following=0, created_at=2020-07-22T05:37:14Z, updated_at=2021-09-27T07:26:06Z}
INFO 15972 --- [nio-8080-exec-6] c.p.p.s.CustomOAuth2UserPrincipalService : ____RETURN OAuth2UserPrincipal for the Authentication
INFO 15972 --- [nio-8080-exec-6] h.OAuth2UserAuthenticationSuccessHandler : Get Request path Info : http://localhost:8080/oauth2/callback/github
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         : *-----CookieUtils getCookie from Request : /oauth2/callback/github
INFO 15972 --- [nio-8080-exec-6] c.p.pttclone.utility.CookieUtils         :   '___Get Cookie Name : redirect_uri
INFO 15972 --- [nio-8080-exec-6] h.OAuth2UserAuthenticationSuccessHandler : client's redirect URI is :
ERROR 15972 --- [nio-8080-exec-6] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception




















