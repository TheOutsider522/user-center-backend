# 用户管理中心项目后端
## 数据库设计
+ 用户表(user):
  + id (主键) bigint
  + username 昵称 varchar
  + avatar 头像 varchar
  + gender 性别 tinyint
  + password 密码 varchar
  + phone 电话 varchar
  + email 邮箱 varchar
  + isValid 是否有效(比如封号之类) tinyint
  + createTime 创建时间(数据插入时间) datetime
  + update 更新时间(数据更新时间) datetime
  + isDelete 是否删除0/1(逻辑删除)  tinyint