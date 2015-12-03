-- ----------------------------
-- Table structure for sys_cron
-- ----------------------------
DROP TABLE IF EXISTS `sys_cron`;
CREATE TABLE `sys_cron` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '计划名称',
  `desc` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `filepath` varchar(255) NOT NULL COMMENT '运行文件',
  `cron` varchar(255) NOT NULL COMMENT '定时',
  `active` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否启用',
  `create_uid` int(11) NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT now(),
  `update_time` datetime NOT NULL DEFAULT now(),
  `update_uid` int(11) NOT NULL NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务计划列表';

-- ----------------------------
-- Table structure for sys_cron
-- ----------------------------
DROP TABLE IF EXISTS `sys_cron_log`;
CREATE TABLE `sys_cron_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cron_id` int(11) NOT NULL COMMENT '计划ID',
  `name` varchar(255) NOT NULL COMMENT '计划名称',
  `desc` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `filepath` varchar(255) NOT NULL COMMENT '运行文件',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态, 1:正在运行 2:运行成功 3:运行错误',
  `create_uid` int(11) NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT now(),
  `update_uid` int(11) NOT NULL DEFAULT 0,
  `update_time` datetime NOT NULL DEFAULT now(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务计划执行列表';