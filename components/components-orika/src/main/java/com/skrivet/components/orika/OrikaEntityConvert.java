package com.skrivet.components.orika;

import com.skrivet.core.common.convert.EntityConvert;
import ma.glasnost.orika.MapperFactory;

import java.util.List;

public class OrikaEntityConvert implements EntityConvert {
    private MapperFactory mapperFactory;

    public OrikaEntityConvert(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    @Override
    public <T> T convert(Object source, Class<T> cls) {
        return mapperFactory.getMapperFacade().map(source,cls);
    }

    @Override
    public <T> T convertIgnoreFiledError(Object source, Class<T> cls) {
        return mapperFactory.getMapperFacade().map(source,cls);
    }

    @Override
    public <T> List<T> convertList(List<?> sources, Class<T> cls) {
        return mapperFactory.getMapperFacade().mapAsList(sources,cls);
    }

    @Override
    public <T> List<T> convertListIgnoreFiledError(List<?> sources, Class<T> cls) {
        return mapperFactory.getMapperFacade().mapAsList(sources,cls);
    }
}
