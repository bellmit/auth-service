/**
 * 操作：返回
 * 适用：所有申请单
 */
import React from 'react';
import { connect } from 'dva';
import { Form, Button } from 'antd';
import { routerRedux } from 'dva/router';

class GoBackBtn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  //返回
  goBack = () => {
    if (this.props.backType === 'history') {
      window.history.go(-1);
    } else {
      let url = '/request';
      if (this.props.backType === 'approved' || this.props.backType === 'approving')
        url = '/approval-management/approve-request/approve-request';
      if (this.props.backType === 'checkCost') url = '/financial-management/check-cost-application';
      this.props.dispatch(
        routerRedux.push({
          pathname: url,
        })
      );
    }
  };

  render() {
    return (
      <div className="go-back-btn request-btn">
        <Button onClick={this.goBack}>{this.$t('common.back')}</Button>
      </div>
    );
  }
}

function mapStateToProps() {
  return {};
}

const wrappedGoBackBtn = Form.create()(GoBackBtn);

export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(wrappedGoBackBtn);