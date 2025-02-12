package com.example.scheduledemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="사용자 이름은 필수 입력 항목입니다.")
    @Size(min=3, max=20, message="사용자 이름은 3~20자 사이여야 합니다.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수 항목입니다.")
    @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
    @Column(nullable = false)
    private String password;

}
