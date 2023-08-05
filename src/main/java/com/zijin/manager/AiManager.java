package com.zijin.manager;

import com.zijin.common.ErrorCode;
import com.zijin.exception.BusinessException;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 用于对接 AI 平台
 */
@Service
public class AiManager {
    @Resource
    private YuCongMingClient yuCongMingClient;
    /**
     * AI 对话
     *
     * @param biModelId
     * @param message
     * @return
     */

    public String doChat(long biModelId, String message) {
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(biModelId);
        devChatRequest.setMessage(message);
        BaseResponse<DevChatResponse> response = yuCongMingClient.doChat(devChatRequest);
        if (response == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "AI 响应错误");
        }
        return response.getData().getContent();
    }
}
