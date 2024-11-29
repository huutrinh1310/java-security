package com.example.demo.model.responses;

import com.example.demo.model.persistence.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private User user;
    @JsonProperty("access_token")
    private String accessToken;
}
