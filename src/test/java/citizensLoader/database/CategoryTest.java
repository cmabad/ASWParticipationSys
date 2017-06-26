package citizensLoader.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import es.uniovi.asw.model.Category;

public class CategoryTest {

	private Category c;

	@Before
	public void setUp() throws Exception {
		c = new Category();
	}

	@Test
	public void test() {
		assertNotNull(c);
		
		c.setId(new Integer(1));
		assertEquals(1, c.getId());
		
		c.setName("categoryName");
		assertEquals("categoryName", c.getName());
		
		assertEquals("Category [id=1, name=categoryName]", c.toString());
		
		c = new Category("categoryName1");
		c.setId(1);
		assertEquals("Category [id=1, name=categoryName1]", c.toString());
		

		c = new Category(new Integer(1), "categoryName2");
		assertEquals("Category [id=1, name=categoryName2]", c.toString());
	}

}
