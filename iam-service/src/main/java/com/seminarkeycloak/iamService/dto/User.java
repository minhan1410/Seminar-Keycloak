package com.seminarkeycloak.iamService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @JsonProperty(namespace = "id", access = JsonProperty.Access.READ_ONLY)
    private String id;

    @JsonProperty(namespace = "username")
    private String username;

    @JsonProperty(namespace = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(namespace = "firstName")
    private String firstName;

    @JsonProperty(namespace = "lastName")
    private String lastName;

    @JsonProperty(namespace = "email")
    private String email;

    //    Them 1 so truong khong co trong keycloak: UserRepresentation

    @JsonProperty(namespace = "phoneNumber")
    private String phoneNumber;

    @JsonProperty(namespace = "address")
    private String address;

    @JsonIgnore
    protected Map<String, List<String>> attributes;

    @JsonIgnore
    protected Boolean enabled;

    @JsonIgnore
    protected Boolean emailVerified;

    public Map<String, List<String>> initAttributes() {
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phoneNumber", new ArrayList<>(List.of(this.getPhoneNumber())));
        attributes.put("address", new ArrayList<>(List.of(this.getAddress())));
        return attributes;
    }
}
