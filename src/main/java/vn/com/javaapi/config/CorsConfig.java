package vn.com.javaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import vn.com.javaapi.constant.Constant;

@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

//		// Tạo một mảng chứa các đường dẫn web được phép
//		String[] allowedOrigins = {
//				"https://example.com",
//				"https://another-domain.com",
//				Constant.BaseUrlFE
//		};
//
//		// Thêm các đường dẫn từ mảng vào cấu hình CORS
//		for (String origin : allowedOrigins) {
//			corsConfiguration.addAllowedOrigin(origin);
//		}

		// Thay thế "*" bằng các nguồn gốc mà bạn muốn cho phép
		corsConfiguration.addAllowedOriginPattern("*");

		// Cho phép sử dụng Cookie
		corsConfiguration.setAllowCredentials(true);

		// Cấu hình các phương thức HTTP cho phép (GET, POST, PUT, DELETE, vv.)
		corsConfiguration.addAllowedMethod("*");

		// Cấu hình các tiêu đề yêu cầu được phép
		corsConfiguration.addAllowedHeader("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return new CorsFilter(source);
	}
}
