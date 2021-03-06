package com.hand.hcf.app.base.system.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hand.hcf.app.base.system.domain.Application;
import com.hand.hcf.app.base.system.domain.FrontLocale;
import com.hand.hcf.app.base.system.dto.LocaleDTO;
import com.hand.hcf.app.base.system.persistence.FrontLocaleMapper;
import com.hand.hcf.app.base.tenant.domain.Tenant;
import com.hand.hcf.app.base.tenant.service.TenantService;
import com.hand.hcf.app.base.util.RespCode;
import com.hand.hcf.app.core.exception.BizException;
import com.hand.hcf.app.core.service.BaseService;
import com.hand.hcf.app.core.util.LoginInformationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @version: 1.0
 * @author: xue.han@hand-china.com
 * @date: 2019/3/12
 */
@Service
public class FrontLocaleService extends BaseService<FrontLocaleMapper,FrontLocale>{
    @Autowired
    private  FrontLocaleMapper frontLocaleMapper;
    @Autowired
    private  TenantService tenantService;
    @Autowired
    private ApplicationService applicationService;
    /**
     * 单个新增 前端多语言
     * @param frontLocale
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public FrontLocale createFrontLocale(FrontLocale frontLocale){
        Long tenantId = LoginInformationUtil.getCurrentTenantId();
        Tenant tenant = tenantService.getTenantById(tenantId);
        if (frontLocale.getId() != null){
            throw new BizException(RespCode.FRONT_LOCALE_EXIST);
        }
        if ( frontLocaleMapper.selectList(
                new EntityWrapper<FrontLocale>()
                        .eq("key_code",frontLocale.getKeyCode().trim())
                        .eq("language",frontLocale.getLanguage())
                        .eq("tenant_id",tenantId)
        ).size() > 0 ){
            throw new BizException(RespCode.FRONT_LOCALE_KEY_CODE_NOT_ALLOWED_TO_REPEAT);
        }
        //如果是系统级租户添加前端多语言则同时插入所有租户
        if (tenant.getSystemFlag()){
            List<Tenant> tenants = tenantService.findAll();
            tenants.stream().forEach(domain -> {
                frontLocale.setId(null);
                frontLocale.setTenantId(domain.getId());
                // 去除空格
                frontLocale.setKeyCode(frontLocale.getKeyCode().trim());
                frontLocaleMapper.insert(frontLocale);
            });
        }else{
            frontLocale.setTenantId(tenantId);
            // 去除空格
            frontLocale.setKeyCode(frontLocale.getKeyCode().trim());
            frontLocaleMapper.insert(frontLocale);
        }
        return frontLocale;
    }

    /**
     * 批量新增 前端多语言
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<FrontLocale> createFrontLocaleBatch(List<FrontLocale> list){
        List<FrontLocale> result = new ArrayList<>();
        list.stream().forEach(frontLocale -> {
            FrontLocale frontLocaleTemp = createFrontLocale(frontLocale);
            result.add(frontLocaleTemp);
        });
        return result;
    }

    /**
     * 单个编辑 前端多语言
     * @param frontLocale
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public FrontLocale updateFrontLocale(FrontLocale frontLocale){
        if (frontLocale.getId() == null){
            throw new BizException(RespCode.FRONT_LOCALE_NOT_EXIST);
        }
        // 去除空格
        frontLocale.setKeyCode(frontLocale.getKeyCode().trim());
        frontLocaleMapper.updateAllColumnById(frontLocale);
        return frontLocale;
    }

    /**
     * 批量编辑 前端多语言
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<FrontLocale> updateFrontLocaleBatch(List<FrontLocale> list){
        List<FrontLocale> result = new ArrayList<>();
        list.stream().forEach(frontLocale -> {
            FrontLocale frontLocaleTemp = updateFrontLocale(frontLocale);
            result.add(frontLocaleTemp);
        });
        return result;
    }

    /**
     * 单个删除 前端多语言
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteFrontLocaleById(Long id){
        FrontLocale frontLocale = frontLocaleMapper.selectById(id);
        if (frontLocale == null){
            throw new BizException(RespCode.FRONT_LOCALE_NOT_EXIST);
        }
        deleteById(id);
    }

    /**
     * 单个查询 前端多语言
     * @param id
     * @return
     */
    public FrontLocale getFrontLocaleById(Long id){
        FrontLocale frontLocale = frontLocaleMapper.selectById(id);
        if (frontLocale == null){
            throw new BizException(RespCode.FRONT_LOCALE_NOT_EXIST);
        }
        return frontLocale;
    }

    /**
     * 分页查询 前端多语言
     * @param language 语言
     * @param applicationId 应用ID
     * @param keyCode 界面key值
     * @param keyDescription key描述
     * @param page
     * @return
     */
    public List<FrontLocale> getFrontLocaleByCond(String language, Long applicationId, String keyCode, String keyDescription, Page page) {
        Long tenantId = LoginInformationUtil.getCurrentTenantId();
        List<FrontLocale> result = frontLocaleMapper.selectPage(page,
                new EntityWrapper<FrontLocale>()
                        .eq(language != null,"language",language)
                        .eq(applicationId != null,"application_id",applicationId)
                        .like(keyCode != null,"key_code",keyCode)
                        .like(keyDescription != null,"key_description",keyDescription)
                        .eq(tenantId != null,"tenant_id",tenantId)
                        .orderBy("key_code")
        );
        return result;
    }

