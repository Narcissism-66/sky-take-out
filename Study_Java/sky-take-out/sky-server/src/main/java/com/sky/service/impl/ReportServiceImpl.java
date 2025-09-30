package com.sky.service.impl;

/*
 * @Auther:fz
 * @Date:2025/8/3
 * @Description:
 */

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList=new ArrayList<>();
        List<Double>turnoverList=new ArrayList<>();
        dateList.add(begin);
        //计算每一天
        while(!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }
        for(LocalDate data:dateList){
            LocalDateTime beginTime=LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(data, LocalTime.MAX);
            Map<Object, Object> map=new HashMap<>();
            map.put("beginTime",beginTime);
            map.put("endTime",endTime);
            map.put("status", Orders.COMPLETED);
            Double turnover=orderMapper.sumByMap(map);
            if (turnover==null)turnover=0.0;
            turnoverList.add(turnover);
        }

        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList=new ArrayList<>();
        List<Integer>totalUserList=new ArrayList<>();
        List<Integer>newUserList=new ArrayList<>();

        dateList.add(begin);
        //计算每一天
        while(!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }


        for(LocalDate data:dateList){
            LocalDateTime beginTime=LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime endTime=LocalDateTime.of(data, LocalTime.MAX);
            Map<Object, Object> map=new HashMap<>();
            map.put("endTime",endTime);
            Integer totalUser=userMapper.countByMap(map);
            map.put("beginTime",beginTime);
            Integer newUser=userMapper.countByMap(map);
            totalUserList.add(totalUser);
            newUserList.add(newUser);

        }
        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .build();
    }

    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList=new ArrayList<>();
        List<Integer>orderCountList=new ArrayList<>();
        List<Integer>validOrderCountList=new ArrayList<>();

        dateList.add(begin);
        //计算每一天
        while(!begin.equals(end)){
            begin=begin.plusDays(1);
            dateList.add(begin);
        }

        for(LocalDate data:dateList){
            LocalDateTime beginTime = LocalDateTime.of(data, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(data, LocalTime.MAX);
            Integer orderCount = getOrderCount(beginTime,endTime,null);//总订单数
            Integer validOrderCount = getOrderCount(beginTime,endTime,Orders.COMPLETED);//有效订单数
            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }

        //求和
        Integer orderCountSum = orderCountList.stream().reduce(Integer::sum).orElse(0);
        Integer validOrderCountSum = validOrderCountList.stream().reduce(Integer::sum).orElse(0);

        //完成率

        double completionRate = 0.0;
        if (orderCountSum != 0) {
            completionRate = validOrderCountSum.doubleValue() / orderCountSum.doubleValue() ;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .orderCountList(StringUtils.join(orderCountList,","))
                .totalOrderCount(orderCountSum)
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .validOrderCount(validOrderCountSum)
                .orderCompletionRate(completionRate)
                .build();
    }

    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        Map<String, Object> map=new HashMap<>();
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        List<GoodsSalesDTO> salesTop = orderMapper.getSalesTop(map);


        List<String> names = salesTop.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList());
        String nameList=StringUtils.join(names,",");
        List<Integer> numbers = salesTop.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList());
        String numberList=StringUtils.join(numbers,",");
        return SalesTop10ReportVO
                .builder()
                .nameList(nameList)
                .numberList(numberList)
                .build();
    }

    /**
     * 获取订单数量
     * @param beginTime
     * @param endTime
     * @param status
     * @return
     */
    private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status){
        Map<Object, Object> map=new HashMap<>();
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("status",status);
        return orderMapper.countByMap(map);
    }
}
