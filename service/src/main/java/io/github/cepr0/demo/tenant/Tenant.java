package io.github.cepr0.demo.tenant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tenants")
public class Tenant implements Persistable<String> {
    @JsonProperty("tenantId")
    @Id
    @Column(columnDefinition = "text")
    private String id;

    @Column(nullable = false, columnDefinition = "text")
    private String url;

    @Column(nullable = false, columnDefinition = "text")
    private String username;

    @Column(nullable = false, columnDefinition = "text")
    private String password;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return true;
    }
}
