package com.wj.frame.server.workflow;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wj.baseUtils.Result;
import com.wj.frame.service.WorkFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * properties-assignment-controller.js 可以指定用户配置页面模板
 */

/**
 * @PackageName: com.wj.activiti.model.controller
 * @ClassName: WorkflowController
 * @Description: 流程控制器
 * @Author: Winjay
 * @Date: 2022-10-16 15:56:09
 */
@RestController
@RequestMapping("/workflow")
public class WorkflowServer {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private WorkFlowService workFlowService;

    /**
     * 流程部署
     */
    @RequestMapping(value = "/deploy/{modelId}")
    public Result deploy(@PathVariable("modelId") String modelId) throws Exception {
        String deployId = this.workFlowService.deploy(modelId);
        return Result.ok();
    }

    /**
     * 删除流程
     * @param modelIds
     */
    @RequestMapping("/delete")
    public Result delete(@RequestParam(name = "ids[]") String[] modelIds){
        for (int i = 0; i < modelIds.length; i++) {
            repositoryService.deleteModel(modelIds[i]);
        }
        return Result.ok();
    }

    /**
     * 流程分页
     * @param limit
     * @param page
     * @return
     */
    @RequestMapping(value = "/modelPage")
    public IPage<Model> modelList(
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "1", value = "page") int page) {
        IPage<Model> pages = new Page<>(page,limit);
        return  this.workFlowService.page(pages);
    }


}