    /**
     * 不分页查询 map形式的前端多语言
     * @param language
     * @param applicationId
     * @return
     */
    public Map<String,String> mapFrontLocaleByCond(String language,Long applicationId){
        Map<String,String> mapFrontLocale = new HashMap<>();
        Long tenantId = LoginInformationUtil.getCurrentTenantId();
        List<FrontLocale> frontLocaleList = frontLocaleMapper.selectList(
                new EntityWrapper<FrontLocale>()
                        .eq(language != null,"language",language)
                        .eq(applicationId != null,"application_id",applicationId)
                        .eq(tenantId != null,"tenant_id",tenantId)
                        .orderBy("key_code")
        );
        if (!CollectionUtils.isEmpty(frontLocaleList)){
            frontLocaleList.stream().forEach(frontLocale -> {
                mapFrontLocale.put(frontLocale.getKeyCode(),frontLocale.getKeyDescription());
            });
        }

        return mapFrontLocale;
    }

    /**
     * 分页查询 前端多语言(返回外文描述信息)
     * @param applicationId
     * @param sourceLanguage
     * @param targetLanguage
     * @param keyCode
     * @param page
     * @return
     */
    public List<LocaleDTO> getOtherFrontLocaleByCond(Long applicationId, String sourceLanguage, String targetLanguage, String keyCode, Page page) {
        List<LocaleDTO> localeDTOList = new ArrayList<>();
        Long tenantId = LoginInformationUtil.getCurrentTenantId();
        List<FrontLocale> frontLocaleList = frontLocaleMapper.selectPage(page,
                new EntityWrapper<FrontLocale>()
                        .eq(applicationId != null, "application_id", applicationId)
                        .eq(sourceLanguage != null, "language", sourceLanguage)
                        .like(keyCode != null, "key_code", keyCode)
                        .eq(tenantId != null,"tenant_id",tenantId)
        );

        if (!CollectionUtils.isEmpty(frontLocaleList)){
            frontLocaleList.stream().forEach(sourceFrontLocale -> {
                FrontLocale targetFrontLocale = this.selectOne(
                        new EntityWrapper<FrontLocale>()
                                .eq("application_id", sourceFrontLocale.getApplicationId())
                                .eq("application_code", sourceFrontLocale.getApplicationCode())
                                .eq("key_code", sourceFrontLocale.getKeyCode())
                                .eq("language", targetLanguage)
                                .eq(tenantId != null,"tenant_id",tenantId)
                );

                LocaleDTO localeDTO = LocaleDTO.builder()
                        .keyCode(sourceFrontLocale.getKeyCode())
                        .sourceId(sourceFrontLocale.getId())
                        .sourceKeyDescription(sourceFrontLocale.getKeyDescription())
                        .build();

                if (targetFrontLocale != null){
                    localeDTO.setTargetId(targetFrontLocale.getId());
                    localeDTO.setTargetKeyDescription(targetFrontLocale.getKeyDescription());
                    localeDTO.setTargetVersionNumber(targetFrontLocale.getVersionNumber());
                }else {
                    localeDTO.setTargetId(null);
                    localeDTO.setTargetKeyDescription(null);
                }
                localeDTOList.add(localeDTO);
            });
        }
        return localeDTOList;
    }

    /**
     * 前端多语言数据初始化
     * @param tenantId
     */
    @Transactional(rollbackFor = Exception.class)
    public void initFrontLocale(Long tenantId){
        Long systemTenantId = tenantService.getSystemTenantId();
        List<FrontLocale> frontLocaleList = frontLocaleMapper.selectList(
                new EntityWrapper<FrontLocale>()
                        .eq(tenantId != null,"tenant_id",systemTenantId)
        );
        if (!frontLocaleList.isEmpty()) {
            frontLocaleList.forEach(frontLocale -> {
                frontLocale.setId(null);
                frontLocale.setTenantId(tenantId);
            });
            this.insertBatch(frontLocaleList);
        }
    }

    /**
     * 新增前端多语言给所有的租户，存在就删除
     * @param list
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertAllTenant(List<FrontLocale> list) {
        List<Tenant> all = tenantService.findAll();
        List<Application> applications = applicationService.listAll(null, null);
        Map<String, Application> appMap = applications.stream().collect(Collectors.toMap(Application::getAppCode, e -> e));
        List<FrontLocale> result;
        for (FrontLocale frontLocale : list) {
            this.delete(getWrapper()
                    .eq("key_code", frontLocale.getKeyCode())
                    .eq("language", frontLocale.getLanguage()));
            if (appMap.containsKey(frontLocale.getApplicationCode())) {
                result = all.stream().map(e -> {
                    FrontLocale v = FrontLocale.builder()
                            .applicationCode(frontLocale.getApplicationCode())
                            .applicationId(appMap.get(frontLocale.getApplicationCode()).getId())
                            .keyCode(frontLocale.getKeyCode())
                            .keyDescription(frontLocale.getKeyDescription())
                            .language(frontLocale.getLanguage())
                            .tenantId(e.getId()).build();
                    v.setDeleted(false);
                    return v;
                }).collect(Collectors.toList());
                this.insertBatch(result);
            }
        }
    }
}
