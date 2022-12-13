package web;

import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.User;
import util.SqlSessionFactoryUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "registerServlet", value = "/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 接受用户的数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 封装用户对象
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        // 调用Mapper,根据用户名查询用户对象

      /*  String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
*/


        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper usermapper = sqlSession.getMapper(UserMapper.class);

        User u = usermapper.selectByUsername(username);

        // 判断用对象是否为null
        if(u==null){
            // 用户名不存在,则可以添加用户
            usermapper.Add(user);
            // 提交事务
            sqlSession.commit();
            // 关闭资源
            sqlSession.close();
        }else{
            // 说明用户名存在，则不能添加代码

            // 要设置编码
            response.setContentType("text/html;charset=utf-8");
            // 通过response输出用户名不存在的信息
            response.getWriter().write("用户名已经存在");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        this.doGet(request, response);
    }
}
