package ru.nabokovsg.library.service;


import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.NewAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.UpdateAcceptableDeviationsGeodesyDto;

import java.util.List;

public interface AcceptableDeviationsGeodesyService {

    ResponseAcceptableDeviationsGeodesyDto save(NewAcceptableDeviationsGeodesyDto geodesyDto);

    ResponseAcceptableDeviationsGeodesyDto update(UpdateAcceptableDeviationsGeodesyDto geodesyDto);

    List<ResponseAcceptableDeviationsGeodesyDto> getAll(Long equipmentLibraryId);

    void delete(Long id);
}