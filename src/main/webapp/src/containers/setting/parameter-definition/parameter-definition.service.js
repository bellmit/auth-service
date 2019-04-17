import config from 'config';
import httpFetch from 'share/httpFetch';

export default {
  //条件查询参数数据
  getParamByModuleCode(params) {
    return httpFetch.get(`${config.mdataUrl}/api/parameter/by/moduleCode`, params);
  },

  //获取租户下启用的·模块
  getModule() {
    return httpFetch.get(`${config.mdataUrl}/api/parameter/module`);
  },

  //获取模块代码下参数值
  getParamValues(params) {
    return httpFetch.get(
      `${config.mdataUrl}/api/parameter/values/valuaList/by/parameterValueType`,
      params
    );
  },

  //新建参数
  newParameter(params) {
    return httpFetch.post(`${config.mdataUrl}/api/parameter/setting`, params);
  },

  //更新参数
  updateParameter(params) {
    return httpFetch.put(`${config.mdataUrl}/api/parameter/setting`, params);
  },

  //删除参数
  deleteParameter(id) {
    return httpFetch.delete(`${config.mdataUrl}/api/parameter/setting/${id}`);
  },
};