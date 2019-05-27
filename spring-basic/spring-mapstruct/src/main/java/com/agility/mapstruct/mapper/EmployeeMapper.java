package com.agility.mapstruct.mapper;

import com.agility.mapstruct.models.Division;
import com.agility.mapstruct.models.DivisionDTO;
import com.agility.mapstruct.models.Employee;
import com.agility.mapstruct.models.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;
import java.util.List;

@Mapper
public interface EmployeeMapper {

    @Mappings({
        @Mapping(target = "employeeId", source = "entity.id"),
        @Mapping(target = "employeeName", source = "entity.name"),
        @Mapping(target = "employeeStartDt", source = "entity.startDt",
            dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    EmployeeDTO employeeToEmployeeDTO(Employee entity);

    @Mappings({
        @Mapping(source = "dto.employeeId", target = "id"),
        @Mapping(source = "dto.employeeName", target = "name"),
        @Mapping(target = "startDt", source = "dto.employeeStartDt",
            dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    Employee employeeDTOToEmployee(EmployeeDTO dto);

    DivisionDTO divisionToDivisionDTO(Division entity);

    Division divisionDTOToDivision(DivisionDTO dto);

    List<EmployeeDTO> employeeToEmployeeDTO(Collection<Employee> employees);

}
