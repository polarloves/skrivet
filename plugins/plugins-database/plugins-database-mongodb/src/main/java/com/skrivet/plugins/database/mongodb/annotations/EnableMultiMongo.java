package com.skrivet.plugins.database.mongodb.annotations;

import com.skrivet.plugins.database.mongodb.registrar.MultiMongoConfiguredRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MultiMongoConfiguredRegistrar.class)
public @interface EnableMultiMongo {
}
