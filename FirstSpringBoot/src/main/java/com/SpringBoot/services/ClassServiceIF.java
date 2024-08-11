package com.SpringBoot.services;

import java.util.Optional;

import com.SpringBoot.entities.Class;

public interface ClassServiceIF {

	public Optional<Class> saveOne(Class clat);
}
