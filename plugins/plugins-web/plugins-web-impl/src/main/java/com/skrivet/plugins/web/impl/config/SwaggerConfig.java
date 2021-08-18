package com.skrivet.plugins.web.impl.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import com.skrivet.core.common.convert.CodeConvert;
import com.skrivet.core.common.convert.ConvertTemplate;
import com.skrivet.core.common.entity.LoginUser;
import com.skrivet.plugins.web.impl.annotation.SkrivetDoc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Configuration
@ConditionalOnProperty(name = "skrivet.doc.enable", havingValue = "true")
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Autowired(required = false)
    private CodeConvert codeConvert;
    @Value("${skrivet.security.type:shiro}")
    private String securityType;
    @Value("${skrivet.jwt.tokenKey:token}")
    private String tokenKey;
    @Value("${skrivet.project.package}")
    private String projectPackage;
    @Value("${skrivet.security.webCheck:true}")
    private boolean check;
    private Docket doc(boolean skrivetDoc, String name, String packageName) {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(LoginUser.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(input -> {
                    if (skrivetDoc) {
                        return input.findControllerAnnotation(SkrivetDoc.class).isPresent();
                    } else {
                        return !input.findControllerAnnotation(SkrivetDoc.class).isPresent();
                    }
                })
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .paths(PathSelectors.any())
                .build().groupName(name);
        if (securityType.equals("jwt")&&check) {
            ParameterBuilder aParameterBuilder = new ParameterBuilder();
            aParameterBuilder
                    .name(tokenKey)
                    .description("授权token")
                    .required(true)
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .build();
            List<Parameter> aParameters = Lists.newArrayList();
            aParameters.add(aParameterBuilder.build());
            docket.globalOperationParameters(aParameters);
        }
        return docket;
    }
    @ConditionalOnProperty(name = "skrivet.doc.enableProjectDoc", havingValue = "true", matchIfMissing = true)
    @Bean
    public Docket projectDoc() {
        return doc(false, "项目接口文档", projectPackage);
    }

    @Bean
    @ConditionalOnProperty(name = "skrivet.doc.enableSkrivetDoc", havingValue = "true", matchIfMissing = true)
    public Docket skrivetDoc() {
        return doc(true, "框架接口文档", "com.skrivet");
    }

    private ApiInfo apiInfo() {
        String describe =
                "<div onclick=\"if(document.getElementById('code').style.display=='none'){document.getElementById('code').style.display='';}else{document.getElementById('code').style.display='none';}\"  style=\"background-color: #f5e8e8;border: 1px solid #e8c6c7;float: none;clear: both;overflow: hidden; display: block; padding: 10px;font-size: 100%;font: inherit;vertical-align: baseline;cursor:pointer\">通用返回格式</div>\n" +
                        "<div id='code' style='display:none;border: 1px solid #f5e8e8;padding:10px;'><table cellspacing=\"0\" style=\"border:1px solid #cccccc;width:100%;\">"
                        + "  <tr  style=\"border:1px solid #cccccc\">\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">节点</td>\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">描述</td>\n" +
                        "  <td  style=\"border:1px solid #cccccc;\">示例</td></tr>"
                        + "  <tr  style=\"border:1px solid #cccccc\">\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">code</td>\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">响应代码，详见响应代码表</td>\n" +
                        "  <td  style=\"border:1px solid #cccccc;\">000000</td></tr>"
                        + "  <tr  style=\"border:1px solid #cccccc\">\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">msg</td>\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">描述信息</td>\n" +
                        "  <td  style=\"border:1px solid #cccccc;\">服务器异常,请重试</td></tr>"
                        + "  <tr  style=\"border:1px solid #cccccc\">\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">count</td>\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">数据条目数</td>\n" +
                        "  <td  style=\"border:1px solid #cccccc;\">0</td></tr>"
                        + "  <tr  style=\"border:1px solid #cccccc\">\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">data</td>\n" +
                        "    <td  style=\"border:1px solid #cccccc;\">数据内容</td>\n" +
                        "  <td  style=\"border:1px solid #cccccc;\">1</td></tr>"
                        + "</table></div>"
                        + "<div onclick=\"if(document.getElementById('codes').style.display=='none'){document.getElementById('codes').style.display='';}else{document.getElementById('codes').style.display='none';}\"  style=\"margin-top:20px;background-color:#c3d9ec;border: 1px solid #e8c6c7;float: none;clear: both;overflow: hidden; display: block; padding: 10px;font-size: 100%;font: inherit;vertical-align: baseline;cursor:pointer\">代码对应关系表</div>\n" +
                        buildCodeInfo();
        return new ApiInfoBuilder()
                .title("skrivet快速开发平台在线接口")
                .description(describe)
                .version("1.0.0")
                .contact(new Contact("polarloves", "", "1107061838@qq.com"))
                .build();

    }

    private String buildCodeInfo() {
        if (codeConvert == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder("<div id='codes' style='border: 1px solid #c3d9ec;padding:10px;display:none;'><table cellspacing=\"0\" style=\"border:1px solid #cccccc;\">");
        Map<String, ConvertTemplate> codes = new TreeMap<>();
        codes.putAll(codeConvert.codes());
        stringBuilder.append("  <tr  style=\"border:1px solid #cccccc\">\n" +
                "    <td  style=\"border:1px solid #cccccc;\">代码</td>\n" +
                "    <td  style=\"border:1px solid #cccccc\">带有变量的内容</td>\n" +
                "  <td  style=\"border:1px solid #cccccc\">不带变量的内容</td></tr>");
        for (String key : codes.keySet()) {
            stringBuilder.append("<tr>");
            stringBuilder.append(" <td  style=\"border:1px solid #cccccc\">");
            stringBuilder.append(key);
            stringBuilder.append("</td>");
            ConvertTemplate convertTemplate = codes.get(key);
            stringBuilder.append(" <td  style=\"border:1px solid #cccccc\">");
            stringBuilder.append(codeConvert.convert(key, convertTemplate.messageTemplate, new Serializable[]{"XX"}));
            stringBuilder.append("</td>");
            stringBuilder.append(" <td  style=\"border:1px solid #cccccc\">");
            stringBuilder.append(codeConvert.convert(key, null, null));
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");

        }
        stringBuilder.append("</table></div>");
        return stringBuilder.toString();
    }
}
