package com.todrepus.enrollmentsys.web.converter;

import com.todrepus.enrollmentsys.domain.member.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class RoleConverter implements Converter<String, Role> {

    @Override
    public Role convert(String title) {
        return Role.resolve(title);
    }
}
