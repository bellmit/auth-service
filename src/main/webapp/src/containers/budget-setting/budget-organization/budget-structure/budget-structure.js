/**
 * created by jsq on 2017/9/18
 */
import React from 'react';
import { connect } from 'dva';
import { Button, Badge, notification, Popover } from 'antd';
import Table from 'widget/table';
import SearchArea from 'widget/search-area';
import 'styles/budget-setting/budget-organization/budget-structure/budget-structure.scss';
import budgetService from 'containers/budget-setting/budget-organization/budget-structure/budget-structure.service';
import organizationService from 'containers/budget-setting/budget-organization/budget-organnization.service';
import { routerRedux } from 'dva/router';

class BudgetStructure extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: true,
      data: [],
      organization: {},
      searchParams: {
        structureCode: '',
        structureName: '',
      },
      pagination: {
        current: 1,
        page: 0,
        total: 0,
        pageSize: 10,
        showSizeChanger: true,
        showQuickJumper: true,
      },
      searchForm: [
        {
          type: 'input',
          id: 'structureCode',
          label: this.$t({ id: 'budget.structureCode' }),
        } /*预算表代码*/,
        {
          type: 'input',
          id: 'structureName',
          label: this.$t({ id: 'budget.structureName' }),
        } /*预算表名称*/,
      ],
      columns: [
        {
          /*预算表代码*/
          title: this.$t({ id: 'budget.structureCode' }),
          align: 'center',
          key: 'structureCode',
          dataIndex: 'structureCode',
        },
        {
          /*预算表名称*/
          title: this.$t({ id: 'budget.structureName' }),
          align: 'center',
          key: 'structureName',
          dataIndex: 'structureName',
        },
        {
          /*编制期段*/
          title: this.$t({ id: 'budget.periodStrategy' }),
          align: 'center',
          key: 'periodStrategy',
          dataIndex: 'periodStrategy',
          width: '10%',
          render: recode => {
            if (recode === 'MONTH') return this.$t({ id: 'periodStrategy.month' }); /*月度*/
            if (recode === 'QUARTER') return this.$t({ id: 'periodStrategy.quarter' }); /*季度*/
            if (recode === 'YEAR') return this.$t({ id: 'periodStrategy.year' }); /*年度*/
          },
        },
        {
          /*备注*/
          title: this.$t({ id: 'budget.structureDescription' }),
          align: 'center',
          key: 'description',
          dataIndex: 'description',
          render: desc => (
            <span>
              {desc ? (
                <Popover placement="topLeft" content={desc}>
                  {desc}
                </Popover>
              ) : (
                '-'
              )}
            </span>
          ),
        },
        {
          /*状态*/
          title: this.$t({ id: 'common.column.status' }),
          key: 'status',
          width: '10%',
          align: 'center',
          dataIndex: 'enabled',
          render: enabled => (
            <Badge
              status={enabled ? 'success' : 'error'}
              text={
                enabled
                  ? this.$t({ id: 'common.status.enable' })
                  : this.$t({ id: 'common.status.disable' })
              }
            />
          ),
        },
      ],
    };
  }
  componentDidMount() {
    this.getList();
  }

  //获取预算表数据
  getList() {
    let params = Object.assign({}, this.state.searchParams);
    for (let paramsName in params) {
      !params[paramsName] && delete params[paramsName];
    }
    this.setState({ loading: true });
    params.organizationId = this.props.organization.id || this.props.id;
    params.page = this.state.pagination.page;
    params.size = this.state.pagination.pageSize;
    budgetService.getStructures(params).then(response => {
      response.data.map((item, index) => {
        item.key = item.structureCode;
      });
      this.setState({
        data: response.data,
        pagination: {
          total: Number(response.headers['x-total-count']),
          current: this.state.pagination.current,
          page: this.state.pagination.page,
          pageSize: this.state.pagination.pageSize,
          showSizeChanger: true,
          showQuickJumper: true,
        },
        loading: false,
      });
    });
  }

  handleSearch = values => {
    let searchParams = {
      structureName: values.structureName,
      structureCode: values.structureCode,
    };
    this.setState(
      {
        searchParams: searchParams,
        page: 1,
      },
      () => {
        this.getList();
      }
    );
  };

  //分页点击
  onChangePager = (pagination, filters, sorter) => {
    let temp = this.state.pagination;
    temp.page = pagination.current - 1;
    temp.current = pagination.current;
    temp.pageSize = pagination.pageSize;
    this.setState(
      {
        loading: true,
        pagination: temp,
      },
      () => {
        this.getList();
      }
    );
  };

  handleCreate = () => {
    if (this.props.organization.enabled) {
      this.props.dispatch(
        routerRedux.replace({
          pathname: '/budget-setting/budget-organization/budget-organization-detail/budget-structure/new-budget-structure/:setOfBooksId/:orgId'
            .replace(':orgId', this.props.organization.id)
            .replace(':setOfBooksId', this.props.organization.setOfBooksId),
        })
      );
    } else {
      notification['error']({
        description: this.$t({ id: 'structure.validateCreate' }) /*请维护当前账套下的预算组织*/,
      });
    }
  };

  //点击行，进入该行详情页面
  handleRowClick = record => {
    console.log(this.props.organization);
    this.props.dispatch(
      routerRedux.push({
        pathname: '/budget-setting/budget-organization/budget-organization-detail/budget-structure/budget-structure-detail/:orgId/:setOfBooksId/:id'
          .replace(':orgId', this.props.organization.id)
          .replace(':setOfBooksId', this.props.organization.setOfBooksId)
          .replace(':id', record.id),
      })
    );
  };

  render() {
    const { searchForm, loading, data, columns, pagination } = this.state;
    return (
      <div className="budget-structure">
        <SearchArea searchForm={searchForm} submitHandle={this.handleSearch} />
        <div className="table-header">
          <div className="table-header-title">
            {this.$t({ id: 'common.total' }, { total: `${pagination.total}` })}
          </div>{' '}
          {/*共搜索到*条数据*/}
          <div className="table-header-buttons">
            <Button type="primary" onClick={this.handleCreate}>
              {this.$t({ id: 'common.create' })}
            </Button>{' '}
            {/*新 建*/}
          </div>
        </div>
        <Table
          loading={loading}
          dataSource={data}
          columns={columns}
          pagination={pagination}
          onChange={this.onChangePager}
          onRow={record => ({
            onClick: () => this.handleRowClick(record),
          })}
          size="middle"
          bordered
        />
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    organization: state.budget.organization,
  };
}

export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(BudgetStructure);