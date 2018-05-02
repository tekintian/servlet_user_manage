# servlet_user_manage
java servlet_user_manage demo project ,  Just for Learning Java Servlet

采用 grade 依赖管理工具管理项目依赖和构建


## 数据库创建语句
- users用户表创建

```sql
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `grade` int(11) DEFAULT '1',
  `passwd` varchar(32) NOT NULL,
  `unknow` varchar(32) DEFAULT NULL,
  `remark` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
```






Git本地创建后再增加远程git仓库合并问题解决：
git pull origin master --allow-unrelated-histories