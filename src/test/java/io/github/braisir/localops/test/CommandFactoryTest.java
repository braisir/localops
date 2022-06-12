package io.github.braisir.localops.test;

import org.junit.Ignore;
import org.junit.Test;

import io.github.braisir.localops.component.CommandFactory;
import junit.framework.Assert;

public class CommandFactoryTest {

	@Test
	@Ignore("mientras no se se implemente esta funcionalidad")
	public void when_command_has_env_token_then_subs () {
		String cmd = "echo Actual directory $jvm{user.home}";
		String result = CommandFactory.lookupEnvVars(cmd);
		Assert.assertTrue(false);
	}
}
