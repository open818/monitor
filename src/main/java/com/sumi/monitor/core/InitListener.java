package com.sumi.monitor.core;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;


public class InitListener implements ServletContextListener
{
	private static Logger log = Logger.getLogger(InitListener.class);

    public void contextInitialized(ServletContextEvent event)
    {
    	log.info("contextInitialized");

		String sql = "select * from sys_cron where active = 'Y'";//SQL语句
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象

		try {
			ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集
			while (ret.next()) {
				//添加定时任务
				try {
					String id = ret.getString("id");
					String name = ret.getString("name");

					String filepath = ret.getString("filepath");
					String cron = ret.getString("cron");
					QuartzManager.addJob(id, name,filepath,cron);
				} catch (SchedulerException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}//显示数据
			ret.close();
			db1.close();//关闭连接
			db1 = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		log.info("contextDestroyed");
		QuartzManager.shutdownJobs();
	}
}
