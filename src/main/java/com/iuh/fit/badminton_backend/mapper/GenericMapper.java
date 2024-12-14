package com.iuh.fit.badminton_backend.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public GenericMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Ánh xạ từ entity sang DTO
     *
     * @param entity   đối tượng entity
     * @param dtoClass lớp DTO đích
     * @param <D>      kiểu dữ liệu DTO
     * @param <E>      kiểu dữ liệu entity
     * @return đối tượng DTO đã ánh xạ
     */
    public <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        if (entity == null) {
            return null; // Return null if the source entity is null
        }
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Ánh xạ từ DTO sang entity
     *
     * @param dto         đối tượng DTO
     * @param entityClass lớp entity đích
     * @param <D>         kiểu dữ liệu DTO
     * @param <E>         kiểu dữ liệu entity
     * @return đối tượng entity đã ánh xạ
     */
    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        if (dto == null) {
            return null; // Return null if the source entity is null
        }
        return modelMapper.map(dto, entityClass);
    }
}