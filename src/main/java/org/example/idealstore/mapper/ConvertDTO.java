package org.example.idealstore.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConvertDTO{

    private final ModelMapperConfig mapperConfig;

    public<T,S> S convertObjects(T object, Class<S> type){
        return mapperConfig.modelMapper().map(object, type);
    }
}
