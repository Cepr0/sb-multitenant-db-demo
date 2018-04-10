package io.github.cepr0.demo.service;

import io.github.cepr0.demo.model.Model;

public interface ModelService {
	Iterable<Model> findAll();
	Model save(Model model);
}
