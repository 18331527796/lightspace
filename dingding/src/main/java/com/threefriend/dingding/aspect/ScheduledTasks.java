package com.threefriend.dingding.aspect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.threefriend.constants.DingDingAccessToken;
import com.threefriend.dingding.mapper.UserTaskRecordMapper;
import com.threefriend.dingding.repository.TaskRecordRepository;
import com.threefriend.dingding.repository.TaskRepository;
import com.threefriend.dingding.repository.UserTaskRecordrepository;
import com.threefriend.dingding.util.DingDingUtils;

/**
 * 定时器任务 每天23点统计当天的情况
 * @author Administrator
 *
 */
@Component
@EnableScheduling
public class ScheduledTasks {
	
	@Autowired
	private TaskRepository task_dao;
	@Autowired
	private TaskRecordRepository record_dao;
	@Autowired
	private UserTaskRecordrepository u_t_dao;

    //输出时间格式
    private static final SimpleDateFormat format = new SimpleDateFormat("HH(hh):mm:ss");

    //表达式  执行此任务
    @Scheduled(cron="0 0 23 1/1 * ? ")
    //@Scheduled(cron="3 3 10 1/1 * ? ")
    public void firstScheduledTasks() throws ParseException{
        System.out.println("定时任务执行，现在时间是 : "+format.format(new Date())+"进行统计操作");
        //获取今天的 0点 23点
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date begin = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        Date end = calendar.getTime();
        
        int leaderNmu = task_dao.findByIsShowAndLevelOrderById(1, 1).size();
        int noLeaderNmu = task_dao.findByIsShowAndLevelOrderById(1, 2).size();
        
        String token = DingDingAccessToken.getToken();
    	JSONArray deptInfo = DingDingUtils.getDeptInfo(token);
    	for (int i=0; i<deptInfo.size(); i++) {
  		  JSONObject jsonObject = (JSONObject) deptInfo.get(i);
  		  System.out.println(jsonObject.get("name").toString());
  		  String id = jsonObject.get("dept_id").toString();
  		  List<UserTaskRecordMapper> deptUserList = DingDingUtils.getDeptUserList(token, id, null);
  		  for (UserTaskRecordMapper user : deptUserList) {
  			  int count = record_dao.countByUserIdAndTimeBetween(user.getUserid(),begin,end);
  			  user.setDeptId(id);
  			  user.setTime(new Date());
  			  String isleader = DingDingUtils.isLeader(token,user.getUserid().toString(),id);
  			  if(isleader.equals("true")) {
  				user.setIsSuccess(count+"/"+leaderNmu);
  			  }else {
  				user.setIsSuccess(count+"/"+noLeaderNmu);
  			  }
  		  }
  		  u_t_dao.saveAll(deptUserList);
  		}
    }
    
    
}