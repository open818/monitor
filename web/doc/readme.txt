Quartz + Mysql + Perl 实现的定时任务计划
Quartz 用来定时跑任务，而实际执行任务操作，由Perl来编写。Perl执行异常，也不影响Quertz服务器的正常运行，不影响其它任务的执行。
新增Perl任务也不需重新启动服务器，只需把对应Perl文件放到服务器某目录，通过JobAction的add方法，把任务添加进数据库即可。

注意：目前只有一个特定的Job类，即PerlJob,通过Runtime.getRuntime().exec()方法调用Windows的cmd来执行指定的Perl文件。因此必须发布运行在Windows系统下，
且系统中必须正常安装Perl。

实现原理：
1、服务器启动时，查询mysql数据库任务计划表，根据表中的信息生成并启动一个个定时任务。
2、提供了对任务计划表的一些增删改查操作，及历史执行记录的查询。并通过定时任务管理类管理已启用的任务。

发布注意：
1、在Mysql数据库中执行db.sql中的建表语句。
2、根据实际数据库，修改DBHelper类中的数据库连接信息。
3、根据Perl安装位置，修改PerlJob类中定义的perl.exe文件位置。