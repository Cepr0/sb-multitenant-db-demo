package io.github.cepr0.demo.repo;

import io.github.cepr0.demo.model.Model;
import org.springframework.data.repository.CrudRepository;

public interface ModelRepo extends CrudRepository<Model, String> {
}
