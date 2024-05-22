package com.cafe.server.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePassword {

    private String email;
    private String oldPassword;
    private String newPassword;
}
