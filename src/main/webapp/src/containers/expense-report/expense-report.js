import React from 'react';
import { connect } from 'dva';
import { Form, Button, Badge, Input, Row, Col, Popover, Dropdown, Icon, Menu } from 'antd';
import Table from 'widget/table';
import SearchArea from 'widget/search-area';
const Search = Input.Search;
import constants from 'share/constants';
import expenseReportService from 'containers/expense-report/expense-report.service';
import debounce from 'lodash.debounce';
import moment from 'moment';
import 'styles/request/request.scss';
import Proxies from 'widget/Template/proxies/proxies';
import { dealCache, deepFullCopy } from 'utils/extend';
import { routerRedux } from 'dva/router';

let cacheSearchData = {};

class ExpenseReport extends React.Component {
  constructor(props) {
    super(props);
    constants.documentStatus.map(status => (status.label = status.text));
    this.state = {
      loading: false,
      expenseReportTypes: [], //报销单类型
      checkboxListForm: [
        {
          id: 'status',
          single: true,
          items: [
            {
              label: this.$t('common.column.status'),
              key: 'statusWithReject',
              checked: ['10011002100310041005100610071008'],
              options: [
                {
                  label: this.$t('constants.documentStatus.all'),
                  value: '10011002100310041005100610071008',
                  operate: '',
                }, //全部
                ...constants.expenseStatus,
              ],
            },
          ],
        },
      ],
      searchForm: [
        {
          type: 'items',
          id: 'dateRange',
          items: [
            { type: 'date', id: 'startDate', label: this.$t('common.start.date'), event: 'DATE' },
            { type: 'date', id: 'endDate', label: this.$t('common.end.date'), event: 'DATE' },
          ],
        },
        {
          type: 'radio',
          label: this.$t('common.date.range'),
          id: 'dateOption',
          event: 'DATE_RANGE',
          defaultValue: 0,
          options: [
            { label: this.$t('common.all'), value: 0 },
            { label: this.$t('common.this.month'), value: 1 },
            { label: this.$t('common.last.three.month'), value: 3 },
          ],
        },
      ],
      searchParams: { status: '10011002100310041005100610071008' },
      columns: [
        {
          title: this.$t('common.sequence'),
          dataIndex: 'index',
          render: (value, record, index) => index + 1 + this.state.pageSize * this.state.page,
          width: '6%',
        },
        {
          title: this.$t('common.submit.date'),
          dataIndex: 'submittedDate',
          render: (value, record) =>
            moment(record.lastSubmittedDate || record.createdDate).format('YYYY-MM-DD'),
        },
        { title: this.$t('common.applicant'), dataIndex: 'applicantName' },
        {
          title: this.$t('common.document.name'),
          dataIndex: 'formName',
          render: value =>
            value ? (
              <Popover placement="topLeft" content={value}>
                {value}
              </Popover>
            ) : (
              '-'
            ),
        },
        {
          title: this.$t('common.matter'),
          dataIndex: 'title',
          render: value =>
            value ? (
              <Popover placement="topLeft" content={value}>
                {value}
              </Popover>
            ) : (
              '-'
            ),
        },
        { title: this.$t('common.document.code'), dataIndex: 'businessCode' },
        { title: this.$t('common.currency'), dataIndex: 'currencyCode', width: '6%' },
        { title: this.$t('common.amount'), dataIndex: 'totalAmount', render: this.filterMoney },
        {
          title: this.$t('common.column.status'),
          dataIndex: 'status',
          width: '8%',
          render: (value, record) => {
            return this.state.checkboxListForm.map(form => {
              if (form.id === 'status') {
                return constants.documentStatus.map(item => {
                  if (
                    (item.value === String(value) && record.rejectType === 1000) ||
                    item.value === String(value * 10000 + record.rejectType)
                  ) {
                    return <Badge text={this.$t(item.label)} status={item.state} />;
                  }
                });
              }
            });
          },
        },
      ],
      data: [],
      page: 0,
      pageSize: 10,
      pagination: {
        total: 0,
      },
      //   expenseReportDetail: menuRoute.getRouteItem('expense-report-detail'),
      //   newExpenseReport: menuRoute.getRouteItem('new-expense-report')
    };
    this.handleSearch = debounce(this.handleSearch, 500);
  }
  componentDidMount() {
    this.getForms();
    this.getCache();
    this.formRef.setValues({
      startDate: null,
      endDate: null,
    });
  }
  //存储筛选数据缓存
  setCache(result) {
    let { page } = this.state;
    result.page = page;
    cacheSearchData = result;
  }
  //获取筛选数据缓存
  getCache() {
    let result = this.props.expenseReport;
    if (result && JSON.stringify(result) !== '{}') {
      let { checkboxListForm } = this.state;
      checkboxListForm.map((item, index) => {
        checkboxListForm[index].items[0].checked = [result[item.id]] || [];
      });
      cacheSearchData = result;
      this.setState({ checkboxListForm }, () => {
        this.dealCache(result);
      });
    } else {
      this.getList();
    }
  }
  //处理筛选缓存数据
  dealCache(result) {
    let { searchForm, page } = this.state;
    if (result) {
      result.startDate = null;
      result.endDate = null;
      page = result.page;
      dealCache(searchForm, result);
      this.setState({ searchForm, page }, () => {
        this.search(result);
        this.props.dispatch({
          type: 'cache/setExpenseReport',
          expenseReport: null,
        });
      });
    }
  }
  searchEventHandle = (event, value) => {
    if (event === 'DATE_RANGE') {
      let startDate, endDate;
      endDate = new Date();
      switch (value) {
        case 0:
          startDate = endDate = null;
          break;
        case 1:
          startDate = new Date().setDate(1);
          break;
        case 3:
          startDate = new Date().calcMonth(-3);
          break;
      }
      this.formRef.setValues({
        startDate: startDate ? moment(startDate) : null,
        endDate: endDate ? moment(endDate) : null,
      });
    } else if (event === 'DATE') {
      this.formRef.setValues({
        dateOption: null,
      });
    }
  };

