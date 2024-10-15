package org.example.idealstore.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConvertObjects {

    private final ModelMapperConfig mapperConfig;

    public<T,S> S convertObjects(T object, Class<S> type){
        return mapperConfig.modelMapper().map(object, type);
    }

    public<T,S> List<S> convertListObjects(List<T> listObject, Class<S> type){
        return listObject.stream()
                .map(object -> mapperConfig.modelMapper().map(object, type))
                .toList();
    }
}
