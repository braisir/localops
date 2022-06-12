package io.github.braisir.localops.component;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.text.StrSubstitutor;

import io.github.braisir.localops.model.Defaults;
import io.github.braisir.localops.model.Operation;
import io.github.braisir.localops.model.OperationsConfig;

public class CommandFactory {

	private CommandFactory() {
		super();
	}
	
	public static final Runnable create(Option opt, OperationsConfig cfg, Options avaliable) {
		Optional<Operation> oo = findOperation(opt, cfg);
		if (oo.isPresent()) {
			return 
			fromOperation(
				opt.getOpt(), 
				oo.get(),
				cfg.getDefaults());
		} else if (OptionsFactory.ENV_OPT.equals(opt.getOpt())) {
			return envCommand();
		} else if (OptionsFactory.HELP_OPT.equals(opt.getOpt())) {
			return helpCommand(avaliable);
		} else if (OptionsFactory.VERSION_OPT.equals(opt.getOpt())){
			return versionCommand();
		}
		return null;
	}
	
	private static final Optional<Operation> findOperation(Option opt, OperationsConfig cfg){
		
		if (cfg.getOperations().containsKey(opt.getOpt())) {
			return 
			Optional
				.of(cfg.getOperations().get(opt.getOpt()));
		} else {
			return
			cfg.getOperations()
			   .values()
			   .stream()		
			   .filter(Objects::nonNull)
			   .filter(ope -> ope.getAlias() != null)
			   .filter(ope -> ope.getAlias().contains(opt.getOpt()))
			   .findFirst();
		}
		
	}
		
	private static final Runnable fromOperation(
			String name,
			Operation op,
			Defaults defaults) {
		return () -> {
			Supplier<String> cmd = null;
			String shell = null;
			String basePath = defaults.getPath();
			if (SystemUtils.IS_OS_WINDOWS) {
				cmd = op.getCmd()::getWin;
				shell = defaults.getShell().getWin();
			} else if (SystemUtils.IS_OS_LINUX) {
				cmd = op.getCmd()::getNix;
				shell = defaults.getShell().getNix();
			} else if (SystemUtils.IS_OS_MAC) {
				cmd = op.getCmd()::getMac;
				shell = defaults.getShell().getMac();
			}
			
			String[] cs = shell.split(" ");
			String[] csa = new String[cs.length + 1];
			for (int i = 0; i < cs.length + 1; i++) {
				if (i < cs.length) {
					csa[i] = cs[i];					
				} else {
					csa[i] = cmd.get();
				}
			}
			try {
				Process process = 
				new ProcessBuilder()
					.command(csa)
					.directory(new File(basePath))
					.inheritIO()					
					.start();
				
				int exitCode = process.waitFor();
			} catch (InterruptedException | IOException e) {
				throw new IllegalStateException("Ha cascado el comando: " + cmd.get(), e);
			}			
		};
	}
	
	
	public static final String lookupEnvVars(String cmd) {
		//StrSubstitutor ss = new StrSubstitutor(valueMap, "$env{","}");
		return cmd;
	}
	
	private static final String lookupJvmVars(String cmd) {
		return cmd;
	}
	
	public static final Runnable envCommand() {
		return () -> {
			new ProcessBuilder()
				.environment()
				.entrySet()
				.stream()
				.forEach(e -> Logger.info(e.getKey() + ":" + e.getValue()));
		};
	}
	
	public static final Runnable helpCommand(Options avaliable) {
		return () -> {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("lo", avaliable, true);
		};
	}
	
	public static final Runnable versionCommand() {
		return () -> {
			Logger.info("0.1.0-SNAPSHOT");
		};
	}
}