  //获取单据列表
  getForms = () => {
    const { checkboxListForm } = this.state;
    //formType：101（申请单）、102（报销单）、103（全部）
    expenseReportService.getDocumentType(102).then(res => {
      // this.setState({expenseReportTypes: res.data});
      let formCheckBoxItem = {};
      let options = [{ label: this.$t('common.all'), value: 'all' }];
      res.data.map(item => {
        options.push({ label: item.formName, value: item.formOid });
      });
      formCheckBoxItem.id = 'formOid';
      formCheckBoxItem.single = true;
      formCheckBoxItem.items = [
        { label: this.$t('common.document.name'), key: 'formOid', options, checked: ['all'] },
      ];
      checkboxListForm.push(formCheckBoxItem);
      this.setState({ checkboxListForm, expenseReportTypes: res.data });
    });
  };

  renderExpandedRow = (title, content, index) => {
    return (
      <div key={index}>
        <span>{title}</span>:
        <span>{content}</span>
      </div>
    );
  };

  renderAllExpandedRow = record => {
    let result = [];
    if (record.warningList) {
      let warningList = JSON.parse(record.warningList);
      let content = '';
      warningList.map(item => {
        if (item.showFlag) {
          content += item.title + '/';
        }
      });
      content &&
        result.push(
          this.renderExpandedRow(
            this.$t('common.label'),
            content.substr(0, content.length - 1),
            result.length
          )
        );
    }
    if (record.printFree) {
      result.push(
        this.renderExpandedRow(
          this.$t('common.print.free'),
          this.$t('common.print.require'),
          result.length
        )
      );
    }
    if (result.length > 0) {
      return result;
    } else {
      return null;
    }
  };

  getList = () => {
    const { page, pageSize, searchParams } = this.state;
    this.setState({ loading: true });
    expenseReportService.getExpenseReportList(page, pageSize, searchParams).then(res => {
      this.setState({
        loading: false,
        data: res.data,
        pagination: {
          total: Number(res.headers['x-total-count']) || 0,
          current: page + 1,
          onChange: this.onChangePaper,
        },
      });
    });
  };

