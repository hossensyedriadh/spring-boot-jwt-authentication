package io.github.hossensyedriadh.springbootjwtauthentication.auth_mechanism.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serial;
import java.io.Serializable;

@ApiModel(value = "AccessTokenRequest", description = "Request body to request for new Access Token")
@NoArgsConstructor
@Data
public final class AccessTokenRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6286167411497156217L;

    @NonNull
    @ApiModelProperty(value = "Refresh Token", required = true)
    private String refresh_token;
}
