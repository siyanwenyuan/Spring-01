package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pojo.User;
@Mapper
public interface UserMapper {

    /**
     * 根据用户名和密码查询用户对象
     * @param username
     * @param password
     * @return
     */
    @Select("select * from tb_user where username = #{username} and password = #{password}")
    User select(@Param("username") String username,@Param("password") String password);

    /**
     * 首先对进行添加的用户名进行查询，如果已经存在则不需要添加
     * @param name
     * @return
     */
    @Select("select *from tb_user where username=#{username}")
    User selectByUsername(String name);


    /**
     * 添加用户
     * @param user
     */
    @Insert("insert into tb_user values(null,#{username},#{password})")
    void Add(User user);


}
