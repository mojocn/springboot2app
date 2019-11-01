package springboot2app.common.util;

import springboot2app.common.annotation.Menu;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class MenuListener implements BeanPostProcessor {
    public Map<String, Map<String, ArrayList<MenuItem>>> menus = new HashMap<>();

    public ArrayList<MenuItem> rows = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                Menu menuTag = AnnotationUtils.findAnnotation(method, Menu.class);
                if (null != menuTag) {
                    String mod = menuTag.mod();
                    String sub = menuTag.sub();
                    Map<String, ArrayList<MenuItem>> modMap = menus.computeIfAbsent(mod, k -> new HashMap<>());
                    ArrayList<MenuItem> subList = modMap.computeIfAbsent(sub, k -> new ArrayList<>());
                    MenuItem mi = MenuItem.of(menuTag);
                    subList.add(mi);
                    rows.add(mi);
                }
            }
        }
        return bean;
    }

}