  onChangePaper = page => {
    if (page - 1 !== this.state.page) {
      this.setState({ page: page - 1 }, () => {
        this.setCache(cacheSearchData);
        this.getList();
      });
    }
  };

  search = values => {
    this.setCache({ ...values });
    values.formOid === 'all' && (values.formOid = '');
    values.startDate && (values.startDate = moment(values.startDate).format('YYYY-MM-DD+00:00:00'));
    values.endDate && (values.endDate = moment(values.endDate).format('YYYY-MM-DD+23:59:59'));
    this.setState({ searchParams: values, page: 0 }, () => {
      this.getList();
    });
  };

  handleSearch = value => {
    this.setCache({ ...value });
    let { searchParams } = this.state;
    searchParams.businessCode = value;
    this.setState(
      {
        page: 0,
        businessCode: value,
        pagination: {
          current: 1,
        },
        searchParams,
      },
      () => {
        this.getList();
      }
    );
  };
  clear = () => {
    this.setState(
      {
        searchParams: {},
        page: 0,
        pagination: { total: 0 },
      },
      () => {
        this.setCache({});
        this.formRef.setValues({
          startDate: null,
          endDate: null,
        });
        this.getList();
      }
    );
  };
  handleRowClick = record => {
    /*this.props.dispatch({
      type: 'cache/setExpenseReport',
      expenseReport: null
    });*/
    // this.context.router.push(this.state.expenseReportDetail.url.replace(':expenseReportOid', record.expenseReportOid).replace(':pageFrom', 'my'));
    this.props.dispatch(
      routerRedux.push({
        pathname: `/expense-report/expense-report-detail/${record.expenseReportOid}/my`,
      })
    );
  };

  //新建报销单
  handleNewExpenseReport = e => {
    this.props.dispatch(
      routerRedux.push({
        pathname: `/expense-report/new-expense-report/${e.key}/:userOid`,
      })
    );
  };

  render() {
    const {
      loading,
      expenseReportTypes,
      checkboxListForm,
      searchForm,
      columns,
      data,
      pagination,
    } = this.state;
    const menu = (
      <Menu onClick={this.handleNewExpenseReport} style={{ maxHeight: 250, overflow: 'auto' }}>
        {expenseReportTypes.map(item => {
          return <Menu.Item key={item.formOid}>{item.formName}</Menu.Item>;
        })}
      </Menu>
    );
    return (
      <div className="application-list">
        <SearchArea
          searchForm={searchForm}
          submitHandle={this.search}
          checkboxListForm={checkboxListForm}
          eventHandle={this.searchEventHandle}
          clearHandle={this.clear}
          wrappedComponentRef={inst => (this.formRef = inst)}
        />
        <div className="table-header">
          <div className="table-header-title">
            {this.$t('common.total', {
              total: pagination.total,
            }) /*共搜索到 {pagination.total} 条数据*/}
          </div>
          <div className="table-header-buttons">
            <div id="expense-report-drop" style={{ position: 'relative' }}>
              <Dropdown
                getPopupContainer={() => document.getElementById('expense-report-drop')}
                overlay={menu}
              >
                <Button type="primary">
                  {this.$t('expense-report.new') /*新建报销单*/} <Icon type="down" />
                </Button>
              </Dropdown>
            </div>
            {/*代理制单*/}
            <Proxies />
            <Search
              className="input-search"
              placeholder={this.$t('expense-report.please.enter.code')}
              onChange={e => this.handleSearch(e.target.value)}
            />
          </div>
        </div>
        <Table
          rowKey="expenseReportOid"
          columns={columns}
          expandedRowRender={this.renderAllExpandedRow}
          dataSource={data}
          pagination={pagination}
          onRow={record => ({ onClick: () => this.handleRowClick(record) })}
          loading={loading}
          rowClassName={record =>
            record.printFree || record.warningList ? '' : 'row-expand-display-none'
          }
          bordered
          size="middle"
        />
      </div>
    );
  }
}

// ExpenseReport.contextTypes = {
//   router: React.PropTypes.object
// };

function mapStateToProps(state) {
  return {
    expenseReport: state.cache.expenseReport,
  };
}

export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(ExpenseReport);