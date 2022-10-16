package com.wj.frame.web.controller.workflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @PackageName: com.wj.activiti.model.controller
 * @ClassName: WorkflowController
 * @Description: 流程控制器
 * @Author: Winjay
 * @Date: 2022-10-16 15:56:09
 */
@Controller
@RequestMapping("/workflow")
public class WorkflowWeb {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 创建模型
     * @param request
     * @param response
     */
    @RequestMapping("/create")
    public void create(HttpServletRequest request, HttpServletResponse response,String modelId) {
        try {
            if(StringUtils.isNotBlank(modelId)){
                response.sendRedirect(request.getContextPath() + "/static/modeler.html?modelId=" + modelId);
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "流程模型名称");
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "流程模型描述");

            Model modelData = repositoryService.newModel();
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName("自定义审批流");
            modelData.setKey(UUID.randomUUID().toString());
            //保存模型
            repositoryService.saveModel(modelData);

            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
            response.sendRedirect(request.getContextPath() + "/static/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            System.out.println("创建模型失败：");
        }
    }
}
