create schema can301;

DROP TABLE IF EXISTS can301.user_info;
CREATE TABLE can301.user_info(
    user_id        int auto_increment comment 'user_id'
        primary key,
    user_name      varchar(255) not null comment 'user_name',
    user_password  varchar(255) not null comment 'user_password',
    user_sex       varchar(255) null comment 'user_sex',
    user_image     varchar(255) not null comment 'user_image',
    user_email     varchar(255) null comment 'user_email',
    user_phone     varchar(255) null comment 'user_phone',
    user_status    varchar(255)     null comment 'user_status'
)  COMMENT = '';

DROP TABLE IF EXISTS can301.favorites_info;
CREATE TABLE can301.favorites_info(
    user_id INT NOT NULL   COMMENT 'user_id',
    post_id varchar(255) NOT NULL   COMMENT 'post_Id'
)  COMMENT = '';


DROP TABLE IF EXISTS can301.post_info;
CREATE TABLE can301.post_info(
    post_id     int auto_increment comment 'post_Id'
        primary key,
    user_id     int          not null comment 'user_id',
    post_title varchar(255) not null comment 'post_title',
    post_info varchar(255) not null comment 'post_info',
    post_time   datetime     not null comment 'post_time'
)  COMMENT = '';


DROP TABLE IF EXISTS can301.favor_info;
CREATE TABLE can301.favor_info(
    user_id INT NOT NULL   COMMENT 'user_id',
    post_id varchar(255) NULL   COMMENT 'post_Id'
)  COMMENT = '';

DROP TABLE IF EXISTS can301.reply_info;
CREATE TABLE can301.reply_info(
    reply_id INT NOT NULL AUTO_INCREMENT  COMMENT 'reply_id' ,
    post_id INT NOT NULL   COMMENT 'post_id' ,
    user_id INT NOT NULL   COMMENT 'user_id' ,
    reply_where VARCHAR(255) NOT NULL   COMMENT 'reply_where;例如：
一楼：该字段为1
一楼中的一楼为：1-1
一楼中的二楼为：1-2
一楼中的一楼中的一楼为：1-1-1
依次类推' ,
    reply_info VARCHAR(255) NOT NULL   COMMENT 'reply_info' ,
    PRIMARY KEY (reply_id)
)  COMMENT = '';

DROP TABLE IF EXISTS can301.message_info;
CREATE TABLE can301.message_info(
    message_id VARCHAR(255) NOT NULL AUTO_INCREMENT  COMMENT 'message_id' ,
    get_user_Id VARCHAR(255) NOT NULL   COMMENT 'get_user_Id' ,
    send_user_Id VARCHAR(255) NOT NULL   COMMENT 'send_user_Id' ,
    content VARCHAR(255)    COMMENT 'content' ,
    PRIMARY KEY (message_id)
)  COMMENT = '';

