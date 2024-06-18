package guru.qa.niffler.data.repository.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentProcessor;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import lombok.SneakyThrows;

public class AllureJsonAppender {

    private final String templateName = "json.ftl";
    private final AttachmentProcessor<AttachmentData> processor = new DefaultAttachmentProcessor();

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @SneakyThrows
    public void logJson(String name, Object json) {
        if (json != null) {
            String jsonString = objectWriter.writeValueAsString(json);
            processor.addAttachment(new AllureJsonAttachment(name, jsonString), new FreemarkerAttachmentRenderer(templateName));
        }
    }
}
