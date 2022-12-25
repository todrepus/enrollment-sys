package com.todrepus.enrollmentsys.web.converter;

import com.todrepus.enrollmentsys.domain.member.Role;
import org.springframework.core.convert.converter.Converter;

public class RoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String title) {
        return Role.resolve(title);
    }
}
