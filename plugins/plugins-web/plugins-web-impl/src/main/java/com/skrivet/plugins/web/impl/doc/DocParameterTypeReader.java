package com.skrivet.plugins.web.impl.doc;

import com.fasterxml.classmate.ResolvedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.readers.parameter.ParameterTypeReader;

import java.util.Set;

import static springfox.documentation.schema.Collections.collectionElementType;
import static springfox.documentation.schema.Collections.isContainerType;
import static springfox.documentation.schema.Types.isBaseType;
import static springfox.documentation.schema.Types.typeNameFor;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class DocParameterTypeReader implements ParameterBuilderPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterTypeReader.class);

    @Override
    public void apply(ParameterContext context) {
        context.parameterBuilder().parameterType(findParameterType(context));
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    public static String findParameterType(ParameterContext parameterContext) {
        ResolvedMethodParameter resolvedMethodParameter = parameterContext.resolvedMethodParameter();
        ResolvedType parameterType = resolvedMethodParameter.getParameterType();
        parameterType = parameterContext.alternateFor(parameterType);

        //Multi-part file trumps any other annotations
        if (isFileType(parameterType) || isListOfFiles(parameterType)) {
            return "form";
        }
        if (resolvedMethodParameter.hasParameterAnnotation(PathVariable.class)) {
            return "path";
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestBody.class)) {
            return "body";
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestPart.class)) {
            return "formData";
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestParam.class)) {
            return determineScalarParameterType(
                    parameterContext.getOperationContext().consumes(),
                    parameterContext.getOperationContext().httpMethod());
        } else if (resolvedMethodParameter.hasParameterAnnotation(RequestHeader.class)) {
            return "header";
        } else if (resolvedMethodParameter.hasParameterAnnotation(ModelAttribute.class)) {
            LOGGER.warn("@ModelAttribute annotated parameters should have already been expanded via "
                    + "the ExpandedParameterBuilderPlugin");
        }
        if (!resolvedMethodParameter.hasParameterAnnotations()) {
            return determineScalarParameterType(
                    parameterContext.getOperationContext().consumes(),
                    parameterContext.getOperationContext().httpMethod());
        }

        return "query";
    }

    private static boolean isListOfFiles(ResolvedType parameterType) {
        return isContainerType(parameterType) && isFileType(collectionElementType(parameterType));
    }

    private static boolean isFileType(ResolvedType parameterType) {
        return MultipartFile.class.isAssignableFrom(parameterType.getErasedType());
    }

    public static String determineScalarParameterType(Set<? extends MediaType> consumes, HttpMethod method) {
        String parameterType = "query";

        if (consumes.contains(MediaType.APPLICATION_FORM_URLENCODED)
                && method == HttpMethod.POST) {
            parameterType = "form";
        } else if (consumes.contains(MediaType.MULTIPART_FORM_DATA)
                && method == HttpMethod.POST) {
            parameterType = "formData";
        }

        return parameterType;
    }
}