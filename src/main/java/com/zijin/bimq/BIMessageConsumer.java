package com.zijin.bimq;

import com.rabbitmq.client.Channel;
import com.zijin.common.ErrorCode;
import com.zijin.constant.BIConstant;
import com.zijin.exception.BusinessException;
import com.zijin.manager.AiManager;
import com.zijin.model.entity.Chart;
import com.zijin.service.ChartService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.zijin.constant.CommonConstant.BI_MODE_ID;

@Slf4j
@Component

public class BIMessageConsumer {
    @Resource
    private ChartService chartService;
    @Resource
    private AiManager aiManager;
    @RabbitListener(queues = {BIConstant.BI_QUEUE_NAME},ackMode = "MANUAL")
    @SneakyThrows
    public void receiveMessage(String message,Channel channel,@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
//        每一条消息打印一条日志
        log.info("receiveMessage message={}",message);
//        消息拒绝
        if(StringUtils.isBlank(message)){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"消息为空");
        }
        long chartId=Long.parseLong(message);
        Chart chart=chartService.getById(chartId);
        if(chart==null){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"图表为空");
        }
        Chart chartupdate=new Chart();
        chartupdate.setId(chart.getId());
        chartupdate.setStatus("running");
        boolean b = chartService.updateById(chartupdate);
        if (!b){
            handleChartupdateError(chart.getId(), "更新图表执行中状态失败");
            return;
        }

        //调用AI
        String result = aiManager.doChat(BI_MODE_ID, buildUserInput(chart));
        String[] splits = result.split("【【【【【");
        if (splits.length < 3) {
            handleChartupdateError(chart.getId(), "AI 生成错误");
            return;
        }
        String genChart = splits[1].trim();
        String genResult = splits[2].trim();
        Chart updateChartResult=new Chart();
        updateChartResult.setId(chart.getId());
        updateChartResult.setGenChart(genChart);
        updateChartResult.setGenResult(genResult);
        updateChartResult.setStatus("succeed");
        boolean updateresult = chartService.updateById(updateChartResult);
        if (!updateresult){
            handleChartupdateError(chart.getId(), "更新图表成功状态失败");

        }
//        消息确认
        channel.basicAck(deliveryTag,false);
    }
          /**
           * 构建用户输入 */
    private String buildUserInput(Chart chart){
        String goal=chart.getGoal();
        String chartType=chart.getChartType();
        String csvData=chart.getChartData();
        // 构造用户输入
        StringBuilder userInput = new StringBuilder();
        userInput.append("分析需求：").append("\n");
        // 拼接分析目标
        String userGoal = goal;
        if (StringUtils.isNotBlank(chartType)) {
            userGoal += "，请使用" + chartType;
        }
        userInput.append(userGoal).append("\n");
        userInput.append("原始数据：").append("\n");
        userInput.append(csvData).append("\n");
        return userInput.toString();
    }
    private void handleChartupdateError(long chartId,String execMessage){
        //把图表状态修改为“执行中”，执行成功后“修改为”“已完成”，保存执行结果，“失败”也一样。
        Chart updateChartResult=new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus("failed");
        updateChartResult.setExecMessage("execMessage");
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult){
            log.error("更新图表失败状态失败"+chartId+","+execMessage);
        }
    }

 }




