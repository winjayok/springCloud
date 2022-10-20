package com.wj.activiti.listener;

import org.activiti.engine.delegate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @PackageName: com.wj.activiti.listener
 * @ClassName: StartListener
 * @Description: 流程监听器
 * @Author: Winjay
 * @Date: 2022-10-16 22:48:02
 */
@Component
public class StartListener implements ExecutionListener , TaskListener{

    protected static final Logger logger = LoggerFactory.getLogger(StartListener.class);

    // 流程名称
    private Expression modelName;
    // 审批人变量
    private Expression taskKey;
    // 审批人变量（多人审批时使用）
    private Expression taskKeyList;
    // 0单人审批，1多人审批
    private Expression taskType;

    public Expression getModelName() {
        return modelName;
    }

    public void setModelName(Expression modelName) {
        this.modelName = modelName;
    }

    public Expression getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(Expression taskKey) {
        this.taskKey = taskKey;
    }

    public Expression getTaskKeyList() {
        return taskKeyList;
    }

    public void setTaskKeyList(Expression taskKeyList) {
        this.taskKeyList = taskKeyList;
    }

    public Expression getTaskType() {
        return taskType;
    }

    public void setTaskType(Expression taskType) {
        this.taskType = taskType;
    }

    private static final String approver = "张三,李思";

    /**
     * @description:ExecutionListener监听开始结束
     * @author: Winjay
     * @date: 2022/10/17 10:53
     * @param delegateExecution
     * isSequential，为true表示是串行多实例，false表示是并行多实例
     * activiti:collection，表示多实例的人员集合，${leaderList}是取变量leaderList的值
     * activiti:elementVariable，人员集合中的元素变量，这里要UserTask中activiti:assignee的值对应，这里变量名称都是leader
     * nrOfInstances，实例总数（nr是number的简写）
     *  nrOfCompletedInstances，已完成的实例数
     * completionCondition，表示多实例任务的完成条件
     * @return: void
     **/
    @Override
    public void notify(DelegateExecution delegateExecution){
        String eventName = delegateExecution.getEventName();
        System.out.println(eventName);
        // ActivitiEventType.PROCESS_STARTED
        if ("start".equals(eventName)) {
            // 流程开始
            logger.info("start......");
        } else if ("end".equals(eventName)) {
            // 流程结束
            logger.info("end......");
        } else if ("take".equals(eventName)) {
            // 连线监听器
            logger.info("take......");
        }
        delegateExecution.setVariable(taskKeyList.getExpressionText(), Arrays.asList(approver.split(",")));
        delegateExecution.setVariable(taskKey.getExpressionText(),"张三");
    }

    /**
     * @description:任务节点的监听
     *   任务节点的监听
     *   TaskListener类实现
     *   说明: usertask2节点配置了处理人所以触发assignment事件监听，
     *   当任务运转到usertask2的时候触发了create事件。 这里我们也可以得出一个结论：assignment事件比create先执行。
     *   任务完成的时候，触发complete事件，因为任务完成之后，要从ACT_RU_TASK中删除这条记录，所以触发delete事件
     * @author: Winjay
     * @date: 2022/10/17 10:53
     * @param delegateTask
     * @return: void
     **/
    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        // ActivitiEventType.PROCESS_STARTED
        if ("create".endsWith(eventName)) {
            logger.info("create=========");
        } else if ("assignment".endsWith(eventName)) {
            logger.info("assignment========");
        } else if ("complete".endsWith(eventName)) {
            logger.info("complete===========");
        } else if ("delete".endsWith(eventName)) {
            logger.info("delete=============");
        }
    }
}
