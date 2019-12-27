package com.jacken.springbootsecurity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jacken.springbootsecurity.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT username,password FROM users WHERE username=#{userName}")
    User findUserByUsername(String userName);

    @Update("UPDATE users SET password = #{password} WHERE username=#{userName}")
    void updateUserPassword(String password, String userName);
}
