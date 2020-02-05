package mybatis;

import mybatis.mapper.BlogMapper;
import mybatis.model.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TestCase {

    private static String resource = "mybatis/mybatis-config.xml";
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;

    {
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 测试resultMap 和 resultType的可用性，说明
     */
    @org.junit.jupiter.api.Test
    public void selectBlogByTitle() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlogByTitle("mybatis");
            System.out.println(blog.getTitle());
        } finally {
            session.close();
        }

    }

    @org.junit.jupiter.api.Test
    public void selectBlogByTitle2() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlogByTitle2("mybatis");
            System.out.println(blog.getLastUpdateTime());
        } finally {
            session.close();
        }

    }

    @org.junit.jupiter.api.Test
    public void selectBlogByPostTime() throws ParseException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Blog blog = mapper.selectBlogByPostTime(sDateFormat.parse("2019-1-1 0:0:1"));
            System.out.println(blog.getPostTime());
        } finally {
            session.close();
        }
    }

    @org.junit.jupiter.api.Test
    public void selectBlogByUpdateTime() throws ParseException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Blog blog = mapper.selectBlogByUpdateTime(sDateFormat.parse("2019-3-12 0:0:1"), "mybatis");
            System.out.println(blog);
        } finally {
            session.close();
        }
    }

    @Test
    void insertBlog() {
        // 默认不自动提交，true自动提交
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setId(6);
        blog.setTitle("insert");
        blog.setAuthorId(5);
        mapper.insertBlog(blog);
        System.out.println("success");
        session.commit();
        session.close();
    }

}
