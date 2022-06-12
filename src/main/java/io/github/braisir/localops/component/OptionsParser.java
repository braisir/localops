package io.github.braisir.localops.component;

import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class OptionsParser {

	private OptionsParser() {
		super();
	}
	
	public static final Optional<CommandLine> parse(String[] args, Options opts) {
		CommandLineParser parser = new DefaultParser();
		try {
			return Optional.of(parser.parse(opts, args));
		} catch (ParseException e) {
			Logger.error(e.getMessage());
			CommandFactory.helpCommand(opts).run();
			return Optional.empty();
		}

	}
}
