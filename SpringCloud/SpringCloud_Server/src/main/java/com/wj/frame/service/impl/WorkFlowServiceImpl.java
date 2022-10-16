package com.wj.frame.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wj.frame.service.WorkFlowService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName: com.wj.frame.service.impl
 * @ClassName: WorkFlowServiceImpl
 * @Description: WorkFlowServiceImpl
 * @Author: Winjay
 * @Date: 2022-10-16 19:27:57
 */
@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Autowired
    private RepositoryService repositoryService;

    @Override
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
            LOGGER.error(getClass().getCanonicalName(), e);
            return null;
        }
    }

    @Override
    public IPage<Model> page(IPage<Model> pages) {
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByCreateTime().desc();
       // List<Model> models = modelQuery.listPage(Integer.valueOf(String.valueOf(pages.getCurrent())), Integer.valueOf(String.valueOf(pages.getSize())));
        List<Model> models = modelQuery.listPage(1,1);
        pages.setRecords(models);
        return pages;
    }
}
