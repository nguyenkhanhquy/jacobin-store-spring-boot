package live.jacobin.config;

import live.jacobin.filter.AdminFilter;
import live.jacobin.filter.CustomerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/dashboard/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<CustomerFilter> customerFilter(){
        FilterRegistrationBean<CustomerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomerFilter());
        registrationBean.addUrlPatterns("/");
        registrationBean.addUrlPatterns("/home");
        registrationBean.addUrlPatterns("/category");
        registrationBean.addUrlPatterns("/search");
        registrationBean.addUrlPatterns("/detail-product");
        registrationBean.addUrlPatterns("/cart");
        return registrationBean;
    }

}
