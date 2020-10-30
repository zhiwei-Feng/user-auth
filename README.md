# user-auth
user-auth module for Advanced Software Engineering
 
## 文件结构
原 src/main/java/SELab 迁移到 src/main/java/ase

## 环境
- openjdk 12
- Mysql
- Http请求使用RestTemplate

## 待办
1.迁移
- controller
- [ ] /utils/pdf
- [x] /util/users
- [x] /user/userinfo
- [x] /login
- [x] /register
- [ ] /user/articleDetail
- [ ] /user/articleSubmission
- [ ] /user/updateArticles
- [ ] /user/reviews
- [ ] /user/undealedNotifications
- [ ] /user/invitationRepo
- [ ] /user/undealedNotificationsNum
- [ ] /user/alreadyDealedNotifications
- [ ] /user/chairMeeting
- [ ] /user/pcMemberMeeting
- [ ] /user/authorMeeting
- [ ] /user/availableMeeting
- service
- [ ] 对应的service
- repository
- [ ] 对应的repository
- domain 
- [ ] 对应的domain

2.待增加（url待定）
- [ ] findByUsername (user)
- [ ] findById (user)
- [ ] findByFullnameAndEmail (user)
- [ ] preAuth (any request)
- [ ] addPostMessage (PostMessage)
- [ ] findByArticleIdAndStatus (PostMessage)
- [ ] findById (PostMessage)
