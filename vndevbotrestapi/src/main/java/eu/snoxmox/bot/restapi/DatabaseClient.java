package eu.snoxmox.bot.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class DatabaseClient {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/license/{license}")
    public String getUser(@PathVariable("license") String license) throws IOException {
        List<License> licenses = jdbcTemplate.query("SELECT * FROM license_list WHERE license = :license",
                new MapSqlParameterSource()
                        .addValue("license", license), new BeanPropertyRowMapper<>(License.class));
        if (licenses.size() == 0) {
            return objectMapper.writeValueAsString(new ResponseEntity<>("License not found", HttpStatus.NOT_FOUND));
        }
        return objectMapper.writeValueAsString(licenses.get(0));
    }

    @PostMapping("/license")
    public ResponseEntity addUser(@RequestBody License user) {
        jdbcTemplate.update("INSERT INTO license_list (license, id) VALUES (:license, :id)",
                new MapSqlParameterSource()
                        .addValue("license", user.getLicense())
                        .addValue("id", user.getId())
                        .addValue("name", user.getName()));
        return ResponseEntity.ok(HttpStatus.OK);
    }


}