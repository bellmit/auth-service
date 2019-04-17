import React from 'react';
import { connect } from 'dva';
import {
  Form,
  Button,
  message,
  Badge,
  Popover,
  Dropdown,
  Icon,
  Menu,
  Row,
  Col,
  Input,
  InputNumber,
} from 'antd';
const Search = Input.Search;
import config from 'config';

import moment from 'moment';
import SearchArea from 'widget/search-area';
import prePaymentService from 'containers/pre-payment/my-pre-payment/me-pre-payment.service';
import 'styles/pre-payment/my-pre-payment/me-pre-payment.scss';
import CustomTable from 'widget/custom-table';
import { routerRedux } from 'dva/router';

const statusList = [
  { value: 1001, label: '编辑中' },
  { value: 1002, label: '审批中' },
  { value: 1003, label: '撤回' },
  { value: 1004, label: '审批通过' },
  { value: 1005, label: '审批驳回' },
];

class MyPrePayment extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      visible: false,
      setOfBooksId: null,
      searchForm: [
        {
          type: 'select',
          colSpan: '6',
          id: 'paymentReqTypeId',
          label: '单据类型',
          getUrl: `${config.prePaymentUrl}/api/cash/pay/requisition/types//queryAll?setOfBookId=${
            props.company.setOfBooksId
          }`,
          options: [],
          method: 'get',
          valueKey: 'id',
          labelKey: 'typeName',
          event: 'paymentReqTypeId',
        },
        {
          type: 'items',
          colSpan: '6',
          id: 'dateRange',
          items: [
            {
              type: 'date',
              id: 'requisitionDateFrom',
              label: '申请日期从',
              event: 'requisitionDateFrom',
            },
            {
              type: 'date',
              id: 'requisitionDateTo',
              label: '申请日期至',
              event: 'requisitionDateTo',
            },
          ],
        },
        {
          type: 'select',
          id: 'employeeId',
          label: '申请人',
          options: [],
          colSpan: 6,
        },
        {
          type: 'select',
          colSpan: '6',
          id: 'status',
          label: '状态',
          options: statusList,
          event: 'status',
        },
        {
          type: 'items',
          colSpan: '6',
          id: 'amountRange',
          items: [
            {
              type: 'inputNumber',
              id: 'advancePaymentAmountFrom',
              label: '本币金额从',
              event: 'advancePaymentAmountFrom',
            },
            {
              type: 'inputNumber',
              id: 'advancePaymentAmountTo',
              label: '本币金额至',
              event: 'advancePaymentAmountTo',
            },
          ],
        },
        {
          type: 'items',
          colSpan: '6',
          id: 'noWritedAmount',
          items: [
            {
              type: 'input',
              id: 'noWritedAmountFrom',
              label: '未核销金额从',
              event: 'noWritedAmountFrom',
            },
            {
              type: 'input',
              id: 'noWritedAmountTo',
              label: '未核销金额至',
              event: 'noWritedAmountTo',
            },
          ],
        },
        {
          type: 'input',
          colSpan: '6',
          id: 'description',
          label: '备注',
          event: 'description',
        },
      ],
      columns: [
        {
          title: '单号',
          dataIndex: 'requisitionNumber',
          width: 180,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          title: '单据类型',
          dataIndex: 'typeName',
          align: 'center',
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          title: '申请人',
          dataIndex: 'employeeName',
          width: 90,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          title: '申请日期',
          dataIndex: 'requisitionDate',
          width: 110,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={moment(desc).format('YYYY-MM-DD')}>
                {desc ? moment(desc).format('YYYY-MM-DD') : '-'}
              </Popover>
            </span>
          ),
        },
        {
          title: '本币金额',
          dataIndex: 'advancePaymentAmount',
          width: 110,
          align: 'center',
          render: desc => (
            <span className="money-cell">
              <Popover content={this.filterMoney(desc, 2)}>{this.filterMoney(desc, 2)}</Popover>
            </span>
          ),
        },
        {
          title: '已核销金额',
          dataIndex: 'writedAmount',
          width: 110,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={this.filterMoney(desc, 2)}>{this.filterMoney(desc, 2)}</Popover>
            </span>
          ),
        },
        {
          title: '未核销金额',
          dataIndex: 'noWritedAmount',
          width: 110,
          align: 'center',
          render: desc => (
            <span className="money-cell">
              <Popover content={this.filterMoney(desc, 2)}>{this.filterMoney(desc, 2)}</Popover>
            </span>
          ),
        },
        {
          title: '备注',
          dataIndex: 'description',
          align: 'center',
          render: value => {
            return <Popover content={value}>{value}</Popover>;
          },
        },
        {
          title: '状态',
          dataIndex: 'status',
          align: 'center',
          width: 110,
          render: (value, record) => {
            return (
              <Badge
                status={this.$statusList[value] && this.$statusList[value].state}
                text={this.$statusList[value] && this.$statusList[value].label}
              />
            );
          },
        },
      ],
      data: [],
      page: 0,
      pageSize: 10,
      pagination: {
        total: 0,
        showQuickJumper: true,
        showSizeChanger: true,
      },
      searchParams: {},
      // NewPayRequisition: menuRoute.getRouteItem('new-pre-payment', 'key'), //新建预付款
      // PayRequisitionDetail: menuRoute.getRouteItem('pre-payment-detail', 'key'), //预付款详情,
      // ContractDetail: menuRoute.getRouteItem('contract-detail', 'key'), //合同详情
      //预付款单类型集合
      prePaymentTypeMenu: [],
    };
  }

  componentDidMount() {
    this.getPrePaymentType();
  }
  /**
   * 获取预付款单类型
   */
  getPrePaymentType = () => {
    let params = {
      userId: this.props.user.id,
      isEnabled: true,
    };
    prePaymentService
      .getPrePaymentType(params)
      .then(res => {
        if (res.status === 200) {
          this.setState({
            prePaymentTypeMenu: res.data,
          });
        }
      })
      .catch(e => {
        message.error('获取预付款单类型失败');
        console.log(`获取预付款单类型失败：${e.response.data}`);
      });
    let searchForm = this.state.searchForm;
    //查询当前机构下所有已创建的预付款单的申请人（查询下拉框)
    prePaymentService
      .getCreatedUserList()
      .then(res => {
        searchForm[2].options = res.data.map(o => ({ value: o.id, label: o.fullName }));
        this.setState({ searchForm });
      })
      .catch(err => {
        message.error(err.response.data.message);
      });
  };

  /**
   * 选中预付款单类型
   */
  handleMenuClick = value => {
    const { dispatch } = this.props;
    let { prePaymentTypeMenu } = this.state;

    let currPrePaymentType = prePaymentTypeMenu.find(item => item.id === value.key);

    this.props.dispatch(
      routerRedux.replace({
        pathname: `/pre-payment/my-pre-payment/new-pre-payment/${0}/${currPrePaymentType.id}/${
          currPrePaymentType.formOid ? currPrePaymentType.formOid : 0
        }`,
      })
    );
  };
  change = e => {
    const { searchParams } = this.state;
    if (e && e.target && e.target.value) {
      searchParams.requisitionNumber = e.target.value;
    } else {
      searchParams.requisitionNumber = '';
    }
    this.setState({ searchParams });
  };
  eventHandle = (type, value) => {
    const { searchParams } = this.state;
    switch (type) {
      case 'requisitionDateFrom': {
        if (value) {
          searchParams.requisitionDateFrom = moment(value).format('YYYY-MM-DD');
        } else {
          searchParams.requisitionDateFrom = '';
        }
        break;
      }
      case 'requisitionDateTo': {
        if (value) {
          searchParams.requisitionDateTo = moment(value).format('YYYY-MM-DD');
        } else {
          searchParams.requisitionDateTo = '';
        }
        break;
      }
      default:
        searchParams[type] = value;
        break;
    }
    this.setState(searchParams);
  };

  //点击重置的事件，清空值为初始值
  handleReset = () => {
    this.clearSearchAreaSelectData();
    let { searchParams } = this.state;
    this.props.clearHandle && this.props.clearHandle();
    this.setState({ searchParams: {} });
  };

  //清除searchArea选择数据
  clearSearchAreaSelectData = () => {
    this.props.form.resetFields();
    this.state.checkboxListForm &&
      this.state.checkboxListForm.map(list => {
        if (!list.single) {
          list.items.map(item => {
            item.checked = [];
          });
        }
      });
  };
  //搜索
  search = values => {
    values.requisitionDateFrom &&
      (values.requisitionDateFrom = moment(values.requisitionDateFrom).format('YYYY-MM-DD'));
    values.requisitionDateTo &&
      (values.requisitionDateTo = moment(values.requisitionDateTo).format('YYYY-MM-DD'));
    this.setState({ searchParams: { ...this.state.searchParams, ...values } }, () => {
      this.customTable.search(this.state.searchParams);
    });
  };
  /**
   * 行点击事件
   */
  rowClick = record => {
    this.props.dispatch(
      routerRedux.replace({
        pathname: `/pre-payment/my-pre-payment/pre-payment-detail/${record.id}/prePayment`,
      })
    );
  };
  /**
   * 根据预付款单单号搜索
   */
  onDocumentSearch = value => {
    this.setState(
      {
        page: 0,
        searchParams: { ...this.state.searchParams, requisitionNumber: value },
      },
      () => {
        this.customTable.search(this.state.searchParams);
      }
    );
  };
  render() {
    const {
      visible,
      loading,
      searchForm,
      columns,
      data,
      pagination,
      prePaymentTypeMenu,
    } = this.state;
    return (
      <div className="pre-payment-container">
        <SearchArea
          searchForm={searchForm}
          submitHandle={this.search}
          eventHandle={this.eventHandle}
          clearHandle={this.handleReset}
          maxLength={4}
        />
        {/* <div className='divider'></div> */}
        <div style={{ marginBottom: 10, marginTop: 10 }}>
          <Row>
            <Col id="me-pre-payment-drop" style={{ position: 'relative' }} span={18}>
              <Dropdown
                getPopupContainer={() => document.getElementById('me-pre-payment-drop')}
                overlay={
                  <Menu onClick={this.handleMenuClick}>
                    {prePaymentTypeMenu.map(item => {
                      return <Menu.Item key={item.id}>{item.typeName}</Menu.Item>;
                    })}
                  </Menu>
                }
                trigger={['click']}
              >
                <Button type="primary">
                  新建预付款单 <Icon type="down" />
                </Button>
              </Dropdown>
            </Col>
            <Col span={6}>
              <Search
                placeholder="请输入预付款单单号"
                onChange={this.change}
                onSearch={this.onDocumentSearch}
                enterButton
              />
            </Col>
          </Row>
        </div>
        <CustomTable
          ref={ref => (this.customTable = ref)}
          columns={columns}
          onClick={this.rowClick}
          scroll={{ x: true, y: false }}
          params={{
            ...this.state.searchParams,
          }}
          url={`${config.prePaymentUrl}/api/cash/prepayment/requisitionHead/query`}
        />
      </div>
    );
  }
}
// MyPrePayment.contextTypes = {
//   router: React.PropTypes.object
// }
const wrappedMyPrePayment = Form.create()(MyPrePayment);

function mapStateToProps(state) {
  return {
    user: state.user.currentUser,
    company: state.user.company,
    language: state.languages.languages,
    languageList: state.languages.languageList,
  };
}
export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(wrappedMyPrePayment);