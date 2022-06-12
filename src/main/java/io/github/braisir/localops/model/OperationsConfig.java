package io.github.braisir.localops.model;

import java.util.Map;

import lombok.Data;

@Data
public class OperationsConfig {

	private Defaults defaults;
	private Map<String, Operation> operations;
}
