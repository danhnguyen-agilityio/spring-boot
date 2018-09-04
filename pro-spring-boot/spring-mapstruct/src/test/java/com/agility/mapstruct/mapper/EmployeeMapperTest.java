package com.agility.mapstruct.mapper;

import com.agility.mapstruct.models.DivisionDTO;
import com.agility.mapstruct.models.Employee;
import com.agility.mapstruct.models.EmployeeDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class EmployeeMapperTest {

    private EmployeeMapper mapper = Mappers.getMapper(EmployeeMapper.class);
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Test
    public void givenEmpDTONestedMappingToEmp_whenMaps_thenCorrect() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setDivision(new DivisionDTO(1, "Division1"));
        Employee entity = mapper.employeeDTOToEmployee(dto);

        assertEquals(dto.getDivision().getId(), entity.getDivision().getId());
        assertEquals(dto.getDivision().getName(), entity.getDivision().getName());
    }

    @Test
    public void givenEmpStartDtMappingToEmpDTO_whenMaps_thenCorrect() throws ParseException {
        Employee entity = new Employee();
        entity.setStartDt(new Date());
        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(),
            entity.getStartDt().toString());
    }

    @Test
    public void givenEmpDTOStartDtMappingToEmp_whenMaps_thenCorrect() throws ParseException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeStartDt("01-04-2016 01:00:00");
        Employee entity = mapper.employeeDTOToEmployee(dto);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        assertEquals(format.parse(dto.getEmployeeStartDt()).toString(),
            entity.getStartDt().toString());
    }


}