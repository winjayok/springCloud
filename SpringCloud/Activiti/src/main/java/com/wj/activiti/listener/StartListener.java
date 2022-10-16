package com.wj.activiti.listener;

import org.activiti.engine.delegate.*;
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
public class StartListener implements ExecutionListener {

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
     * isSequential，为true表示是串行多实例，false表示是并行多实例
     * activiti:collection，表示多实例的人员集合，${leaderList}是取变量leaderList的值
     * activiti:elementVariable，人员集合中的元素变量，这里要UserTask中activiti:assignee的值对应，这里变量名称都是leader
     * nrOfInstances，实例总数（nr是number的简写）
     *  nrOfCompletedInstances，已完成的实例数
     * completionCondition，表示多实例任务的完成条件
     * ————————————————
     * @param delegateExecution
     */
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String assignee = delegateExecution.getEventName();
        System.out.println(assignee);

        delegateExecution.setVariable(taskKeyList.getExpressionText(), Arrays.asList(approver.split(",")));
        delegateExecution.setVariable(taskKey.getExpressionText(),"张三");
    }
}
