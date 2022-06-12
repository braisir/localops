package io.github.braisir.localops.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import io.github.braisir.localops.component.LocalOpsParser;
import io.github.braisir.localops.model.Operation;
import io.github.braisir.localops.model.OperationsConfig;

public class LocalOpsParserTest {

	@Test
	public void when_invoke_exists_then_check_if_config_file_exists() throws IOException {		
		Assert.assertTrue(LocalOpsParser.exist());
	}
	
	@Test
	public void when_parse_file_then_not_null_object_returned() {
		OperationsConfig result = LocalOpsParser.parse();
		Assert.assertNotNull(result);
	}
	
	@Test
	public void when_parse_sample_file_then_three_operations_found() {
		OperationsConfig result = LocalOpsParser.parse();
		Assert.assertEquals(4, result.getOperations().size());
	}
	
	@Test
	public void when_parse_sample_file_then_command_and_description_not_null() {
		OperationsConfig result = LocalOpsParser.parse();
		for (Operation op : result.getOperations().values()) {
			Assert.assertNotNull(op.getDesc());
			Assert.assertNotNull(op.getCmd());
		}
	}
	
	@Test
	public void when_parse_sample_file_then_command_if_fully_informed() {
		OperationsConfig result = LocalOpsParser.parse();
		for (Operation op : result.getOperations().values()) {
			Assert.assertNotNull(op.getCmd().getNix());
			Assert.assertNotNull(op.getCmd().getMac());
			Assert.assertNotNull(op.getCmd().getWin());
		}
	}
}
