package com.pl.project.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class ObjectMapperResolver implements ContextResolver<ObjectMapper>
{
    private final ObjectMapper mapper;

    public ObjectMapperResolver()
    {
        mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        return mapper;
    }
}
