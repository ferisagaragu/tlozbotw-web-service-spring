package org.neurobrain.tlozbotw.util;

import org.junit.Test;
import org.neurobrain.tlozbotw.entity.User;
import org.springframework.context.annotation.Description;


public class TextTest {

	@Test
	@Description("normal function test")
	public void toJSONString() {
		User user = new User();
		/*String assertStg = Text.toJSONString(user);
		assertEquals('{', assertStg.charAt(0));
		assertEquals('}', assertStg.charAt(assertStg.length() - 1));*/
	}

	@Test
	@Description("function test with null param")
	public void toJSONString_bad() {
		/*String assertStg = Text.toJSONString(null);
		assertEquals("", assertStg);*/
	}

}
