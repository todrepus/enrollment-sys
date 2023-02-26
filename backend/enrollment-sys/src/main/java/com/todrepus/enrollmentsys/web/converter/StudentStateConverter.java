package com.todrepus.enrollmentsys.web.converter;

import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.domain.member.Student;
import com.todrepus.enrollmentsys.domain.member.StudentState;
import org.springframework.core.convert.converter.Converter;

public class StudentStateConverter implements Converter<String, StudentState> {

    @Override
    public StudentState convert(String source) {
        return StudentState.resolve(source);
    }
}
