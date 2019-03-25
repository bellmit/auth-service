package com.hand.hcf.app.base.userRole.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.base.userRole.domain.PageList;
import com.hand.hcf.app.base.userRole.persistence.PageListMapper;
import com.hand.hcf.app.base.util.RespCode;
import com.hand.hcf.core.exception.BizException;
import com.hand.hcf.core.service.BaseI18nService;
import com.hand.hcf.core.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @version: 1.0
 * @author: xue.han@hand-china.com
 * @date: 2019/1/28
 */
@Service
@AllArgsConstructor
@Transactional
public class PageListService extends BaseService<PageListMapper,PageList>{
    private final PageListMapper pageListMapper;

    private final BaseI18nService baseI18nService;

    /**
     * 新增 页面
     * @param pageList
     * @return
     */
    public PageList createPageList(PageList pageList){
        if (pageList.getId() != null){
            throw new BizException(RespCode.PAGE_LIST_EXIST);
        }
        if (pageList.getPageName() == null){
            throw new BizException(RespCode.PAGE_LIST_PAGE_NAME_IS_NULL);
        }
        if (pageList.getFilePath() == null){
            throw new BizException(RespCode.PAGE_LIST_FILE_PATH_IS_NULL);
        }
        if (pageList.getPageRouter() == null){
            throw new BizException(RespCode.PAGE_LIST_PAGE_ROUTER_IS_NULL);
        }
        if (pageListMapper.selectList(
                new EntityWrapper<PageList>()
                        .eq("page_name",pageList.getPageName())
                        .eq("page_router",pageList.getPageRouter())
        ).size() > 0 ){
            throw new BizException(RespCode.PAGE_LIST_PAGE_ROUTER_REPEAT);
        }
        pageListMapper.insert(pageList);
        return pageListMapper.selectById(pageList);
    }

    /**
     * 逻辑删除 页面
     * @param id
     */
    public void deletePageListById(Long id){
        PageList pageList = pageListMapper.selectById(id);
        if (pageList == null){
            throw new BizException(RespCode.PAGE_LIST_NOT_EXIST);
        }
        pageList.setDeleted(true);
        pageListMapper.updateById(pageList);
    }

    /**
     * 修改 页面
     * @param pageList
     * @return
     */
    public PageList updatePageList(PageList pageList){
        if (pageList.getId() == null){
            throw new BizException(RespCode.PAGE_LIST_NOT_EXIST);
        }
        pageListMapper.updateById(pageList);
        return pageListMapper.selectById(pageList);
    }

    /**
     * 根据id查询 页面
     * @param id
     * @return
     */
    public PageList getPageListById(Long id){
        PageList pageList = pageListMapper.selectById(id);
        if (pageList == null){
            throw new BizException(RespCode.PAGE_LIST_NOT_EXIST);
        }
        return baseI18nService.selectOneTranslatedTableInfoWithI18nByEntity(pageList,PageList.class);
    }

    /**
     * 条件分页查询 页面
     * @param pageName
     * @param pageRouter
     * @param page
     * @return
     */
    public Page<PageList> getPageListByCond(String pageName,String pageRouter,Page page){
        Page<PageList> result = this.selectPage(page,
                new EntityWrapper<PageList>()
                        .eq("deleted",false)
                        .like(pageName != null,"page_name",pageName)
                        .like(pageRouter != null,"page_router",pageRouter)
                        .orderBy("last_updated_date",false)
        );
        if (result.getRecords().size() > 0) {
            result.setRecords(baseI18nService.selectListTranslatedTableInfoWithI18nByEntity(result.getRecords(),PageList.class));
        }
        return result;
    }
}
