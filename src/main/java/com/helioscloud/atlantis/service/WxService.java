/*
 * Copyright (c) 2018. Shanghai Zhenhui Information Technology Co,. ltd.
 * All rights are reserved.
 */

package com.helioscloud.atlantis.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.helioscloud.atlantis.security.PrincipalLite;
import com.helioscloud.atlantis.domain.CompanyConfiguration;
import com.helioscloud.atlantis.domain.ConfigurationDetail;
import com.helioscloud.atlantis.dto.UserDTO;
import com.helioscloud.atlantis.exception.UserNotActivatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 钉钉业务逻辑实现类
 * Created by Strive on 17/8/31.
 */
@Service
public class WxService {

    private static Logger log = LoggerFactory.getLogger(WxService.class);

    @Value("${wechat.wechatUrl:}")
    private String wechatUrl;//微信中间件URL

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    public JSONObject authenticate(String code, String companyOid, String suiteId, String corpId, Map<String, String> var3) {
        JSONObject jsonResp = null;
        String retMsg;
        try {
            String url = wechatUrl + "authenticate";
            JSONObject jsonReq = buildBaseJson(companyOid, "JSSDK", corpId, suiteId);
            jsonReq.put("code", code);
            HttpURLConnection conn;

            conn = httpsPostJson(url);
            OutputStream os = conn.getOutputStream();
            os.write(jsonReq.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            InputStream sendStatus = conn.getInputStream();
            int size = sendStatus.available();
            byte[] jsonBytes = new byte[size];
            sendStatus.read(jsonBytes);
            retMsg = new String(jsonBytes, "UTF-8");
            log.info("retMsg:{}", retMsg);
            jsonResp = JSONObject.parseObject(retMsg);
            sendStatus.close();
            conn.disconnect();
        } catch (JSONException e) {
            throw new UserNotActivatedException("user.not.found");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        log.info("response:{}", jsonResp);
        return jsonResp;
    }

    public JSONObject buildBaseJson(String companyOID, String tokenType, String corpId, String suiteId) {
        JSONObject baseJson = new JSONObject();
        String secret = null;
        if (companyOID != null && !companyOID.trim().equals("")) {
            ConfigurationDetail.WxConfiguration wxConfiguration = getCompanyConfiguration(UUID.fromString(companyOID)).getConfiguration().getWxConfiguration();
            corpId = wxConfiguration.getCorpId();
            secret = wxConfiguration.getSecretKey();
        }
        baseJson.put("suiteId", suiteId == null ? "" : suiteId);
        baseJson.put("tokenType", tokenType);
        baseJson.put("corpId", corpId == null ? "" : corpId);
        baseJson.put("secret", secret == null ? "" : secret);
        baseJson.put("companyStr", companyOID);
        return baseJson;
    }

    public CompanyConfiguration getCompanyConfiguration(UUID companyOID) {
        List<CompanyConfiguration> companyConfiguration = companyService.findOneByCompanyOID(companyOID);
        if (!companyConfiguration.isEmpty()) {
            return companyConfiguration.get(0);
        } else {
            throw new IllegalArgumentException("companyOID is wrong");
        }

    }

    public HttpURLConnection httpsPostJson(String https_url) {
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(https_url);
//            conn = (HttpsURLConnection) url.openConnection();
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public UserDetails loadUserByUsername(String code, String suiteId, String corpId, String companyStr)
            throws UsernameNotFoundException {

           /*empCode是微信通讯录中的帐号,方案:
        1、保证微信通讯录中号码/邮箱的信息必须存在，可通过获取帐号调微信接口获取号码;
        2、数据库表中建立帐号与用户对应关系，由帐号确认用户身份*/
        JSONObject userInfo = authenticate(code, companyStr, suiteId, corpId, (Map) null);//员工在微信通讯录中的帐号
        UUID userOID = null;
        if (userInfo != null && userInfo.containsKey("errcode") && userInfo.getString("errcode").equals("0") && userInfo.containsKey("userOID")) {
            userOID = UUID.fromString(userInfo.getString("userOID"));
        } else {
            if (userInfo == null) {
                throw new UserNotActivatedException("user.not.found");
            }
            if (userInfo.containsKey("errmsg") && !userInfo.getString("errmsg").equals("")) {
                throw new UserNotActivatedException(userInfo.getString("errmsg"));
            }
        }

        UserDTO u = userService.findOneByUserOID(userOID);

        if (u == null) {
            //匹配保存失败，发邮件,根据公司OID获取公司管理员邮箱，发邮件。
//            dingTalkLoginServiceImpl.sendMatchErrorEmail(,mobile,email,dingtalkUserId,name);
            throw new UserNotActivatedException("user.not.found");
        }
        //1.用户是否被激活
        if (!u.isActivated()) {
            throw new UserNotActivatedException("user.not.activated");
        }
        //公共检查2.用户离职 3，用户锁定 4.密码过期
        userService.loginCommonCheck(u);

        return new PrincipalLite(u);
    }
}
