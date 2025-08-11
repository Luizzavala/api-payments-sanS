package me.quadradev.application.core.mapper;

import me.quadradev.application.core.dto.RoleDto;
import me.quadradev.application.core.dto.RoleRequest;
import me.quadradev.application.core.model.Role;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface RoleMapper {
    Role toEntity(RoleRequest request);
    RoleDto toDto(Role role);
}
