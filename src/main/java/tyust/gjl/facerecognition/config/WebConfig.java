package tyust.gjl.facerecognition.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tyust.gjl.facerecognition.interceptor.LoginInterceptor;

/**
 * @author : coderWu
 * @date : Created on 17:14 2018/5/13
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/page/search")
                .addPathPatterns("/page/show")
                .addPathPatterns("/page/add");
        super.addInterceptors(registry);
    }
}
