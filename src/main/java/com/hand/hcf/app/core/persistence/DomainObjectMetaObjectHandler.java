

package com.hand.hcf.app.core.persistence;


import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.hand.hcf.app.core.util.LoginInformationUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.time.ZonedDateTime;

public class DomainObjectMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long currentUserId = getCurrentUserId();
        setValue("createdDate", ZonedDateTime.now(), metaObject);
        setValue("lastUpdatedDate", ZonedDateTime.now(), metaObject);
//        setValue("versionNumber", 1, metaObject);
        if (currentUserId != null) {
            setValue("createdBy", currentUserId, metaObject);
            setValue("lastUpdatedBy", currentUserId, metaObject);
        } else {
            setValue("createdBy", 0L, metaObject);
            setValue("lastUpdatedBy", 0L, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            setFieldValByName("lastUpdatedBy", currentUserId, metaObject);
        } else {
            setFieldValByName("lastUpdatedBy", 0L, metaObject);
        }
        setFieldValByName("lastUpdatedDate", ZonedDateTime.now(), metaObject);
    }

    private void setValue(String fieldName, Object value, MetaObject metaObject) {
        Object field = getFieldValByName(fieldName, metaObject);
        if (field == null && value != null) {
            setFieldValByName(fieldName, value, metaObject);
        }
    }

    private Long getCurrentUserId() {

        Long currentUserID = null;
        try {
            currentUserID = LoginInformationUtil.getCurrentUserId();
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentUserID;
    }
}
