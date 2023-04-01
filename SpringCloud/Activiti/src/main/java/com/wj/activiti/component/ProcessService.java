package com.wj.activiti.component;

import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Package: com.wj.activiti.component
 * @ClassName: ProcessService
 * @Auther: Winjay
 * @Date: 2022/10/23 13:03
 * @ProjectName: SpringCloud
 * @Des:流程实现类
 */
@Component
public class ProcessService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ProcessService.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    public boolean startProcess(String businessId){
        //①key：流程key
        //②businesskey：业务表ID，例如请假表，包含了请假人，起止时间等。
        //③vars：流程变量
        Map<String,Object> map = new HashMap<>();
        map.put("days",3);
        map.put("apply","张三");
        map.put("pass","1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave_process",businessId,map);

        //申请人默认跳过审批
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        if(task != null){
            Map<String,Object> map1 = new HashMap<>();
            map1.put("zg",null);
            taskService.complete(task.getId(),map1);
        }

        //输出相关信息
        LOGGER.info("流程部署id：{}",processInstance.getDeploymentId());
        LOGGER.info("流程定义id：{}", processInstance.getProcessDefinitionId());
        LOGGER.info("流程实例id：{}", processInstance.getId());
        LOGGER.info("活动id：{}", processInstance.getActivityId());
        return true;
    }

    /**
     * @description:流程实例删除
     * @author: Winjay
     * @date: 2022/10/23 13:09
     * @return: boolean
     **/
    public boolean deleteProcess(String processInstanceId){
        runtimeService.deleteProcessInstance(processInstanceId, "删除流程");
        historyService.deleteHistoricProcessInstance(processInstanceId);
        return true;
    }

    /**
     * @description:设置任务代理人
     * @author: Winjay
     * @date: 2022/10/23 22:57
     * @return: boolean
     **/
    public boolean candidateAssignee(String instanceId,String assignee){
        Task task = null;
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("leave_process")
                .processInstanceId(instanceId)
                //.taskCandidateUser(assignee)
                .taskUnassigned()
                .list();
        if (null != tasks && !tasks.isEmpty()){
            task = tasks.get(0);
            taskService.claim(task.getId(),assignee);
        }
        return true;
    }

    /**
     * @description:任务转派
     * @author: Winjay
     * @date: 2022/10/23 13:39
     * @param instanceId
     * @param assignee
     * @return: boolean
     **/
    public boolean transferAssignee(String instanceId, String assignee) {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("leave_process")
                .processInstanceId(instanceId)
                .taskAssignee(assignee)
                .singleResult();
        if(null == task){
            List<Task> leave_process = taskService.createTaskQuery().processDefinitionKey("leave_process").list();
            List<String> list = leave_process.stream().map(TaskInfo::getAssignee).collect(Collectors.toList());
            LOGGER.error("无法转派,当前任务不属于代理人{},应该属于{}",assignee, JSONObject.toJSONString(list));
            return false;
        }
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"[转派]"+assignee);
        taskService.setAssignee(task.getId(), assignee);
        return true;
    }
}
