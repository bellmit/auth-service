import React from 'react';
import { connect } from 'dva';
import { Form, Input, Tabs, Badge, Popover, Col, Row } from 'antd';
const TabPane = Tabs.TabPane;
import config from 'config';
import { routerRedux } from 'dva/router';
import SearchArea from 'widget/search-area';
import moment from 'moment';
import CustomTable from 'widget/custom-table';
const Search = Input.Search;

class ExpenseAdjustApprove extends React.Component {
  constructor(props) {
    super(props);
    const status = {
      1001: { label: this.$t('common.editing'), state: 'default' },
      1004: { label: this.$t('common.approve.pass'), state: 'success' },
      1002: { label: this.$t('common.approving'), state: 'processing' },
      1005: { label: this.$t('common.approve.rejected'), state: 'error' },
      1003: { label: this.$t('common.withdraw'), state: 'warning' },
    };
    this.state = {
      tabValue: 'unapproved',
      loading1: false,
      loading2: false,
      SearchForm1: [
        {
          type: 'select',
          options: [],
          id: 'expAdjustTypeId',
          label: this.$t('epx.adjust.receipt.type'),
          labelKey: 'expAdjustTypeName',
          colSpan: 6,
          valueKey: 'id',
          getUrl: `${config.expenseUrl}/api/expense/adjust/types/document/query`,
          method: 'get',
          getParams: { setOfBooksId: this.props.company.setOfBooksId, userId: this.props.user.id },
        },
        {
          type: 'select',
          id: 'adjustTypeCategory',
          label: this.$t('exp.adjust.type'),
          colSpan: 6,
          options: [
            { label: this.$t('exp.adjust.exp.detail'), value: '1001' },
            { label: this.$t('exp.adjust.exp.add'), value: '1002' },
          ],
        },
        {
          //申请人
          type: 'list',
          listType: 'select_authorization_user',
          options: [],
          id: 'userId',
          label: this.$t('exp.adjust.applier'),
          labelKey: 'userName',
          valueKey: 'userId',
          single: true,
          colSpan: 6,
        },
        {
          type: 'items',
          id: 'dateRange',
          colSpan: 6,
          items: [
            {
              type: 'date',
              id: 'beginDate',
              label: this.$t({ id: 'contract.search.date.from' } /*提交时间从*/),
            },
            {
              type: 'date',
              id: 'endDate',
              label: this.$t({ id: 'contract.search.date.to' } /*提交时间至*/),
            },
          ],
        },
        {
          type: 'select',
          key: 'currency',
          id: 'currencyCode',
          label: this.$t('common.currency'),
          getUrl: `${config.mdataUrl}/api/currency/rate/company/standard/currency/getAll`,
          options: [],
          method: 'get',
          labelKey: 'currency',
          valueKey: 'currency',
          colSpan: 6,
        },
        {
          type: 'items',
          id: 'amount',
          colSpan: 6,
          items: [
            { type: 'input', id: 'amountMin', label: this.$t('exp.money.from') },
            { type: 'input', id: 'amountMax', label: this.$t('exp.money.to') },
          ],
        },
        {
          type: 'input',
          id: 'description',
          label: this.$t('common.comment'),
          colSpan: 6,
        },
      ],
      SearchForm2: [
        {
          type: 'select',
          options: [],
          id: 'expAdjustTypeId',
          label: this.$t('epx.adjust.receipt.type'),
          labelKey: 'expAdjustTypeName',
          colSpan: 6,
          valueKey: 'id',
          getUrl: `${config.expenseUrl}/api/expense/adjust/types/document/query`,
          method: 'get',
          getParams: { setOfBooksId: this.props.company.setOfBooksId, userId: this.props.user.id },
        },
        {
          type: 'select',
          id: 'adjustTypeCategory',
          label: this.$t('exp.adjust.type'),
          colSpan: 6,
          options: [
            { label: this.$t('exp.adjust.exp.detail'), value: '1001' },
            { label: this.$t('exp.adjust.exp.add'), value: '1002' },
          ],
        },
        {
          type: 'list',
          listType: 'select_authorization_user',
          options: [],
          id: 'userId',
          label: this.$t('exp.adjust.applier'),
          labelKey: 'userName',
          valueKey: 'userId',
          single: true,
          colSpan: 6,
        },
        {
          type: 'items',
          id: 'dateRange',
          colSpan: 6,
          items: [
            {
              type: 'date',
              id: 'beginDate',
              label: this.$t({ id: 'contract.search.date.from' } /*提交时间从*/),
            },
            {
              type: 'date',
              id: 'endDate',
              label: this.$t({ id: 'contract.search.date.to' } /*提交时间至*/),
            },
          ],
        },
        {
          type: 'select',
          key: 'currency',
          id: 'currencyCode',
          label: this.$t('common.currency'),
          getUrl: `${config.mdataUrl}/api/currency/rate/company/standard/currency/getAll`,
          options: [],
          method: 'get',
          labelKey: 'currency',
          valueKey: 'currency',
          colSpan: 6,
        },
        {
          type: 'items',
          id: 'amount',
          colSpan: 6,
          items: [
            { type: 'input', id: 'amountMin', label: this.$t('exp.money.from') },
            { type: 'input', id: 'amountMax', label: this.$t('exp.money.to') },
          ],
        },
        {
          type: 'input',
          id: 'description',
          label: this.$t('common.comment'),
          colSpan: 6,
        },
      ],
      searchParams: {},
      columns: [
        {
          //单据编号
          title: this.$t('common.document.code'),
          dataIndex: 'documentNumber',
          width: 150,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          //单据类型
          title: this.$t('exp.receipt.type'),
          dataIndex: 'typeName',
          align: 'center',
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          title: this.$t('exp.adjust.type'),
          dataIndex: 'adjustTypeCategory',
          align: 'center',
          width: 100,
          render: desc => (
            <span>
              <Popover
                content={
                  desc === '1001' ? this.$t('exp.adjust.exp.detail') : this.$t('exp.adjust.exp.add')
                }
              >
                {desc === '1001' ? this.$t('exp.adjust.exp.detail') : this.$t('exp.adjust.exp.add')}
              </Popover>
            </span>
          ),
        },
        {
          title: this.$t('exp.adjust.applier'),
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
          //申请日期
          title: this.$t('exp.adjust.apply.date'),
          dataIndex: 'adjustDate',
          width: 100,
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
          //币种
          title: this.$t('common.currency'),
          dataIndex: 'currencyCode',
          align: 'center',
          width: 80,
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          title: this.$t('common.amount'),
          dataIndex: 'totalAmount',
          width: 110,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={this.filterMoney(desc, 2)}>{this.filterMoney(desc, 2)}</Popover>
            </span>
          ),
        },
        {
          title: this.$t('request.base.amount'),
          dataIndex: 'functionalAmount',
          width: 110,
          align: 'center',
          render: desc => (
            <span>
              <Popover content={this.filterMoney(desc, 2)}>{this.filterMoney(desc, 2)}</Popover>
            </span>
          ),
        },
        {
          title: this.$t('common.comment'),
          dataIndex: 'description',
          align: 'left',
          render: desc => (
            <span>
              <Popover content={desc}>{desc ? desc : '-'}</Popover>
            </span>
          ),
        },
        {
          title: this.$t('common.column.status'),
          dataIndex: 'status',
          align: 'center',
          width: 100,
          render: (value, record) => {
            return <Badge status={status[value].state} text={status[value].label} />;
          },
        },
      ],
      // columns: [//单据编号
      //     { title: this.$t('common.document.code'), dataIndex: 'documentNumber', width: 180, align:'center',
      //       render: (desc,record)=><span><Popover content={record.expenseAdjustApprovalView.documentNumber}>{record.expenseAdjustApprovalView.documentNumber? record.expenseAdjustApprovalView.documentNumber : "-"}</Popover></span>
      //     },
      //     { title: this.$t('exp.receipt.type'), dataIndex: 'typeName', align:'center',
      //       render: (desc,record)=><span><Popover content={record.expenseAdjustApprovalView.typeName}>{record.expenseAdjustApprovalView.expAdjustTypeName? record.expenseAdjustApprovalView.expAdjustTypeName : ""}</Popover></span>
      //     },  //调整类型
      //     { title: this.$t('exp.adjust.type'), dataIndex: 'adjustTypeCategory', align:'center', width: 100,
      //       render: (desc,record)=><span><Popover content={record.expenseAdjustApprovalView.adjustTypeCategory === '1001'? this.$t('exp.adjust.exp.detail'): this.$t('exp.adjust.exp.add')}>{record.expenseAdjustApprovalView.adjustTypeCategory === '1001'? this.$t('exp.adjust.exp.detail'): this.$t('exp.adjust.exp.add')}</Popover></span>
      //     },
      //     { title: this.$t('exp.adjust.applier'), dataIndex: 'employeeName', width: 100 , align: 'center',
      //       render: (desc,record) =><Popover content={record.expenseAdjustApprovalView.employeeName && record.expenseAdjustApprovalView.employeeName}>
      //         {record.expenseAdjustApprovalView.employeeName ? record.expenseAdjustApprovalView.employeeName : '-'}
      //       </Popover>
      //     },  //提交日期
      //     { title: this.$t('common.submit.date'), dataIndex: 'adjustDate', align: 'center', width: 90,
      //       render: (desc, record) => {
      //         let value = record.expenseAdjustApprovalView.adjustDate;
      //         return <Popover content={value && moment(value).format('YYYY-MM-DD')}>{value ? moment(value).format('YYYY-MM-DD') : '-'}</Popover>
      //       }
      //     },
      //     { title: this.$t('common.currency'), dataIndex: 'currencyCode' ,align: 'center',width: '5%',
      //       render:   (desc,record) => <Popover content={record.expenseAdjustApprovalView.currencyCode}>{record.expenseAdjustApprovalView.currencyCode || '-'}</Popover>

      //     },
      //     // {title: '币种', dataIndex: 'currency'},
      //     { title: this.$t('common.amount'), dataIndex: 'totalAmount', align: 'center', width: 120,
      //       render: (desc,record)=>{
      //       return this.filterMoney(record.expenseAdjustApprovalView.totalAmount)}
      //     },
      //     {
      //       title: this.$t('customField.base.amount')/*本币金额*/, dataIndex: 'functionalAmount', width: '15%',align:'center',
      //       render: (value, record) => {
      //         //return `${record.currencyCode}${record.originCurrencyTotalAmount}`;
      //         return this.filterMoney(record.expenseAdjustApprovalView.functionalAmount)
      //       }
      //     },
      //      { title: this.$t('common.comment'), dataIndex: 'description', align:'center',
      //        render: (desc,record)=><span><Popover content={record.expenseAdjustApprovalView.description}>{record.expenseAdjustApprovalView.description||'-'}</Popover></span>
      //      },
      //     {
      //       title: this.$t('common.column.status'), dataIndex: 'status', width: 100, render: (value, record) => {
      //         return (
      //             <Badge status={this.$statusList[record.expenseAdjustApprovalView.status].state} text={this.$statusList[record.expenseAdjustApprovalView.status].label} />
      //         )
      //       }
      //     }
      // ],
      unapprovedData: [],
      approvedData: [],
      unapprovedPagination: {
        total: 0,
      },
      approvedPagination: {
        total: 0,
      },
      unapprovedPage: 0,
      unapprovedPageSize: 10,
      approvedPage: 0,
      approvedPageSize: 10,
    };
  }

  componentDidMount() {
    //this.props.location.query.approved
    this.setState({ tabValue: false ? 'approved' : 'unapproved' });
  }

  handleSearch = values => {
    values.employeeId = values.userId && values.userId[0];
    values.beginDate && (values.beginDate = moment(values.beginDate).format('YYYY-MM-DD'));
    values.endDate && (values.endDate = moment(values.endDate).format('YYYY-MM-DD'));
    this.setState({ searchParams: values }, () => {
      this.customTable.search({ ...values, finished: this.state.tabValue === 'approved' });
    });
  };

  handleTabsChange = key => {
    this.setState(
      {
        tabValue: key,
      },
      () => {
        this.customTable.search({ finished: key === 'approved' });
      }
    );
  };

  //进入详情页
  handleRowClick = (record, flag) => {
    let place = {
      pathname: '/approval-management/approve-expense-adjust/expense-adjust-approve-detail/:expenseAdjustTypeId/:id/:entityOid/:flag/:entityType'
        .replace(':id', record.id)
        .replace(':expenseAdjustTypeId', record.expAdjustTypeId)
        .replace(':entityOid', record.documentOid)
        .replace(':flag', this.state.tabValue)
        .replace(':entityType', record.entityType),
      state: {
        entityOid: record.documentOid,
        entityType: record.entityType,
      },
    };
    this.props.dispatch(
      routerRedux.replace({
        pathname: place.pathname,
      })
    );
  };

  renderContent() {
    return <div />;
  }

  render() {
    const {
      tabValue,
      loading1,
      loading2,
      SearchForm1,
      SearchForm2,
      columns,
      unapprovedData,
      approvedData,
      unapprovedPagination,
      approvedPagination,
    } = this.state;
    return (
      <div className="approve-contract">
        <Tabs defaultActiveKey={tabValue} onChange={this.handleTabsChange}>
          <TabPane tab={this.$t({ id: 'contract.unapproved' } /*未审批*/)} key="unapproved">
            {tabValue === 'unapproved' && (
              <div>
                <SearchArea
                  searchForm={SearchForm1}
                  maxLength={4}
                  clearHandle={() => {}}
                  submitHandle={this.handleSearch}
                />
                <Row gutter={24} style={{ marginBottom: 12, marginTop: 20 }}>
                  <Col span={18} />
                  <Col span={6}>
                    <Search
                      placeholder={this.$t('exp.input.number.tips')}
                      onSearch={e => {
                        this.customTable &&
                          this.customTable.search({
                            ...this.state.searchParams,
                            documentNumber: e,
                            finished: this.state.tabValue === 'approved',
                          });
                      }}
                      className="search-number"
                      enterButton
                    />
                  </Col>
                </Row>
                <CustomTable
                  ref={ref => (this.customTable = ref)}
                  columns={columns}
                  onClick={this.handleRowClick}
                  scroll={{ x: true, y: false }}
                  params={{ finished: tabValue === 'approved' }}
                  url={`${config.expenseUrl}/api/expense/adjust/headers/approvals/filters`}
                />
              </div>
            )}
          </TabPane>
          <TabPane tab={this.$t({ id: 'contract.approved' } /*已审批*/)} key="approved">
            {tabValue === 'approved' && (
              <div>
                <SearchArea
                  searchForm={SearchForm1}
                  maxLength={4}
                  clearHandle={() => {}}
                  submitHandle={this.handleSearch}
                />
                <Row gutter={24} style={{ marginBottom: 12, marginTop: 20 }}>
                  <Col span={18} />
                  <Col span={6}>
                    <Search
                      placeholder={this.$t('exp.input.number.tips')}
                      onSearch={e => {
                        this.customTable &&
                          this.customTable.search({
                            ...this.state.searchParams,
                            documentNumber: e,
                            finished: this.state.tabValue === 'approved',
                          });
                      }}
                      className="search-number"
                      enterButton
                    />
                  </Col>
                </Row>
                <CustomTable
                  ref={ref => (this.customTable = ref)}
                  columns={columns}
                  onClick={this.handleRowClick}
                  scroll={{ x: true, y: false }}
                  params={{ finished: tabValue === 'approved' }}
                  url={`${config.expenseUrl}/api/expense/adjust/headers/approvals/filters`}
                />
              </div>
            )}
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    user: state.user.currentUser,
    company: state.user.company,
  };
}

const wrappedExpenseAdjustApprove = Form.create()(ExpenseAdjustApprove);

export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(wrappedExpenseAdjustApprove);