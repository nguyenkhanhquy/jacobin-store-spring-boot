package live.jacobin.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorPageRegistrar implements ErrorPageRegistrar {

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // Đăng ký trang lỗi 404
        registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error-404"));

        // Đăng ký trang lỗi 500
        registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-500"));

        // Đăng ký trang lỗi cho mọi loại ngoại lệ
        registry.addErrorPages(new ErrorPage(Throwable.class, "/error-java"));
    }

}
