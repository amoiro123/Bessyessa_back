package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.ReservationDTO;
import com.bessy.productservice.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper extends MapperUtil {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    @Mapping(source = "loanedUntil", target = "loanedUntil", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "loanedFrom", target = "loanedFrom", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "loanedOn", target = "loanedOn", qualifiedByName = "localDateTimeToString")
    ReservationDTO toDto(Reservation product);

    @Mapping(source = "loanedUntil", target = "loanedUntil", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "loanedFrom", target = "loanedFrom", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "loanedOn", target = "loanedOn", qualifiedByName = "stringToLocalDateTime")
    Reservation toEntity(ReservationDTO productDTO);
}