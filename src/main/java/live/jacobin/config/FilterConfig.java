package live.jacobin.config;

import live.jacobin.filter.ManagerFilter;
import live.jacobin.filter.CustomerFilter;
import live.jacobin.filter.StaffFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ManagerFilter> managerFilter(){
        FilterRegistrationBean<ManagerFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ManagerFilter());
        registrationBean.addUrlPatterns("/dashboard/manager-staff");
        registrationBean.addUrlPatterns("/dashboard/manager-customer");
        registrationBean.addUrlPatterns("/dashboard/manager-revenue");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<StaffFilter> staffFilter(){
        FilterRegistrationBean<StaffFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new StaffFilter());
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
