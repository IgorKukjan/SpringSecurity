package ru.javabegin.training.services;

import org.springframework.stereotype.Service;

@Service("process")
public class ProcessImpl implements ProcessInterface {

	@Override
	public String getMessage() {
		return "my message";
	}
}
