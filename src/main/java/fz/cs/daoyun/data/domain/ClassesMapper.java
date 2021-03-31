package fz.cs.daoyun.data.domain;

import fz.cs.daoyun.mapper.provider.ClassesSqlProvider;
import fz.cs.daoyun.domain.Classes;
import org.apache.ibatis.annotations.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ClassesMapper {
    @Delete({
        "delete from t_class",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({

            "insert into t_class (" +
                    "classes_id, classes_name, school, department, teacher_id, teacher_name",
            ") " +
            "values(" +
                    "#{classesId,jdbcType=INTEGER},  #{classesName,jdbcType=VARCHAR}, #{school,jdbcType=VARCHAR}, " +
                    "#{department,jdbcType=VARCHAR}, #{teacher_id,jdbcType=VARCHAR}, #{teacher_name,jdbcType=VARCHAR}" +
            ")"

    })
    int insert(Classes record);

    @InsertProvider(type= ClassesSqlProvider.class, method="insertSelective")
    int insertSelective(Classes record);

//
    @Select("select * from t_class where id = #{id,jdbcType=INTEGER}")
    Classes selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ClassesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Classes record);

    @Update({
        "update t_class",
        "set classes_id = #{classesId,jdbcType=INTEGER},",
          "classes_name = #{classesName,jdbcType=VARCHAR},",
          "school = #{school,jdbcType=VARCHAR},",
          "department = #{department,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Classes record);


    @Select("select * from t_class")
    List<Classes> selectAll();

    @Select("select * from  t_class where classes_id = #{classesId,jdbcType=INTEGER}")
    Classes selectByClassId(Integer classId);

    @Delete("delete  from t_class where classes_id = #{classesId,jdbcType=INTEGER}")
    void deleteByClassId(Integer classesId);


    @Select("select classes_id, classes_name, school, department, teacher_id, teacher_name from t_class where teacher_id =  #{name,jdbcType=VARCHAR}")
    List<Classes> selectByTeacherId(String name);
}
