import { getQueryUrlParam } from 'utils/extend';
import React from 'react';
import { connect } from 'dva';
import { Form, Button, message, Modal } from 'antd';

import approveRequestService from 'containers/approve/request/request.service';
import ApproveBar from 'widget/Template/approve-bar';
import PropTypes from 'prop-types';

import 'styles/approve/request/approve-request-btn.scss';
import { routerRedux } from 'dva/router';

class ApproveRequestBtn extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      passLoading: false,
      rejectLoading: false,
      info: {}, //申请单详情
      approvalChain: {}, //审批链
      showAdditionalBtn: false, //是否显示加签按钮
      showPriceViewBtn: false, //是否显示价格审核复选框
      preApproveOids: [], //当前审批链中已审批用户的Oid
      signCompanyOids: [],
    };
  }

  componentWillReceiveProps(nextProps) {
    if (!this.state.info.applicationOid && nextProps.approving) {
      const formInfo = nextProps.formInfo;
      this.setState(
        {
          info: nextProps.info,
          showPriceViewBtn:
            formInfo.customFormPropertyMap &&
            formInfo.customFormPropertyMap['travel.booker.price.audit.enable'] === 'true',
        },
        () => {
          this.showAdditional();
          this.getPreApproveOid();
        }
      );
    }
  }

  //判断是否可以加签
  showAdditional = () => {
    let params = {
      companyOid: this.props.company.companyOid,
      formOid: this.state.info.formOid,
      counterSignType: 'enableAddSign',
    };
    this.props.approving &&
      this.state.info.formOid &&
      approveRequestService.postAddSignEnableScope(params).then(res => {
        // this.setState({ showAdditionalBtn: res.data });
        if (res.data.enabled) {
          //加签人范围
          this.setState({
            showAdditionalBtn: res.data.enabled,
            signCompanyOids: res.data.approvalAddSignScope.companyOids,
          });
        }
      });
  };

  //获取当前审批链中已审批的用户Oid
  getPreApproveOid = () => {
    let preApproveOids = [];
    (this.state.info.approvalHistorys || []).map(item => {
      item.operation === 2001 && preApproveOids.push(item.operatorOid);
    });
    this.setState({ preApproveOids });
  };

  //提示 当前被加签的人是否在已审批的人中
  hasRepeatApproveTip = (value, additionalItems, priceAuditor) => {
    let additionalOids = [];
    let additionalHaveApprovedNames = []; //加签人中已审批的用户名
    additionalItems.map(item => {
      additionalOids.push(item.userOid);
    });
    additionalOids.map((Oid, index) => {
      if (this.state.preApproveOids.indexOf(Oid) > -1) {
        additionalHaveApprovedNames.push(additionalItems[index].fullName);
      }
    });
    if (additionalHaveApprovedNames.length) {
      Modal.confirm({
        title: `${additionalHaveApprovedNames.join('、')} ${
          this.$t('approve.request.has.approved') /*已经审批通过，是否继续*/
        }？`,
        onOk: () => this.handleApprovePass(value, additionalOids, priceAuditor),
      });
    } else {
      this.handleApprovePass(value, additionalOids, priceAuditor);
    }
  };

  //审批通过
  handleApprovePass = (value, additionalOids, priceAuditor) => {
    let params = {
      approvalTxt: value,
      entities: [
        {
          approverOid: this.props.user.userOid, //getQueryUrlParam('approverOid'),
          entityOid: this.state.info.applicationOid,
          entityType: 1001, //申请单
          countersignApproverOids: additionalOids,
          priceAuditor,
        },
      ],
    };
    this.setState({ passLoading: true });
    approveRequestService
      .handleRequestApprovePass(params)
      .then(res => {
        if (res.data.failNum === 0) {
          message.success(this.$t('common.operate.success'));
          this.setState({ passLoading: false });
          this.goBack();
        } else {
          this.setState({ passLoading: false });
          message.error(
            `${this.$t('common.operate.filed')}，${
              res.data.failReason[this.state.info.applicationOid]
            }`
          );
        }
      })
      .catch(e => {
        this.setState({ passLoading: false });
        message.error(`${this.$t('common.operate.filed')}，${e.response.data.message}`);
      });
  };

  //审批驳回
  handleApproveReject = (value, additionalItems) => {
    let additionalOids = [];
    additionalItems.map(item => {
      additionalOids.push(item.userOid);
    });
    let params = {
      approvalTxt: value,
      entities: [
        {
          approverOid: location.search.split('=')[2],
          entityOid: this.state.info.applicationOid,
          entityType: 1001, //申请单
        },
      ],
    };
    this.setState({ rejectLoading: true });
    approveRequestService
      .handleRequestApproveReject(params)
      .then(res => {
        if (res.data.failNum === 0) {
          message.success(this.$t('common.operate.success'));
          this.setState({ rejectLoading: false });
          this.goBack();
        } else {
          this.setState({ rejectLoading: false });
          message.error(
            `${this.$t('common.operate.filed')}，${
              res.data.failReason[this.state.info.applicationOid]
            }`
          );
        }
      })
      .catch(e => {
        this.setState({ rejectLoading: false });
        message.error(`${this.$t('common.operate.filed')}，${e.response.data.message}`);
      });
  };

  //返回
  goBack = () => {
    this.props.dispatch(
      routerRedux.push({
        pathname: '/approval-management/approve-request',
      })
    );
  };

  render() {
    const {
      passLoading,
      rejectLoading,
      approveRequestList,
      showAdditionalBtn,
      showPriceViewBtn,
      signCompanyOids,
    } = this.state;
    let moreButtons = [];
    showAdditionalBtn && moreButtons.push('additional');
    return (
      <div className="approve-request-btn">
        {this.props.approving ? (
          <ApproveBar
            backUrl={'/approval-management/approve-request'}
            signCompanyOids={signCompanyOids}
            moreButtons={moreButtons}
            passLoading={passLoading}
            rejectLoading={rejectLoading}
            priceView={this.props.formType === 2003 && showPriceViewBtn}
            customFormPropertyMap={
              this.props.formInfo ? this.props.formInfo.customFormPropertyMap : {}
            }
            handleApprovePass={this.hasRepeatApproveTip}
            handleApproveReject={this.handleApproveReject}
          />
        ) : (
          <Button type="primary" className="back-btn" onClick={this.goBack}>
            {this.$t('common.back')}
          </Button>
        )}
      </div>
    );
  }
}

ApproveRequestBtn.propTypes = {
  formType: PropTypes.number,
  info: PropTypes.object,
  formInfo: PropTypes.object,
  approving: PropTypes.bool,
};

function mapStateToProps(state) {
  return {
    user: state.user.currentUser,
    company: state.user.company,
  };
}

const wrappedApproveRequestBtn = Form.create()(ApproveRequestBtn);

export default connect(
  mapStateToProps,
  null,
  null,
  { withRef: true }
)(wrappedApproveRequestBtn);