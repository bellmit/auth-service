/**
 * Created by 13576 on 2017/12/4.
 */
import React from 'react';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import {
  Form,
  Card,
  Input,
  Row,
  Col,
  Affix,
  Button,
  DatePicker,
  Select,
  InputNumber,
  message,
  Spin,
} from 'antd';
const FormItem = Form.Item;
const Option = Select.Option;
// import menuRoute from 'routes/menuRoute'
import config from 'config';
import httpFetch from 'share/httpFetch';
import moment from 'moment';
import Upload from 'widget/upload';
import Chooser from './chooser';
import prePaymentService from 'containers/pre-payment/my-pre-payment/me-pre-payment.service';
import Lov from 'widget/Template/lov';

class MyNewPrePayment extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      pageLoading: false,
      user: {},
      fetch: false,
      contractTypeDisabled: true,
      setOfBooksId: null,
      isNew: true, //新建 or 编辑
      data: [], //编辑的合同信息
      partnerCategoryOptions: [], //合同方类型选项
      currencyOptions: [], //币种
      companyIdOptions: [], //公司
      contractCategoryOptions: [], //合同大类选项
      uploadOids: [], //上传附件的Oids
      employeeOptions: [], //员工选项
      venderOptions: [], //供应商选项
      employeeIdOptions: [], //申请人
      unitIdOptions: [], //部门
      selectorItem: {},
      extraParams: null,
      departmentId: '',
      // PayRequisitionDetail: menuRoute.getRouteItem('pre-payment-detail', 'key'), //预付款详情
      // PrepaymentList: menuRoute.getRouteItem('me-pre-payment', 'key'),    //预付款列表页面
      model: {},
      fileList: [],
      userList: [],
    };
  }
  componentDidMount() {
    if (this.props.match.params.id !== '0') {
      prePaymentService.getHeadById(this.props.match.params.id).then(res => {
        let fileList = [];
        if (res.data.attachments) {
          res.data.attachments.map(item => {
            fileList.push({
              ...item,
              uid: item.attachmentOid,
              name: item.fileName,
              status: 'done',
            });
          });
        }
        this.setState({
          model: res.data,
          isNew: false,
          fetch: true,
          fileList,
        });
      });
    } else {
      httpFetch
        .get(`${config.mdataUrl}/api/departments/${this.props.user.departmentOid}`)
        .then(res => {
          this.setState({
            departmentId: res.data.id,
          });
        });
    }
    if (this.props.match.params.prePaymentTypeId) {
      this.listUserByTypeId(this.props.match.params.prePaymentTypeId);
    }
  }
  //上传附件
  handleUpload = Oids => {
    this.setState({
      uploadOids: Oids,
      attachmentOids: Oids,
    });
  };
  //保存
  handleSave = e => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        let dataValue;

        this.setState({ loading: true });

        if (this.state.isNew) {
          dataValue = {
            ...values,
            paymentReqTypeId: this.props.match.params.prePaymentTypeId,
            tenant_id: this.props.user.tenantId,
            employeeId: values.user.id,
            // companyId: this.props.user.companyId,
            // unitId: this.state.departmentId,
            formOid: this.props.match.params.formOid != 0 ? this.props.match.params.formOid : '',
            ifWorkflow: this.props.match.params.formOid != 0,
            advancePaymentAmount: 0,
            companyId: values.company[0].id,
            unitId: values.department[0].departmentId,
          };
        } else {
          dataValue = {
            ...this.state.model,
            ...values,
            tenant_id: this.props.user.tenantId,
            employeeId: values.user.id,
            // companyId: this.props.user.companyId,
            // unitId: this.state.departmentId,
            formOid: this.props.match.params.formOid != 0 ? this.props.match.params.formOid : '',
            ifWorkflow: this.props.match.params.formOid != 0,
            companyId: values.company[0].id,
            unitId: values.department[0].departmentId,
          };
        }
        //dataValue.attachmentOid = this.state.uploadOids.toString();
        dataValue.attachmentOids =
          this.state.uploadOids && this.state.uploadOids.length > 0
            ? this.state.uploadOids
            : dataValue.attachmentOids;
        prePaymentService
          .addPrepaymentHead(dataValue)
          .then(res => {
            this.setState({
              loading: false,
            });
            // this.context.router.push(this.state.PayRequisitionDetail.url.replace(':id', res.data.id));
            this.props.dispatch(
              routerRedux.push({
                pathname: `/pre-payment/my-pre-payment/pre-payment-detail/${res.data.id}/:flag`,
              })
            );
          })
          .catch(e => {
            message.error(`保存失败，${e.response.data.message}`);
            this.setState({
              loading: false,
            });
          });
      }
    });
  };
  //取消
  onCancel = () => {
    this.props.dispatch(
      routerRedux.push({
        pathname: `/pre-payment/my-pre-payment/my-pre-payment`,
      })
    );
  };

  userChange = user => {
    prePaymentService
      .getUserInfoByTypeId(user.userOid)
      .then(res => {
        let temp = res.data;
        let company = [{ id: temp.companyId, name: temp.companyName }];
        let department = [{ departmentId: temp.departmentId, path: temp.departmentName }];
        this.props.form.setFieldsValue({
          company: company,
          department: department,
        });
      })
      .catch(err => {
        message.error('请求失败，请稍后重试...');
      });
  };

  listUserByTypeId = typeId => {
    prePaymentService
      .listUserByTypeId(typeId)
      .then(res => {
        this.setState({ userList: res.data }, () => {
          if (res.data && res.data.length) {
            let user = res.data[0];
            this.userChange(user);
          }
        });
      })
      .catch(err => {
        message.error('请求失败，请稍后重试...');
      });
  };

  onBack = () => {
    this.props.dispatch(
      routerRedux.push({
        pathname: `/pre-payment/my-pre-payment/pre-payment-detail/${
          this.props.match.params.id
        }/prePayment`,
      })
    );
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const {
      model,
      loading,
      fetch,
      fileList,
      departmentId,
      pageLoading,
      userList,
      isNew,
    } = this.state;
    const rowLayout = { type: 'flex', gutter: 24, justify: 'center' };
    const formItemLayout = {
      labelCol: {
        xs: { span: 12 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    };

    let visible = this.props.match.params.id !== '0' ? this.props.match.params.id && fetch : true;
    return visible ? (
      <div className="new-contract " style={{ marginBottom: '10px' }}>
        <Spin spinning={pageLoading}>
          <Form onSubmit={this.handleSave}>
            <Row {...rowLayout}>
              <Col span={10}>
                <FormItem label="申请人" {...formItemLayout}>
                  {getFieldDecorator('user', {
                    rules: [{ required: true, message: '请选择' }],
                    initialValue: isNew
                      ? userList.length > 0
                        ? { id: userList[0].id, fullName: userList[0].fullName }
                        : { id: this.props.user.id, fullName: this.props.user.fullName }
                      : { id: model.employeeId, fullName: model.employeeName },
                  })(
                    <Lov
                      code="prepayment_user_authorize"
                      valueKey="id"
                      labelKey="fullName"
                      onChange={this.userChange}
                      allowClear={false}
                      single
                      extraParams={{ payReqTypeId: this.props.match.params.prePaymentTypeId }}
                    />
                  )}
                </FormItem>
              </Col>
            </Row>
            <Row {...rowLayout}>
              <Col span={10}>
                <FormItem label="公司" {...formItemLayout}>
                  {getFieldDecorator('company', {
                    rules: [
                      {
                        required: true,
                        message: this.$t('common.please.select'),
                      },
                    ],

                    initialValue: isNew
                      ? [{ id: this.props.user.companyId, name: this.props.user.companyName }]
                      : model.id
                        ? [{ id: model.companyId, name: model.companyName }]
                        : [],
                  })(
                    <Chooser
                      type="company"
                      labelKey="name"
                      valueKey="id"
                      single={true}
                      listExtraParams={{ setOfBooksId: this.props.company.setOfBooksId }}
                    />
                  )}
                </FormItem>
              </Col>
            </Row>
            <Row {...rowLayout}>
              <Col span={10}>
                <FormItem label="部门" {...formItemLayout}>
                  {getFieldDecorator('department', {
                    rules: [
                      {
                        required: true,
                        message: this.$t('common.please.select'),
                      },
                    ],
                    initialValue: isNew
                      ? [
                          {
                            departmentId: departmentId,
                            path: this.props.user.departmentName,
                          },
                        ]
                      : model.id
                        ? [
                            {
                              departmentId: model.unitId,
                              path: model.path,
                            },
                          ]
                        : [],
                  })(
                    <Chooser
                      type="department_document"
                      labelKey="path"
                      valueKey="departmentId"
                      single={true}
                      listExtraParams={{ tenantId: this.props.user.tenantId }}
                    />
                  )}
                </FormItem>
              </Col>
            </Row>
            <Row {...rowLayout}>
              <Col span={10}>
                <FormItem label="备注" {...formItemLayout}>
                  {getFieldDecorator('description', {
                    rules: [
                      {
                        required: true,
                        message: '请输入',
                      },
                    ],
                    initialValue: isNew ? '' : model.description,
                  })(<Input.TextArea placeholder="请输入" />)}
                </FormItem>
              </Col>
            </Row>

            <Row {...rowLayout} style={{ marginBottom: 40 }}>
              <Col span={10}>
                <FormItem label="附件信息" {...formItemLayout}>
                  {getFieldDecorator('attachmentOid')(
                    <Upload
                      attachmentType="BUDGET_JOURNAL"
                      uploadUrl={`${config.baseUrl}/api/upload/static/attachment`}
                      fileNum={9}
                      uploadHandle={this.handleUpload}
                      defaultFileList={fileList}
                      defaultOids={model.attachmentOids || []}
                    />
                  )}
                </FormItem>
              </Col>
            </Row>

            {/* <div style={{textAlign: "center"}}>
              <Button type="primary" htmlType="submit" loading={loading} style={{ margin: '0 20px' }}>{isNew ? "下一步" : "确定"}</Button>
              <Button onClick={this.onCancel}>取消</Button>
            </div> */}
            <Affix
              offsetBottom={0}
              style={{
                position: 'fixed',
                bottom: 0,
                marginLeft: '-35px',
                width: '100%',
                height: '50px',
                boxShadow: '0px -5px 5px rgba(0, 0, 0, 0.067)',
                background: '#fff',
                lineHeight: '50px',
              }}
            >
              <Button
                type="primary"
                htmlType="submit"
                loading={loading}
                style={{ margin: '0 20px' }}
              >
                {this.props.match.params.id === '0' ? '下一步' : '确定'}
              </Button>
              {this.props.match.params.id === '0' ? (
                <Button onClick={this.onCancel}>取消</Button>
              ) : (
                <Button onClick={this.onBack}>返回</Button>
              )}
            </Affix>
          </Form>
        </Spin>
      </div>
    ) : null;
  }
}
// MyNewPrePayment.contextTypes = {
//   router: React.PropTypes.object
// }
const wrappedMyNewPrePayment = Form.create()(MyNewPrePayment);

function mapStateToProps(state) {
  return {
    user: state.user.currentUser,
    company: state.user.company,
  };
}

export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(wrappedMyNewPrePayment);