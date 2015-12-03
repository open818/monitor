package com.sumi.monitor.action;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.sumi.monitor.object.Result;
import com.sumi.monitor.util.JsonUtil;
import org.apache.log4j.Logger;

public class InitAction extends ActionSupport {
	private static Logger log = Logger.getLogger(InitAction.class);

	@Override
	public String execute() throws Exception {
		log.info("execute");
		return super.execute();
	}

	public String config() throws Exception {
		log.info("config");
		Result result = new Result();
		result.setMessage("设置成功");
		result.setCallbackType("");
		JsonUtil.toJson(new Gson().toJson(result));
		return null;
	}
}
