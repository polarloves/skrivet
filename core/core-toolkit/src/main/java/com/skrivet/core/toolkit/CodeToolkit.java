package com.skrivet.core.toolkit;

import com.skrivet.core.common.convert.CodeConvert;

import java.io.Serializable;

public class CodeToolkit {
    private static CodeConvert codeConvert;

    public static void setCodeConvert(CodeConvert codeConvert) {
        CodeToolkit.codeConvert = codeConvert;
    }

    public static String render(String code, String messageTemplate, Serializable[] variables) {
        return codeConvert.convert(code, messageTemplate, variables);
    }
}
