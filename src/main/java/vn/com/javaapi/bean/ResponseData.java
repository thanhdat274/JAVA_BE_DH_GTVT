package vn.com.javaapi.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.javaapi.utils.ObjectUtil;

import java.util.Map;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
	private String code;
	private String message;
	private Map<String, Object> data;

	@Override
	public String toString() {
		return ObjectUtil.convertToString(this);
	}
}
