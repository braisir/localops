package io.github.braisir.localops;

import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import io.github.braisir.localops.component.CommandDispatcher;
import io.github.braisir.localops.component.LocalOpsParser;
import io.github.braisir.localops.component.Logger;
import io.github.braisir.localops.component.OptionsFactory;
import io.github.braisir.localops.component.OptionsParser;
import io.github.braisir.localops.model.OperationsConfig;

public class Main {

	public static void main(String[] args) {
		if (LocalOpsParser.exist()) {
			OperationsConfig config = LocalOpsParser.parse();
			Options optsAvaliable = OptionsFactory.create(config);
			Optional<CommandLine> cmd = OptionsParser.parse(args, optsAvaliable);
			if (cmd.isPresent()) {
				CommandDispatcher
					.dispatch(
						cmd.get(), 
						config, 
						optsAvaliable);				
			}
		} else {
			Logger.error("localops.yml not found !!");
		}		
	}

}
