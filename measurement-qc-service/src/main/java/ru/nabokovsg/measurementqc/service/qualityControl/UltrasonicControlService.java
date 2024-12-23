package ru.nabokovsg.measurementqc.service.qualityControl;

import ru.nabokovsg.measurementqc.dto.ultrasonicControl.NewUltrasonicControlDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.ResponseUltrasonicControlDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.UpdateUltrasonicControlDto;

import java.util.List;

public interface UltrasonicControlService {

    ResponseUltrasonicControlDto save(NewUltrasonicControlDto defectDto);

    ResponseUltrasonicControlDto update(UpdateUltrasonicControlDto defectDto);

   ResponseUltrasonicControlDto get(Long id);

    List<ResponseUltrasonicControlDto> getAll(Long workJournalId);

    void delete(Long id);
}