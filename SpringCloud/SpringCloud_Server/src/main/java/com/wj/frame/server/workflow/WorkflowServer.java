package com.wj.frame.server.workflow;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wj.activiti.component.WorkFlowService;
import com.wj.baseUtils.Result;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByCreateTime().desc();
        List<Model> models = modelQuery.listPage(Integer.valueOf(String.valueOf(pages.getCurrent())), Integer.valueOf(String.valueOf(pages.getSize())));
        pages.setRecords(models);
        return pages;
    }


    @RequestMapping("/test/start")
    public Result start(){
        workFlowService.start();
        return Result.ok();
    }

    @RequestMapping("/test/next")
    public Result next(String assignee){
        workFlowService.completeTask(assignee);
        return Result.ok();
    }

    @RequestMapping("/process/trace/{executionId}")
    public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response) throws Exception {
        //设置返回的文件类型
        response.setContentType("image/jpg");
        workFlowService.processTracking(executionId, response.getOutputStream());
    }
}
