package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.*;
import org.example.shopvue.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据uid查用户
    @Select("select * from user where uid=#{uid}")
    User findUidUser(String uid);

    //根据手机号查找用户
    @Select("select * from user where phone=#{phone}")
    User findPhoneUser(String phone);

    //注册用户
    @Insert("insert into user (uid,name,phone,password) value (#{uid},#{name},#{phone},#{password})")
    int insertUser(User user);

    //查找用户数量
    @Select("select count(*) from user")
    int findUserNum();

    //查询手机号是否存在
    @Select("select count(*) from user where phone=#{phone}")
    int findPhone(String phone);

    //登录功能
    @Select("select * from user where (uid=#{uid} or phone=#{phone}) and password=#{password}")
    User findUser(@Param("uid") String uid, @Param("phone") String phone, @Param("password") String password);

    @Select("select phone from user where uid=#{uid}")
    String findPhoneByUid(String uid);

    //修改用户信息
    @Update("update user set name=#{name},birth=#{birth},gender=#{gender} where uid=#{uid}")
    int updateUser(User user);

    //修改用户密码
    @Update("update user set password=#{password} where uid=#{uid}")
    int updatePassword(@Param("uid") String uid, @Param("password") String password);

    //修改用户手机号
    @Update("update user set phone=#{phone} where uid=#{uid}")
    int updatePhone(@Param("uid") String uid, @Param("phone") String phone);

    //查找所有的用户
    @Select("select * from user")
    List<User> findAll();

    //根据邮箱查找用户
    @Select("select * from user where email=#{email}")
    User findUserByEmail(String email);

    //查找邮箱
    @Select("select email from user where uid=#{uid}")
    String findEmailById(String uid);

    //修改邮箱
    @Update("update user set email=#{email} where uid = #{uid}")
    int updateEmail(@Param("uid") String uid, @Param("email") String email);
}
