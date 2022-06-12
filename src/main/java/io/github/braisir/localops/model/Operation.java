package io.github.braisir.localops.model;

import java.util.List;

import lombok.Data;

@Data
public class Operation {

	private List<String> alias;
	private String desc;
	private Command cmd;
}
