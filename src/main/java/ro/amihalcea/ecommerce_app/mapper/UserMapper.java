package ro.amihalcea.ecommerce_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.amihalcea.ecommerce_app.dto.UserDTO;
import ro.amihalcea.ecommerce_app.model.User;
import ro.amihalcea.ecommerce_app.model.UserRole;

@Mapper
public interface UserMapper {

    @Mapping(target = "role",expression = "java(getRoleOrDefault(dto))")
    @Mapping(target = "id",source = "dto.id")
    @Mapping(target = "username",source = "dto.username")
    @Mapping(target = "password",source = "dto.password")
    @Mapping(target = "email",source = "dto.email")
    @Mapping(target = "firstName",source = "dto.firstName")
    @Mapping(target = "lastName",source = "dto.lastName")
    User mapFromDto(UserDTO dto);

    @Mapping(target = "role",expression = "java(getUserRoleFromModel(user))")
    @Mapping(target = "id",source = "id")
    @Mapping(target = "username",source = "username")
    @Mapping(target = "password",source = "password")
    @Mapping(target = "email",source = "email")
    @Mapping(target = "firstName",source = "firstName")
    @Mapping(target = "lastName",source = "lastName")
    UserDTO mapFromModel(User user);

    default UserRole getRoleOrDefault(UserDTO dto){
        String role = dto.getRole();
        return role != null && !role.isEmpty() ? UserRole.valueOf(role.toUpperCase()) : UserRole.USER;
    }
    default String getUserRoleFromModel(User user){
        return user.getRole().name();
    }
}
