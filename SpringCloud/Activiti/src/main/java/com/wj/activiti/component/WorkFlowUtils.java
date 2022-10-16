package com.wj.activiti.component;

import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.wj.activiti.component
 * @ClassName: WorkFlowUtils
 * @Description: 流程工具类
 * @Author: Winjay
 * @Date: 2022-10-16 23:33:44
 */
@Component
public class WorkFlowUtils {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    private ManagementService managementService;

    /**
     * 流程启动
     * @return
     */
    public boolean start(){
        //创建流程实例  流程定义的key: bpmn id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_leave");

        //输出相关信息
        System.out.println("流程部署id" + processInstance.getDeploymentId());
        System.out.println("流程定义id" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id" + processInstance.getId());
        System.out.println("活动id" + processInstance.getActivityId());
        return true;
    }

    public boolean completeTask(String assignee) {
        System.out.println(assignee);
        //3.查询当前用户的任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process_leave")
                .taskAssignee(assignee)
                .singleResult();

        //4.处理任务,结合当前用户任务列表的查询操作的话,任务ID:task.getId()
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pass", "1");

        taskService.complete(task.getId(), variables);

        //5.输出任务的id
        System.out.println(task.getId());
        return true;
    }
}
