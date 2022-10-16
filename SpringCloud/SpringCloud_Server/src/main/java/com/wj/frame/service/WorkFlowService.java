package com.wj.frame.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.activiti.engine.repository.Model;

/**
 * @PackageName: com.wj.frame.service
 * @InterfaceName: WorkFlowService
 * @Description: WorkFlowService接口服务类
 * @Author: Winjay
 * @Date: 2022-10-16 19:27:14
 */
public interface WorkFlowService {

    /**
     * 流程部署
     * @param id
     * @return
     */
    String deploy(String id);

    /**
     * 流程分页
     * @param pages
     * @return
     */
    IPage<Model> page(IPage<Model> pages);
}
