package com.lumen.minuAPI.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserDTO findAll();
    UserDTO findById(@Param("id") int id);
    UserDTO findByProviderId(
        @Param("providerId") String providerId,
        @Param("provider") String provider
    );
    void insertUser(UserDTO user);
    void updateUser(UserDTO user);
    void deleteUser(@Param("id") int id);
}
