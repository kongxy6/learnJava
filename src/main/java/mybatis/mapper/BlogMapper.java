package mybatis.mapper;

import mybatis.model.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface BlogMapper {

    Blog selectBlog(int id);

    Blog selectBlogByTitle(String title);

    Blog selectBlogByTitle2(String title);

    Blog selectBlogByPostTime(Date date);

    // 当方法只有一个参数时，参数时随意的，类型匹配即可【此时参数对象就是该参数】
    // 当有多个参数的是时候需要指定参数的别名与映射语句保持一致【此时为paramMap】
    Blog selectBlogByUpdateTime(@Param("time") Date date, @Param("title") String title);

    void insertBlog(Blog blog);

}
