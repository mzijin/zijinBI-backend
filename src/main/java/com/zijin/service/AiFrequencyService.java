package com.zijin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijin.model.entity.AiFrequency;


/**
* @author Shier
* @description 针对表【ai_frequency(ai调用次数表)】的数据库操作Service
* @createDate 2023-07-11 20:47:00
*/
public interface AiFrequencyService extends IService<AiFrequency> {

    /**
     * 调用智能分析接口次数自动减一
     * @param userId
     * @return
     */
    boolean invokeAutoDecrease(long userId);

    /**
     * 查看用户有无调用次数
     * @param userId
     * @return
     */
    boolean hasFrequency(long userId);

}
