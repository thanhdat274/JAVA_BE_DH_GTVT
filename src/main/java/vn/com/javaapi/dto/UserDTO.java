package vn.com.javaapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
    private String username;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Integer roleId;

    @Column(name = "birthday")
    private Timestamp birthday;

    @Column(name = "create_date")
    private Timestamp createdAt;
}
