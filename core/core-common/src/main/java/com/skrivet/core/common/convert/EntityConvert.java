package com.skrivet.core.common.convert;

import java.util.List;

public interface EntityConvert {
    public <T> T convert(Object source, Class<T> cls);
    public <T> T convertIgnoreFiledError(Object source, Class<T> cls);
    public <T> List<T> convertList(List<?> sources, Class<T> cls);
    public <T> List<T> convertListIgnoreFiledError(List<?> sources, Class<T> cls);
}
