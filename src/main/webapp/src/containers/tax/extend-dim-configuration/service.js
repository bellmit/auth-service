import config from 'config';
import httpFetch from 'share/httpFetch';

export default {
  // 新增价税分离
  insertInvoicingSite(params) {
    return httpFetch.post(`${config.taxUrl}/api/tax/vat/rule/dimension`, params);
  },

  // 修改价税分离
  updateInvoicingSite(params) {
    return httpFetch.put(`${config.taxUrl}/api/tax/vat/rule/dimension`, params);
  },
  // 删除价税分离
  delectInvoicingSite(id) {
    return httpFetch.delete(`${config.taxUrl}/api/tax/vat/rule/dimension/${id}`);
  },

  // 获取价税分离
  pageInvoicingSiteByCond() {
    return httpFetch.get(`${config.taxUrl}/api/tax/vat/rule/dimension/query/condition`);
  },
  // 获取维度下拉列表
  getSystemValueList() {
    return httpFetch.get(`${config.mdataUrl}/api/dimension/page/by/cond`);
  },
  // 获取维值下拉列表
  getSystemValueList1(id) {
    return httpFetch.get(
      `${
        config.mdataUrl
      }/api/dimension/item/page/by/dimensionId?roleType=TENANT&page=0&size=10&dimensionId=${id}&enabled=true`
    );
  },

  // 根据税种ID查询税率详情
  // pageTaxRateByTaxCategoryId(taxCategoryId) {
  //   return httpFetch.get(`${config.taxUrl}/api/tax/rate/pageTaxRateByTaxCategoryId/${taxCategoryId}`);
  // },
};