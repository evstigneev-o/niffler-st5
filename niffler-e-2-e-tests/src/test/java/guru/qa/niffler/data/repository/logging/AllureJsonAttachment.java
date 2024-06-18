package guru.qa.niffler.data.repository.logging;

import io.qameta.allure.attachment.AttachmentData;

public class AllureJsonAttachment implements AttachmentData {
    private final String name;
    private final String json;

    public AllureJsonAttachment(String name, String json) {
        this.name = name;
        this.json = json;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getJson() {
        return json;
    }
}
