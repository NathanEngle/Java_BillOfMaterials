/*
Nathan Engle
8/25/2021
*/

package bom;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPartManagerImpl {
	
	private final PartManagerImpl test = new PartManagerImpl();
	@Before
	public void setup() {
		test.importPartStore("resources/bom.json");
	}
	@Test
	public void importPartStore() {
		Assert.assertEquals(79, test.importPartStore("resources/bom.json"));
	}
	
	@Test
	public void retrievePart() {
		
		Part thing = test.retrievePart("290B7266J1");
		
		Assert.assertTrue(thing.getName().equals("Rotor Shaft Assembly Final"));
	}
	
	@Test
	public void getFinalAssemblies() {
		
		List<Part> finalAssemblies = test.getFinalAssemblies();
		
		Assert.assertTrue(finalAssemblies.size() == 4);
		//an attempt at verifying order of list ascends based on part number
		Assert.assertTrue((finalAssemblies.get(0).getPartNumber().equals("20-0001")) && (finalAssemblies.get(3).getPartNumber().equals("290B7266J6")));
	}
	
	@Test
	public void getPurchasePartsByPrice() {

		List<Part> purchases = test.getPurchasePartsByPrice();
		
		Assert.assertTrue(purchases.size() == 52);
		//attempt at verifying order of list descends based on price
		Assert.assertTrue((purchases.get(0).getPrice() == 19.75f) && (purchases.get(51).getPrice() == 0.54f));
	}
	
	@Test
	public void costPartAssemblies() {

		Part thing1 = test.costPart("290B7266J1");
		Part thing2 = test.costPart("290B7266J2");
		Part thing3 = test.costPart("290B7266J6");
		Part thing4 = test.costPart("20-0001");
		
		Assert.assertTrue(thing1.getPrice() == 415.16f);
		Assert.assertTrue(thing2.getPrice() == 532.20f);
		Assert.assertTrue(thing3.getPrice() == 334.10f);
		Assert.assertTrue(thing4.getPrice() == 96.39f);
	}
	
	@Test
	public void costPartComponents() {

		Part thing1 = test.costPart("20-0015");
		Part thing2 = test.costPart("290B2350P2");
		Part thing3 = test.costPart("290B3810T1");
		Part thing4 = test.costPart("290B7266J5");
		
		Assert.assertTrue(thing1.getPrice() == 70.46f);
		Assert.assertTrue(thing2.getPrice() == 54.75f);
		Assert.assertTrue(thing3.getPrice() == 10.95f);
		Assert.assertTrue(thing4.getPrice() == 532.20f);
	}
}
