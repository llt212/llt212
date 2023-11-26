package config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;


public class WebConfig implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext arg0) throws ServletException{
        AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMVCConfig.class);
        ctx.setServletContext(arg0);

        Dynamic dispatcher=arg0.addServlet("dispatcher",new DispatcherServlet(ctx));
//        dispatcher.addMapping("/");
//        dispatcher.setLoadOnStartup(1);
    }
}
