package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.NewAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.UpdateAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.model.AcceptableDeviationsGeodesy;

@Mapper(componentModel = "spring")
public interface AcceptableDeviationsGeodesyMapper {

    AcceptableDeviationsGeodesy mapToAcceptableDeviationsGeodesy(NewAcceptableDeviationsGeodesyDto geodesyDto);

    AcceptableDeviationsGeodesy mapToUpdateAcceptableDeviationsGeodesy(UpdateAcceptableDeviationsGeodesyDto geodesyDto);

    ResponseAcceptableDeviationsGeodesyDto mapToResponseAcceptableDeviationsGeodesyDto(
                                                                         AcceptableDeviationsGeodesy geodesy);
}