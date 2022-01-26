package io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@ApiModel(value = "JwtResponse", description = "Response body containing JWTs when successfully authenticated")
@AllArgsConstructor
@Getter
@Setter
public final class JwtResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2849747568694419915L;

    @ApiModelProperty(value = "Access Token")
    private String access_token;

    @ApiModelProperty(value = "Refresh Token", notes = "To be used to get new Access token when current Access token has expired")
    private String refresh_token;

    @ApiModelProperty(value = "Type of the JWTs")
    private String token_type;
}
