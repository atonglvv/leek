package com.atong.leek.demo.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @program: leek
 * @description:
 * @author: atong
 * @create: 2022-07-18 13:59
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //删除springboot默认的StringHttpMessageConverter解析器
        converters.removeIf(x -> x instanceof StringHttpMessageConverter);
        for (HttpMessageConverter<?> converter : converters) {
            // 解决 Controller 返回普通文本中文乱码问题
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            // 解决 Controller 返回json对象中文乱码问题
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper objectMapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
                objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                    @Override
                    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                        jsonGenerator.writeString("");
                    }
                });
                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
            }
        }
    }

    /**
     * 注意, 该方法跟 @JsonFormat(pattern = "yyyy-MM-dd HH:mm") 冲突
     */
/*    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                //json格式输出
                SerializerFeature.PrettyFormat,
                // 保留map为空的字段
                SerializerFeature.WriteMapNullValue,
                // 将String类型的null转成""形式
                SerializerFeature.WriteNullStringAsEmpty,
                // 将Number类型的null转成0
                SerializerFeature.WriteNullNumberAsZero,
                // 将List类型的null转成[],而不是""
                SerializerFeature.WriteNullListAsEmpty,
                // Boolean类型的null转成false
                SerializerFeature.WriteNullBooleanAsFalse,
                // 处理可能循环引用的问题
                SerializerFeature.DisableCircularReferenceDetect);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }*/
}