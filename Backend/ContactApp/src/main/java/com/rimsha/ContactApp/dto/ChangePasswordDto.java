package com.rimsha.ContactApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDto {

    public String currentPassword;
    public String newPassword;
}
