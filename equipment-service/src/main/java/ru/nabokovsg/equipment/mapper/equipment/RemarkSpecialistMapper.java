package ru.nabokovsg.equipment.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.equipment.dto.remarkSpecialist.NewRemarkSpecialistDto;
import ru.nabokovsg.equipment.dto.remarkSpecialist.ResponseRemarkSpecialistDto;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.model.equipment.EquipmentPartElement;
import ru.nabokovsg.equipment.model.equipment.RemarkSpecialist;

@Mapper(componentModel = "spring")
public interface RemarkSpecialistMapper {

    @Mapping(source = "remarkDto.equipmentId", target = "equipmentId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "remarkDto.remark", target = "remark")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(target = "partElementName", ignore = true)
    @Mapping(target = "id", ignore = true)
    RemarkSpecialist mapWithEquipmentElement(NewRemarkSpecialistDto remarkDto, EquipmentElement element);

    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    void mapWithEquipmentPartElement(@MappingTarget RemarkSpecialist remarkSpecialist, EquipmentPartElement partElement);

    @Mapping(source = "remark", target = "remark")
    RemarkSpecialist mapToUpdateRemarkByEquipment(@MappingTarget RemarkSpecialist remarkSpecialist, String remark);

    ResponseRemarkSpecialistDto mapToResponseRemarkSpecialistDto(RemarkSpecialist remarkSpecialist);
}