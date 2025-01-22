package com.example.controller;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 林万奇
 * @since 2025-01-09
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Resource
    EmployService employService;
    @Resource
    UserService userService;
    @Resource
    PositionService positionService;
    @Resource
    ResumeService resumeService;
    @Resource
    SubmitService submitService;
    @Resource
    IndustryService industryService;

    @GetMapping("/base")
    public Result base() {
        Map<String, Integer> resultList = new HashMap<String, Integer>();
        List<Employ> employList = employService.selectAll();
        List<User> userList = userService.selectAll();
        List<Position> positionList = positionService.selectAll(null);
        List<Resume> resumeList = resumeService.selectAll(null);
        resultList.put("employCount", employList.size());
        resultList.put("userCount", userList.size());
        resultList.put("positionCount", positionList.size());
        resultList.put("resumeCount", resumeList.size());
        return Result.success(resultList);
    }
    @GetMapping("/pie")
    public Result pie() {
        List<Map<String, Object>> resultMap = new ArrayList<>();
        List<Submit> submits = submitService.selectAll(null);
        Map<String, Long> map = submits.stream().filter(submit -> ObjectUtil.isNotEmpty(submit.getPositionName()))
                .collect(Collectors.groupingBy(Submit::getPositionName, Collectors.counting()));
        for (String key: map.keySet()) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("name", key);
            tmp.put("value", map.get(key));
            resultMap.add(tmp);
        }
        return  Result.success(resultMap);
    }
    @GetMapping("/bar")
    public Result bar() {
        List<String> xAxis = new ArrayList<>();
        List<Long> yAxis = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        List<Industry> industryList = industryService.selectAll();
        List<Position> positionList = positionService.selectAll(null);
        for (Industry industry : industryList) {
            xAxis.add(industry.getName());
            yAxis.add(positionList.stream()
                    .filter(position -> ObjectUtil.isNotEmpty(position.getIndustryName()) && position.getIndustryId().equals(industry.getId()))
                    .count());
        }
        resultMap.put("xAxis", xAxis);
        resultMap.put("yAxis", yAxis);
        return  Result.success(resultMap);
    }
    @GetMapping("/line")
    public Result line() {
        List<String> xAxis = new ArrayList<>();
        List<Long> yAxis = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        List<Submit> submits = submitService.selectAll(null);
        LocalDate today = LocalDate.now();
        List<String> list = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        list.add(today.format(format));
        for (int i = 0;i < 30;i++) {
            LocalDate previousDate = today.minusDays(i);
            list.add(previousDate.format(format));
        }
        ListUtil.reverse(list);
        for (String date : list) {
            xAxis.add(date);
            yAxis.add(submits.stream().filter(submit -> submit.getTime().toString().equals(date)).count());
        }

        resultMap.put("xAxis", xAxis);
        resultMap.put("yAxis", yAxis);
        return  Result.success(resultMap);
    }

}
