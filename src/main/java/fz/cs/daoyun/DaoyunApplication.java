package fz.cs.daoyun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@MapperScan("fz.cs.daoyun.mapper")
@SpringBootApplication(exclude = {QuartzAutoConfiguration.class})
@EnableTransactionManagement
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

@EnableWebMvc
public class DaoyunApplication {

    public static void main(String[] args) {
        System.out.println("welcom to DaoYun");
        SpringApplication.run(DaoyunApplication.class, args);
    }

}
