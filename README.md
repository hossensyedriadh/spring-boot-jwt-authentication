# spring-boot-jwt-authentication

JWT Authentication Implementation with Spring Boot
<hr>
<p>
For full documentation visit <a href="https://spring-boot-jwt-authentication.herokuapp.com/api/swagger-ui/" target="_blank">Heroku</a>
</p>
<br>
<strong>Credentials:</strong>
<table>
<thead>
<tr>
<th>Username</th>
<th>Password</th>
<th>Authority</th>
</tr>
</thead>
<tbody>
<tr>
<td>admin</td>
<td>imadmin</td>
<td><em>Administrator</em></td>
</tr>
<tr>
<td>mod</td>
<td>immod</td>
<td><em>Moderator</em></td>
</tr>
<tr>
<td>member</td>
<td>immember</td>
<td><em>Member</em></td>
</tr>
</tbody>
</table>
<hr>
<div>
<p>
<strong>Authorization header value must start with "<em>Bearer </em>" (without quotes) followed by the access token</strong>
</p>
<p>
Example: "<em>Bearer access_token</em>" (without quotes)
</p>
</div>
<hr>
<div>
<p>Refresh token should be used to get new access token instead of sending Authentication request again. 
However, if refresh token is expired/invalid then re-authentication is mandatory</p>
</div>