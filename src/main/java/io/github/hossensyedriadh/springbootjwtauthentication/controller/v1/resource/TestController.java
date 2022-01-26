package io.github.hossensyedriadh.springbootjwtauthentication.controller.v1.resource;

import com.nimbusds.jose.shaded.json.JSONObject;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/test", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TestController {
    @Operation(method = "GET", summary = "For any authenticated user", description = "Accessible by any authority")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_MODERATOR', 'ROLE_MEMBER')")
    @GetMapping("/")
    public ResponseEntity<?> forEveryone() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Hi " + username + "!");

        return ResponseEntity.ok(jsonObject.toJSONString());
    }

    @Operation(method = "GET", summary = "For admin and moderator", description = "Accessible by Administrator and Moderator only")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMINISTRATOR', 'ROLE_MODERATOR')")
    @GetMapping("/mod")
    public ResponseEntity<?> forAdminsAndMods() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Hi " + username + "! This is only for admins, moderators");

        return ResponseEntity.ok(jsonObject.toJSONString());
    }

    @Operation(method = "GET", summary = "For admin", description = "Accessible by Administrator only")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    @GetMapping("/admin")
    public ResponseEntity<?> forAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Hi " + username + "! This is only for admins");

        return ResponseEntity.ok(jsonObject.toJSONString());
    }
}
