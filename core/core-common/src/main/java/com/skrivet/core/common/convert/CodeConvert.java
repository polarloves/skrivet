package com.skrivet.core.common.convert;

import java.io.Serializable;
import java.util.Map;

public interface CodeConvert {
    public String convert(String code, String template, Serializable[] variables);

    public Map<String, ConvertTemplate> codes();
}
