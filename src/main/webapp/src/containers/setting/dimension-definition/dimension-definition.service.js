import config from 'config';
import httpFetch from 'share/httpFetch';

export default {
  /**
   * 获取列表
   * @param {*} params
   */
  getDimensionSettingList(params) {
    return httpFetch.get(`${config.mdataUrl}/api/dimension/page/by/cond`, params);
  },
  /**
   * 创建维度
   * @param {*} params
   */
  addDimensionSetting(params) {
    return httpFetch.post(`${config.mdataUrl}/api/dimension`, params);
  },
  /**
   * 更改维度
   * @param {*} params
   */
  editDimensionSetting(params) {
    return httpFetch.put(`${config.mdataUrl}/api/dimension`, params);
  },
  /**
   * 删除参数设置
   * @param {*} params
   */
  deleteDimensionSetting(dimensionId) {
    return httpFetch.delete(`${config.mdataUrl}/api/dimension/${dimensionId}`);
  },
  /**
   *条件查询
   */
  queryDimensionSetting(params) {
    return httpFetch.get(`${config.mdataUrl}/api/dimension/page/by/cond`, params);
  },
  /**
   * 查询维度序号
   */
  NumberDimensionSetting(setOfBooksId) {
    return httpFetch.get(
      `${config.mdataUrl}/api/dimension/list/unselected/sequence/by/${setOfBooksId}`
    );
  },
};