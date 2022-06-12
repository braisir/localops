package io.github.braisir.localops.test;

import org.junit.Test;

import io.github.braisir.localops.component.CommandFactory;
import junit.framework.Assert;

public class CommandFactoryTest {

	@Test
	public void when_command_has_env_token_then_subs () {
		String cmd = "echo Actual directory $jvm{user.home}";
		String result = CommandFactory.lookupEnvVars(cmd);
		Assert.assertTrue(false);
	}
}
