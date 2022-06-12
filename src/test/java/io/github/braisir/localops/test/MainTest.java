package io.github.braisir.localops.test;

import org.junit.Ignore;
import org.junit.Test;

import io.github.braisir.localops.Main;

public class MainTest {

	@Test
	public void when_invoke_without_params_then_show_help() {
		Main.main(new String[]{});
	}
	
	@Test
	public void when_invoke_helloworld_operations_then_execute_associated_command() {
		Main.main(new String[] {"-hola_mundo"});
	}
	
	@Test
	@Ignore
	public void when_invoke_compile_operations_then_execute_associated_command() {
		Main.main(new String[] {"-compile_without_tests"});
	}
	
	@Test
	public void when_invoke_env_operations_then_execute_associated_command() {
		Main.main(new String[] {"-env"});
	}
}
