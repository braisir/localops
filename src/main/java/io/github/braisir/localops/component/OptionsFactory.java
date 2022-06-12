package io.github.braisir.localops.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

import io.github.braisir.localops.model.Operation;
import io.github.braisir.localops.model.OperationsConfig;

public class OptionsFactory {

	private OptionsFactory() {
		super();
	}
	
	public static final Options create(OperationsConfig cfg) {
		Options result = new Options();
		OptionGroup oper = new OptionGroup();
		cfg.getOperations()
		   .entrySet()
		   .stream()
		   .flatMap(OptionsFactory::parseOption)
		   .forEach(oper::addOption);
				
		oper.addOption(helpOption());
		oper.addOption(versionOption());
		oper.addOption(checksOption());
		oper.addOption(envOption());
		
		result.addOptionGroup(oper);
		
		return result;
	}
	
	private static final Stream<Option> parseOption(Entry<String, Operation> e) {
		List<Option> los = new ArrayList<Option>();
		los.add(new Option(e.getKey(),e.getValue().getDesc()));
		if (e.getValue().getAlias() != null) {
			for (String alias : e.getValue().getAlias()) {
				los.add(new Option(alias, e.getValue().getDesc()));
			}
		}
		return los.stream();		
	}
	
	public static final String HELP_OPT = "help";
	private static final Option helpOption() {
		return new Option(HELP_OPT, "show avaliable options.");
	}
	
	public static final String VERSION_OPT = "version";
	private static final Option versionOption() {
		return new Option(VERSION_OPT,"show version of project.");
	}
	
	public static final String ENV_OPT = "env";
	private static final Option envOption() {
		return new Option(ENV_OPT, "show enviroment variables in shell.");
	}
	
	public static final String CHECK_OPT = "check";
	private static final Option checksOption() {
		return new Option(CHECK_OPT, "evaluate enviroment checks.");
	}
}
