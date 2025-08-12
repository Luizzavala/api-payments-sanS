package me.quadradev.application.core.mapper;

import me.quadradev.application.core.dto.RoleDto;
import me.quadradev.application.core.dto.RoleRequest;
import me.quadradev.application.core.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "menuPermissions", ignore = true)
    Role toEntity(RoleRequest request);

    RoleDto toDto(Role role);
}
