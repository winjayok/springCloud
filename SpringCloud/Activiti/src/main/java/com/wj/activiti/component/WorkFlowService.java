package com.wj.activiti.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.wj.activiti.component
 * @ClassName: WorkFlowUtils
 * @Description: 流程工具类
 * @Author: Winjay
 * @Date: 2022-10-16 23:33:44
 */
@Component
public class WorkFlowService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowService.class);

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
     * @description:流程部署
     * @author: Winjay
     * @date: 2022/10/17 11:20
     * @param id
     * @return: java.lang.String
     **/
    public String deploy(String id) {
        try {
            Model modelData = repositoryService.getModel(id);
            JsonNode modelNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model, "UTF-8");//在此处转为GBK才可以,可以解决奇数中文的问题,不知为什么.
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
            return deployment.getId();
        } catch (Exception e) {
            LOGGER.error("流程部署异常", e);
            return null;
        }
    }


    /**
     * 流程启动
     * @return
     */
    public boolean start(){
        //①key：流程key
        //②businesskey：业务表ID，例如请假表，包含了请假人，起止时间等。
        //③vars：流程变量
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process_leave");
        //输出相关信息
        System.out.println("流程部署id" + processInstance.getDeploymentId());
        System.out.println("流程定义id" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例id" + processInstance.getId());
        System.out.println("活动id" + processInstance.getActivityId());
        return true;
    }

    /**
     * @description:执行任务
     * @author: Winjay
     * @date: 2022/10/17 11:23
     * @param assignee
     * @return: boolean
     **/
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

    /**
     * 流程是否已经结束
     *
     * @param processInstanceId 流程实例ID
     * @return
     */
    public boolean isFinished(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().finished().processInstanceId(processInstanceId).count() > 0;
    }


    /**
     * @description:输出流程跟踪图片
     * @author: Winjay
     * @date: 2022/10/17 11:30
     * @param executionId
     * @param out
     * @return: void
     **/
    public void processTracking(String executionId, ServletOutputStream out) throws IOException {
        // 当前活动节点、活动线
        List<String> activeActivityIds = new ArrayList<String>(), highLightedFlows = new ArrayList<String>();

        /**
         * 获得当前活动的节点
         */
        String processDefinitionId = null;
        if (this.isFinished(executionId)) {// 如果流程已经结束，则得到结束节点
            activeActivityIds.add(historyService.createHistoricActivityInstanceQuery().executionId(executionId).activityType("endEvent").singleResult().getActivityId());
            processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(executionId).singleResult().getProcessDefinitionId();
        } else {// 如果流程没有结束，则取当前活动节点
            // 根据流程实例ID获得当前处于活动状态的ActivityId合集
            activeActivityIds = runtimeService.getActiveActivityIds(executionId);
            Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        /**
         * 获得当前活动的节点-结束
         */

        /**
         * 获得活动的线
         */
        // 获得历史活动记录实体（通过启动时间正序排序，不然有的线可以绘制不出来）
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().executionId(executionId).orderByHistoricActivityInstanceStartTime().asc().list();
        // 计算活动线
        highLightedFlows = this.getHighLightedFlows((ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId), historicActivityInstances);
        /**
         * 获得活动的线-结束
         */

        /**
         * 绘制图形
         */
        if (null != activeActivityIds) {
            InputStream imageStream = null;
            try {
                // 获得流程引擎配置
                ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
                // 根据流程定义ID获得BpmnModel
                BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
                // 输出资源内容到相应对象
                imageStream = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, "png", activeActivityIds,
                        highLightedFlows, processEngineConfiguration.getActivityFontName(),
                        processEngineConfiguration.getLabelFontName(),
                        processEngineConfiguration.getAnnotationFontName(),
                        processEngineConfiguration.getClassLoader(),
                        1.0);
                IOUtils.copy(imageStream, out);
            } finally {
                IOUtils.closeQuietly(imageStream);
            }
        }
    }

    /**
     * 获得高亮线
     *
     * @param processDefinitionEntity   流程定义实体
     * @param historicActivityInstances 历史活动实体
     * @return 线ID集合
     */
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size(); i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());// 得 到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            if ((i + 1) >= historicActivityInstances.size()) {
                break;
            }
            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());// 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {// 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

    /**
     * 清空指定活动节点流向
     *
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    public List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    /**
     * 还原指定活动节点流向
     *
     * @param activityImpl         活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */
    public void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }
}
