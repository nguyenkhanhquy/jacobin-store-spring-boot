package live.jacobin.config;

import live.jacobin.filter.ManagerFilter;
import live.jacobin.filter.CustomerFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ManagerFilter> managerFilter(){
        FilterRegistrationBean<ManagerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ManagerFilter());
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
        registrationBean.addUrlPatterns("/checkout");
        registrationBean.addUrlPatterns("/order");
        return registrationBean;
    }

}
