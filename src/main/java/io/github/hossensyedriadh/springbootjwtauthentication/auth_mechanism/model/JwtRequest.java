package io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

@ApiModel(value = "JwtRequest", description = "Request body to authenticate and get JWTs")
@NoArgsConstructor
@Data
public final class JwtRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 7511956042194663011L;

    @ApiModelProperty(value = "Username of the user", required = true)
    @NonNull
    private String username;

    @ApiModelProperty(value = "Password (in plain text) of the user", required = true)
    @NonNull
    private String password;
}
