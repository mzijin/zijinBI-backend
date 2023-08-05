package com.zijin.api;

import org.springframework.stereotype.Service;

/**
 * 调用 AI 平台的示例代码
 */
@Service
public class OpenAiApi {

    public static void main(String[] args) {

//        /**
//         * AI 对话（需要自己创建请求响应对象）
//         *
//         * @param request
//         * @param openAiApiKey
//         * @return
//         */
//        public CreateChatCompletionResponse createChatCompletion(CreateChatCompletionRequest request, String openAiApiKey) {
//            if (StringUtils.isBlank(openAiApiKey)) {
//                throw new BusinessException(ErrorCode.PARAMS_ERROR, "未传 openAiApiKey");
//            }
//            String url = "https://api.openai.com/v1/chat/completions";
//            String json = JSONUtil.toJsonStr(request);
//            String result = HttpRequest.post(url)
//                    .header("Authorization", "Bearer " + openAiApiKey)
//                    .body(json)
//                    .execute()
//                    .body();
//            return JSONUtil.toBean(result, CreateChatCompletionResponse.class);
//        }

    }
}
