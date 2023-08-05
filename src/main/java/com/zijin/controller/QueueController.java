package com.zijin.controller;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 队列测试
 *
 *
 */
@RestController
@RequestMapping("/gueue")
@Slf4j
@Profile({"dev","local"})
public class QueueController {
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @GetMapping("/add")
    public void add(String name){
        CompletableFuture.runAsync(()->{
            System.out.println("任务执行中："+name+",执行人："+Thread.currentThread().getName());
            try {
//                睡个600秒方便我们查看实现线程池的实现过程
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        },threadPoolExecutor);

    }
    @GetMapping("/get")
    public String get(){
        Map<String,Object> map=new HashMap<>();
        int size = threadPoolExecutor.getQueue().size();
        map.put("队列长度",size);
        long taskCount = threadPoolExecutor.getTaskCount();
        map.put("任务总数",taskCount);
        int activeCount = threadPoolExecutor.getActiveCount();
        map.put("正在工作的线程数",activeCount);
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        map.put("已经完成的任务数",completedTaskCount);

        return JSONUtil.toJsonStr(map);
    }
}
