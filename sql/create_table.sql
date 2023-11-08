-- auto-generated definition
-- auto-generated definition
create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                        null comment '用户昵称',
    userAccount  varchar(256)                        null comment '??',
    avatarUrl    varchar(1024)                       null comment '用户头像',
    gender       tinyint                             null comment '性别',
    userPassword varchar(512)                        not null comment '密码',
    phone        varchar(128)                        null comment '电话',
    email        varchar(512)                        null comment '邮箱',
    userStatus   int       default 0                 not null comment '用户状态(0代表正常)',
    createTime   datetime  default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   timestamp default CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint   default 0                 not null comment '是否删除',
    userRole     int       default 0                 not null comment '用户角色 0-普通用户 1-管理员',
    registerCode varchar(512)                        null comment '注册编号',
    tags         varchar(1024)                       null comment '标签json列表'
)
    comment '用户';

alter table user add COLUMN tags varchar(1024) null comment '标签列表';

create table tag
(
    id         bigint auto_increment comment 'id'
        primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户id',
    parentId   bigint                             null comment '父标签id',
    isParent   tinyint                            null comment '0-不是父标签, 1-是父标签',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除',
    constraint uniIdx_tagName
        unique (tagName)
)
    comment '标签';

create index idx_userId
    on tag (userId);
