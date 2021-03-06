package com.hand.hcf.app.workflow.dto.form;

import com.hand.hcf.app.workflow.constant.ApprovalFormPropertyConstants;
import com.hand.hcf.app.workflow.domain.ApprovalFormProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ApprovalFormPropertyInitDTO {
    //默认管控方式配置值
    public static final String MANAGE_TYPE_PROPERTY_VALUE = "1002";

    /**
     * 默认机票管控字段配置值
     */
    public static final String FLIGHT_FIELDS_PROPERTY_VALUE = "{\"passengerList\":{\"control\":true},\"internationalPassengerList\":{\"control\":true},\"travelerCount\":{\"control\":false},\"skipFields\":{\"control\":false},\"departBeginDate\":{\"enable\":false,\"floatDays\":4,\"show\":true,\"required\":true,\"control\":true},\"departEndDate\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":true},\"returnBeginDate\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":true},\"returnEndDate\":{\"enable\":false,\"floatDays\":4,\"show\":true,\"required\":true,\"control\":true},\"fromCities\":{\"enable\":false,\"show\":true,\"required\":true,\"control\":true},\"toCities\":{\"enable\":false,\"show\":true,\"required\":true,\"control\":true},\"discount\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"ticketPrice\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"seatClass\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"takeOffBeginTime\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"takeOffEndTime\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"arrivalBeginTime\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"arrivalEndTime\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false}}";

    /**
     *    //默认酒店管控字段
     */
    public static final String HOTEL_FIELDS_PROPERTY_VALUE = "{\"city\":{\"enable\":false,\"show\":true,\"required\":false,\"control\":false},\"maxPrice\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":true},\"minPrice\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":true},\"fromDate\":{\"enable\":true,\"floatDays\":1,\"show\":true,\"required\":true,\"control\":true},\"leaveDate\":{\"enable\":true,\"floatDays\":1,\"show\":true,\"required\":true,\"control\":true},\"roomNumber\":{\"enable\":true,\"show\":true,\"required\":true,\"control\":true},\"passenger\":{\"enable\":true,\"show\":true,\"required\":true,\"control\":true}}";

    /**
     *    //默认最大机票数量是否允许修改管控字段配置值
     */
    public static final String MAX_FLIGHT_TICKET_AMOUNT_MODIFIED_ENABLE_PROPERTY_VALUE = "false";

    /**
     *    //默认火车管控字段配置值
     */
    public static final String TRAIN_FIELDS_PROPERTY_VALUE = "{\"passengerList\":{\"enable\":true},\"travelerCount\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"fromCity\":{\"enable\":true,\"show\":true,\"required\":true,\"control\":true},\"toCity\":{\"enable\":true,\"show\":true,\"required\":true,\"control\":true},\"departBeginDate\":{\"enable\":true,\"floatDays\":4,\"show\":true,\"required\":true,\"control\":true},\"departEndDate\":{\"enable\":false,\"show\":true,\"required\":false,\"control\":false},\"returnBeginDate\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"returnEndDate\":{\"enable\":true,\"floatDays\":4,\"show\":true,\"required\":true,\"control\":true},\"ticketPrice\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false},\"seatClass\":{\"enable\":false,\"show\":false,\"required\":false,\"control\":false}}";

    /**
     *    //初始化默认会签类型为顺序加签
     */
    public static final String COUNTERSIGN_TYPE_PROPERTY_VALUE = "2";

    /**
     *    //携程成本中心自定义字段
     */
    public static final String CTRIP_COST_CENTER_CONFIG = "{\"costCenter1\":{\"enabled\":false,\"type\":\"\",\"value\":\"\",\"valueScope\":\"\"},\"costCenter2\":{\"enabled\":false,\"type\":\"\",\"value\":\"\",\"valueScope\":\"\"},\"costCenter3\":{\"enabled\":false,\"type\":\"\",\"value\":\"\",\"valueScope\":\"\"},\"costCenter4\":{\"enabled\":false,\"type\":\"\",\"value\":\"\",\"valueScope\":\"\"},\"costCenter5\":{\"enabled\":false,\"type\":\"\",\"value\":\"\",\"valueScope\":\"\"}}";



    /**
     *    //FunctionProfile配置差旅行程
     */
    public static final String TravelItineraryConfig = "{\"flight\":true,\"hotel\":true,\"train\":true,\"other\":true,\"subsidy\":true,\"remake\":true}";

    public List<ApprovalFormProperty> approvalFormPropertyList = new ArrayList<>();
    public List<UUID> formOidList = new ArrayList<>();

    public ApprovalFormPropertyInitDTO() {
        ApprovalFormProperty approvalFormPropertyManageType = new ApprovalFormProperty(ApprovalFormPropertyConstants.APPLICATION_PROPERTY_MANAGE_TYPE, MANAGE_TYPE_PROPERTY_VALUE);
        ApprovalFormProperty approvalFormPropertyFlightFields = new ApprovalFormProperty(ApprovalFormPropertyConstants.APPLICATION_PROPERTY_CONTROL_FIELDS, FLIGHT_FIELDS_PROPERTY_VALUE);
        ApprovalFormProperty approvalFormPropertyHotelFields = new ApprovalFormProperty(ApprovalFormPropertyConstants.APPLICATION_PROPERTY_CONTROL_FIELDS_HOTEL, HOTEL_FIELDS_PROPERTY_VALUE);
        ApprovalFormProperty approvalFormPropertyTrainFields = new ApprovalFormProperty(ApprovalFormPropertyConstants.APPLICATION_PROPERTY_CONTROL_FIELDS_TRAIN, TRAIN_FIELDS_PROPERTY_VALUE);
        ApprovalFormProperty flightItineraryDisabled = new ApprovalFormProperty(ApprovalFormPropertyConstants.TRAVEL_FLIGHT_ITINERARY_DISABLED, Boolean.FALSE.toString());
        ApprovalFormProperty hotelItineraryEnable = new ApprovalFormProperty(ApprovalFormPropertyConstants.TRAVEL_HOTEL_ITINERARY_ENABLE, Boolean.TRUE.toString());
        ApprovalFormProperty trainItineraryDisabled = new ApprovalFormProperty(ApprovalFormPropertyConstants.TRAVEL_TRAIN_ITINERARY_DISABLED, Boolean.FALSE.toString());
        ApprovalFormProperty otherItineraryDisabled = new ApprovalFormProperty(ApprovalFormPropertyConstants.TRAVEL_OTHER_ITINERARY_DISABLED, Boolean.FALSE.toString());
        ApprovalFormProperty travelAllowanceDisabled = new ApprovalFormProperty(ApprovalFormPropertyConstants.TRAVEL_ALLOWANCE_DISABLED, Boolean.FALSE.toString());
        ApprovalFormProperty expiredDateConfig = new ApprovalFormProperty(ApprovalFormPropertyConstants.EXPIRED_TIME_CONFIG, Boolean.FALSE.toString());

        ApprovalFormProperty approvalFormPropertyMaxFlightTicketAmountModifiedEnable = new ApprovalFormProperty(ApprovalFormPropertyConstants.MAX_FLIGHT_TICKET_AMOUNT_MODIFIED_ENABLE, MAX_FLIGHT_TICKET_AMOUNT_MODIFIED_ENABLE_PROPERTY_VALUE);
        this.approvalFormPropertyList.add(approvalFormPropertyManageType);
        this.approvalFormPropertyList.add(approvalFormPropertyFlightFields);
        this.approvalFormPropertyList.add(approvalFormPropertyHotelFields);
        this.approvalFormPropertyList.add(approvalFormPropertyTrainFields);
        this.approvalFormPropertyList.add(flightItineraryDisabled);
        this.approvalFormPropertyList.add(hotelItineraryEnable);
        this.approvalFormPropertyList.add(trainItineraryDisabled);
        this.approvalFormPropertyList.add(otherItineraryDisabled);
        this.approvalFormPropertyList.add(travelAllowanceDisabled);
        this.approvalFormPropertyList.add(approvalFormPropertyMaxFlightTicketAmountModifiedEnable);
        this.approvalFormPropertyList.add(expiredDateConfig);
    }

    public ApprovalFormPropertyInitDTO(String propertyName, String propertyValue) {
        ApprovalFormProperty approvalFormProperty = new ApprovalFormProperty(propertyName, propertyValue);
        approvalFormPropertyList.add(approvalFormProperty);
    }

    public ApprovalFormPropertyInitDTO(Map<String, String> paramMap) {
        paramMap.forEach((a, b) -> {
            ApprovalFormProperty approvalFormProperty = new ApprovalFormProperty(a, b);
            approvalFormPropertyList.add(approvalFormProperty);
        });
    }
}
