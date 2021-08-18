package cn.skrivet.test;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;

@RequestMapping("/")
@org.springframework.stereotype.Controller
public class Controller {
    private static final Class<? extends Annotation>[] SERVICE_ANNOTATION_CLASSES = new Class[]{org.springframework.stereotype.Controller.class, Service.class, Repository.class};

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "123";
    }

    public static void main(String[] args) {
        System.err.println(containsAnnotation(com.skrivet.supports.data.web.impl.dict.DictController.class, SERVICE_ANNOTATION_CLASSES));
    }

    protected static boolean containsAnnotation(Class<?> aClass, Class<? extends Annotation>[] classes) {
        for (Class<? extends Annotation> clz : classes) {
            if (aClass.getAnnotation(clz) != null) {
                return true;
            }
        }
        return false;
    }
}
