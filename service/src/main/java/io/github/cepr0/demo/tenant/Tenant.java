package io.github.cepr0.demo.tenant;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @Column(columnDefinition = "text")
    private String id;

    @Column(nullable = false, columnDefinition = "text")
    private String url;

    @Column(nullable = false, columnDefinition = "text")
    private String username;

    @Column(nullable = false, columnDefinition = "text")
    private String password;
}
