package vn.com.javaapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @JsonProperty("id")
    private Long userId;
    private String username;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Integer roleId;
    private Timestamp birthday;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer isEnabled;
}
