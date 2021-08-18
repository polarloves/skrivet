package skrivet.springframework.plugins.web.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.skrivet.plugins.security.core.service.SecurityService;
import com.skrivet.plugins.web.impl.handler.ExceptionInterceptor;
import com.skrivet.plugins.web.impl.resolver.LoginUserArgumentResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
@AutoConfigureAfter(SecurityService.class)
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    private SecurityService securityService;

    //解决PUT/DELETE获取不到数据的问题
    @Bean
    public FormContentFilter httpPutFormContentFilter() {
        return new FormContentFilter();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ExceptionInterceptor(securityService)).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "TRACE", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations(
//                "classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        if (securityService == null) {
            logger.info("have not config permission，will not analysis LoginUser entity!");
            return;
        }
        argumentResolvers.add(new LoginUserArgumentResolver(securityService));
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter convert : converters) {
            if (convert instanceof MappingJackson2HttpMessageConverter) {
                //空字符串处理，转为NULL
                ObjectMapper objectMapper = ((MappingJackson2HttpMessageConverter) convert).getObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addDeserializer(String.class, new StdDeserializer<String>(String.class) {
                    @Override
                    public String deserialize(JsonParser p, DeserializationContext context) throws IOException, JsonProcessingException {
                        String result = StringDeserializer.instance.deserialize(p, context);
                        if (StringUtils.isEmpty(result)) {
                            return null;
                        }
                        return result;
                    }
                });
                //指定遇到date按照这种格式转换
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                objectMapper.setDateFormat(fmt);
                objectMapper.registerModule(module);
            }
        }
    }


}
