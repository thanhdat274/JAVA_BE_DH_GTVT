package vn.com.javaapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "birthday")
    private Timestamp birthday;

    @Column(name = "create_at")
    private Timestamp createdAt;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "is_enabled")
    private Integer isEnabled;

}



