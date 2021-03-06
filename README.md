# user-auth
user-auth module for Advanced Software Engineering

## demo用法（临时）
主要流程，定义了一个假冒的findArticleById的接口，位于DemoController中。同时在/user/articleDetail中实现了对该接口的调用。
- 配置文件application.properties中定义了服务ip和port以及相应的api名称
- 定义了RemoteServiceConfig来注入application.properties对应的变量
- DemoController的接口中，先是请求了/check接口验证登录状态，然后返回了预设的Article对象
- /user/articleDetail同样是先是请求了/check接口验证，然后成功后再调用对应的service，在service中会对假冒的findArticleById接口进行调用，
获取相应的Article对象。
 
## 文件结构
原 src/main/java/SELab 迁移到 src/main/java/ase

## 环境
- openjdk 12
- Mysql
- Http请求使用RestTemplate

## 待办

**等待接口协商联调**

1.迁移
- controller
- [x] /util/users
- [x] /user/userinfo
- [x] /login
- [x] /register
- [x] /user/articleDetail
- [x] /user/reviews
- [x] /user/undealedNotifications
- [x] /user/invitationRepo
- [x] /user/undealedNotificationsNum
- [x] /user/alreadyDealedNotifications
- [x] /user/chairMeeting
- [x] /user/pcMemberMeeting
- [x] /user/authorMeeting
- [x] /user/availableMeeting
- service
- [x] 对应的service
- repository
- [x] 对应的repository
- domain 
- [x] 对应的domain

2.待增加（url待定）
- [x] findByUsername (user), url=/user/userinfo, method=GET
- [x] findById (user), url=/user/id, method=GET
- [x] findByFullnameAndEmail (user), url=/user/author, method=GET
- [x] preAuth (any request), url=/check, method=GET
- [x] addPostMessage (PostMessage), url=/postmessage, method=POST
- [x] findByArticleIdAndStatus (PostMessage), url=/postmessage/article, method=GET
- [x] findById (PostMessage), url=/postmessage/id, method=GET
