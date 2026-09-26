package vn.rikkei.exam.restaurantreservation.config;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.rikkei.exam.restaurantreservation.tool.AgentTools;

@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(AgentTools agentTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(agentTools)
                .build();
    }
}
