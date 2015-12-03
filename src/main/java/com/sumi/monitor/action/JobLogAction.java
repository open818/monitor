package com.sumi.monitor.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sumi.monitor.core.DBHelper;
import com.sumi.monitor.object.Job;
import com.sumi.monitor.object.JobLog;
import com.sumi.monitor.object.Result;
import com.sumi.monitor.util.JsonUtil;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JobLogAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private int cron_id = 0;

	private  static Logger log = Logger.getLogger(JobLogAction.class);
	
	public String list() throws Exception {
		if(cron_id > 0){
			String sql = "select * from sys_cron_log where cron_id = ? order by create_time desc";//SQL语句
			DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
			List<JobLog> list = new ArrayList<JobLog>();
			try {
				db1.pst.setInt(1, cron_id);
				ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				while (ret.next()) {
					//添加定时任务
					JobLog job = new JobLog();
					job.setId(ret.getInt("id"));
					job.setCron_id(ret.getInt("cron_id"));
					job.setName(ret.getString("name"));
					job.setDesc(ret.getString("desc"));
					job.setFilePath(ret.getString("filepath"));
					job.setStatus(ret.getInt("status"));
					job.setCreated_time(format.format(ret.getTimestamp("create_time")));
					job.setUpdate_time(format.format(ret.getTimestamp("update_time")));

					list.add(job);
				}//显示数据
				ret.close();
				db1.close();//关闭连接
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Result result = new Result();
			result.setStatusCode("200");
			result.setMessage("执行成功");
			result.setCallbackType("");
			result.setRebackData(list);
			JsonUtil.objectToJson(result);
		}else{
			Result result = new Result();
			result.setStatusCode("400");
			result.setMessage("参数错误");
			result.setCallbackType("");
			JsonUtil.objectToJson(result);
		}

		return null;
	}

	public int getCron_id() {
		return cron_id;
	}

	public void setCron_id(int cron_id) {
		this.cron_id = cron_id;
	}
}
