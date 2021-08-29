package com.example.rest_demo.service;

import com.example.rest_demo.RestDemoApplication;
import com.example.rest_demo.TimingExtension;
import com.example.rest_demo.config.HSQLDBTestProfileConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.example.rest_demo.util.ValidationUtil.getRootCause;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        RestDemoApplication.class, HSQLDBTestProfileConfig.class
})
@ActiveProfiles("test")
@Sql(scripts = "classpath:sql/initDBHibernate.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class AbstractServiceTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger(RestaurantServiceTest.class);
    protected <T extends Throwable> void validateRootCause(Class<T> rootExceptionClass, Runnable runnable) {
        assertThrows(rootExceptionClass, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}
