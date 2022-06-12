package io.github.braisir.localops.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.github.braisir.localops.model.OperationsConfig;

public class LocalOpsParser {

	public static final String LOCALOPS_FILE = "localops.yml";
	
	private LocalOpsParser() {
		super();
	}
	
	public static final boolean exist() {
		File f = new File(LOCALOPS_FILE);
		return f.exists();
	}
	
	public static final OperationsConfig parse() {
		return 
		Stream
			.generate(LocalOpsParser::parseYaml)
			.limit(1)
			.map(LocalOpsParser::expandAllOsAttr)
			.map(LocalOpsParser::subsIdemToken)
			.findFirst()
			.get();
	}
	
	private static final OperationsConfig parseYaml() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		mapper.findAndRegisterModules();
		if (exist()) {
			try {
				return mapper.readValue(new File(LOCALOPS_FILE), OperationsConfig.class);
			} catch (StreamReadException e) {
				throw new IllegalStateException("File corrupt: localops.yml", e);
			} catch (DatabindException e) {
				throw new IllegalStateException("File format incorrect", e);
			} catch (IOException e) {
				throw new IllegalStateException("File not found: localops.yml in current directory.");			}
		} else {
			throw new IllegalStateException("File not found: localops.yml in current directory.");
		}		
	}
	
	private static final OperationsConfig expandAllOsAttr(OperationsConfig cfg) {
		cfg.getOperations()
		   .values()
		   .stream()
		   .forEach(o -> {
			   String allos = o.getCmd().getAllos();
			   if (allos != null) {
				   o.getCmd().setMac(allos);
				   o.getCmd().setNix(allos);
				   o.getCmd().setWin(allos);
			   }
		   });
		return cfg;
	}
	
	private static final String IDEM = "idem";
	private static final OperationsConfig subsIdemToken(OperationsConfig cfg) {
		cfg.getOperations()
		   .values()
		   .stream()
		   .forEach(o -> {
			   List<Consumer<String>> attrToUpdate = new ArrayList<>();
			   String candidate = null;
			   if (IDEM.equals(o.getCmd().getMac())) {
				   attrToUpdate.add(o.getCmd()::setMac);
			   } else {
				   candidate = o.getCmd().getMac();
			   }
			   if (IDEM.equals(o.getCmd().getNix())) {
				   attrToUpdate.add(o.getCmd()::setNix);
			   } else {
				   candidate = o.getCmd().getNix();
			   }
			   if (IDEM.equals(o.getCmd().getWin())){
				   attrToUpdate.add(o.getCmd()::setWin);
			   } else {
				   candidate = o.getCmd().getWin();
			   }
			   for (Consumer<String> c : attrToUpdate) {
				   c.accept(candidate);
			   }
		   });
		
		return cfg;
	}
}
