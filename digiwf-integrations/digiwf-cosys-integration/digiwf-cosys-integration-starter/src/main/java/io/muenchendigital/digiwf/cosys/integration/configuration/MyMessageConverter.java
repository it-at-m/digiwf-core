package io.muenchendigital.digiwf.cosys.integration.configuration;

import com.google.gson.Gson;
import io.muenchendigital.digiwf.cosys.integration.domain.model.GenerateDocument;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;
import org.springframework.util.SerializationUtils;

public class MyMessageConverter extends AbstractMessageConverter {

    public MyMessageConverter() {
        super(new MimeType("application", "json"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return (GenerateDocument.class.equals(clazz));
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        if (payload instanceof GenerateDocument)
            return payload;
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        GenerateDocument target2 = gson.fromJson(new String((byte[]) payload), GenerateDocument.class);
        return target2;
    }
}
