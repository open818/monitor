package com.sumi.monitor.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sumi.monitor.core.DBHelper;
import com.sumi.monitor.core.QuartzManager;
import com.sumi.monitor.object.Job;
import com.sumi.monitor.object.Result;
import com.sumi.monitor.util.JsonUtil;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JobAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private Job job;

	private  static Logger log = Logger.getLogger(JobAction.class);
	
	public String list() throws Exception {
		String sql = "select c.*, l.create_time last_time, status\n" +
				"from sys_cron c\n" +
				"\t\t left join (select * from (select * from sys_cron_log l order by create_time desc) a group by cron_id) l on c.id = l.cron_id";//SQL语句
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		List<Job> list = new ArrayList<Job>();
		try {
			ResultSet ret = db1.pst.executeQuery();//执行语句，得到结果集
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (ret.next()) {
				//添加定时任务
				Job job = new Job();
				job.setId(ret.getInt("id"));
				job.setName(ret.getString("name"));
				job.setDesc(ret.getString("desc"));
				job.setFilePath(ret.getString("filepath"));
				job.setActive(ret.getString("active"));
				job.setCron(ret.getString("cron"));
				job.setCreated_time(format.format(ret.getTimestamp("create_time")));
				job.setLastLog_state(ret.getInt("status"));
				if(ret.getTimestamp("last_time") != null){
					job.setLastLog_time(format.format(ret.getTimestamp("last_time")));
				}

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
		return null;
	}

	public String add() throws Exception{
		String sql = "insert into sys_cron(`name`,`desc`,`filepath`,`cron`) values(?,?,?,?)";//SQL语句
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		try {
			db1.pst.setString(1, job.getName());
			db1.pst.setString(2, job.getDesc()==null?"":job.getDesc());
			db1.pst.setString(3, job.getFilePath());
			db1.pst.setString(4, job.getCron());
			db1.pst.executeUpdate();
			ResultSet rs = db1.pst.getGeneratedKeys();
			int record_id = 0;
			if (rs.next()) {
				record_id = rs.getInt(1);//取得ID
			}
			rs.close();
			db1.pst.close();
			db1.close();//关闭连接

			if (record_id > 0) {
				QuartzManager.addJob(String.valueOf(record_id), job.getName(), job.getFilePath(), job.getCron());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		result.setRebackData(job);
		JsonUtil.objectToJson(result);
		return null;
	}

	public String delete() throws Exception {
		String sql = "delete from sys_cron where id=?";//SQL语句
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		try {
			db1.pst.setInt(1, job.getId());
			int i = db1.pst.executeUpdate();
			if(i > 0){
				QuartzManager.removeJob(String.valueOf(job.getId()));
			}
			db1.pst.close();
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		result.setRebackData(job);
		JsonUtil.objectToJson(result);
		return null;
	}

	public String active() throws Exception {
		String sql = "select * from sys_cron where id = ?";
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		try {
			db1.pst.setInt(1, job.getId());
			ResultSet ret = db1.pst.executeQuery();
			if(ret.next()){
				String active = ret.getString("active");
				String name = ret.getString("name");
				String filepath = ret.getString("filepath");
				String cron = ret.getString("cron");
				if("N".equals(active)){
					int i = db1.pst.executeUpdate("update sys_cron set active = 'Y' where id=" + job.getId());
					if(i > 0){
						QuartzManager.addJob(String.valueOf(job.getId()), name, filepath, cron);
					}
				}
			}
			ret.close();
			db1.pst.close();
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		result.setRebackData(job);
		JsonUtil.objectToJson(result);
		return null;
	}

	public String inactive() throws Exception {
		String sql = "select * from sys_cron where id = ?";
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		try {
			db1.pst.setInt(1, job.getId());
			ResultSet ret = db1.pst.executeQuery();
			if(ret.next()){
				String active = ret.getString("active");
				String name = ret.getString("name");
				String filepath = ret.getString("filepath");
				String cron = ret.getString("cron");
				if("Y".equals(active)){
					int i = db1.pst.executeUpdate("update sys_cron set active = 'N' where id=" + job.getId());
					if(i > 0){
						QuartzManager.removeJob(String.valueOf(job.getId()));
					}
				}
			}
			ret.close();
			db1.pst.close();
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		result.setRebackData(job);
		JsonUtil.objectToJson(result);
		return null;
	}

	public String modify() throws Exception{
		String sql = "update sys_cron set `name` = ?, `desc`=?, `filepath` = ?, `cron` = ? where id = ?";//SQL语句
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		try {
			db1.pst.setString(1, job.getName());
			db1.pst.setString(2, job.getDesc()==null?"":job.getDesc());
			db1.pst.setString(3, job.getFilePath());
			db1.pst.setString(4, job.getCron());
			db1.pst.setInt(5, job.getId());
			int i = db1.pst.executeUpdate();

			db1.pst.close();
			db1.close();//关闭连接

			if (i > 0) {
				QuartzManager.modifyJobTime(String.valueOf(job.getId()), job.getName(), job.getFilePath(), job.getCron());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		result.setRebackData(job);
		JsonUtil.objectToJson(result);
		return null;
	}

	public String immediateRun() throws Exception{
		String sql = "select * from sys_cron where id = ?";
		DBHelper db1 = new DBHelper(sql);//创建DBHelper对象
		try {
			db1.pst.setInt(1, job.getId());
			ResultSet ret = db1.pst.executeQuery();
			if(ret.next()){
				String name = ret.getString("name");
				String filepath = ret.getString("filepath");

				QuartzManager.addImmediateJob(String.valueOf(job.getId()), name, filepath);
			}
			ret.close();
			db1.pst.close();
			db1.close();//关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Result result = new Result();
		result.setStatusCode("200");
		result.setMessage("执行成功");
		result.setCallbackType("");
		result.setRebackData(job);
		JsonUtil.objectToJson(result);
		return null;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
}
