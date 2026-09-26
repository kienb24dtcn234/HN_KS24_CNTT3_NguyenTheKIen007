package vn.rikkei.exam.restaurantreservation.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.rikkei.exam.restaurantreservation.tool.AgentTools;

@Configuration
public class ChatConfig {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(10)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 ChatMemory chatMemory,
                                 VectorStore vectorStore,
                                 AgentTools agentTools) {
        return builder
                .defaultSystem("Ban la tro ly dat ban nha hang. Tra loi cau hoi ve quy dinh dua tren "
                        + "tai lieu noi bo duoc cung cap. Voi thao tac dat ban / tra cuu ban trong / "
                        + "duyet, hay goi tool tuong ung. Neu khong du can cu, tra loi khong biet.")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build())
                .defaultTools(agentTools)
                .build();
    }
}
