package com.skrivet.core.toolkit;


import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.BizExp;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionToolkit {
    private ExceptionToolkit() {

    }

    public static String getStackMsg(Exception e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}
