package org.example.shopvue.mapper;

import org.apache.ibatis.annotations.*;
import org.example.shopvue.model.Address;

import java.util.List;

@Mapper
public interface AddressMapper {

    @Select("select * from address where uid=#{uid}")
    public List<Address> selectByUid(String uid);

    @Insert("insert into address (uid,address,region,delivery_phone,delivery_name) values (#{uid},#{address},#{region},#{delivery_phone},#{delivery_name})")
    public int insert(Address address);

    //更新地址信息
    @Update("update address set delivery_phone = #{delivery_phone} ,delivery_name = #{delivery_name} ,region = #{region} ,address= #{address} where uid=#{uid} and address_id = #{address_id}")
    public int update(Address address);

    //删除地址信息
    @Delete("delete from address where uid=#{uid} and address_id=#{address_id}")
    public int delete(@Param("uid") String uid, @Param("address_id") int address_id);
}
