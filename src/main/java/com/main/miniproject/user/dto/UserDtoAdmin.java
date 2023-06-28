package com.main.miniproject.user.dto;

import lombok.Data;

@Data
public class UserDtoAdmin {
    private String searchBy;

    private String searchQuery = "";
}
