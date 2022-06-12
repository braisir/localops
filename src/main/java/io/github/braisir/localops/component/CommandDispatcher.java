package io.github.braisir.localops.component;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import io.github.braisir.localops.model.OperationsConfig;

public class CommandDispatcher {

	private CommandDispatcher() {
		super();
	}
	
	public static final void dispatch(CommandLine cmd, OperationsConfig cfg, Options avaliable) {
		Option[] active = cmd.getOptions();
		if (active.length == 0) {			
			CommandFactory
				.helpCommand(avaliable)
				.run();
		} else {
			for (Option opt : active) {
				CommandFactory
					.create(opt, cfg,avaliable)
					.run();
			}
		}
	}
}
