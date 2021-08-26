/*
Nathan Engle
8/25/2021
*/

package bom;


import java.io.FileReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PartManagerImpl implements PartManager {	
	//private member to use with other member functions
	//such as retrievePart, getFinalAssemblies, and getPurchasePartsByPrice
	private Map<String, Part> partMap;
		
	@Override
	public int importPartStore(String filePath) {
		
		try (FileReader jsonReader = new FileReader(filePath)) {
			
			Gson gson = new Gson();
			
			//JSON file read into list because it was easier than loading it into the Map directly
			//it is also easier to convert a list to a map than an array to a map
			Type listType = new TypeToken<List<Part>>(){}.getType();
			List<Part> partsList = gson.fromJson(jsonReader, listType);
			
			Map<String, Part> parts = partsList.stream().collect(Collectors.toMap(Part::getPartNumber, Function.identity()));
			this.partMap = parts;
			
			return parts.size();
		}
		catch(Exception e) {
			System.out.println(e.getCause());
		}
		
		return -1; //if problem is encountered during loading of list
	}

	@Override
	public Part costPart(String partNumber) {
		float extPrice = 0;
		Part wPart = retrievePart(partNumber);
		if(wPart != null) { //if part is found, continue, else return null
			
			if(wPart.getBillOfMaterial() == null) { //if BoM is null, part must have a price. return unchanged part
				return wPart;
			}
			
			else {
				for(BomEntry i : wPart.getBillOfMaterial()) {
					extPrice += costPart(i.getPartNumber()).getPrice() * i.getQuantity();
				}
				extPrice = roundForMoney(extPrice);
				wPart.setPrice(extPrice);
				return wPart;
			}
		}
		return null;
	}

	@Override
	public Part retrievePart(String partNumber) {
		for(Map.Entry<String, Part> things : this.partMap.entrySet()) {
			if(things.getValue().getPartNumber().equals(partNumber)) {
				return things.getValue();
			}
		}
		return null;
	}

	@Override
	public List<Part> getFinalAssemblies() {		
		List<Part> finals = new ArrayList<>();
		
		for(Map.Entry<String, Part> things : this.partMap.entrySet()) {
			if(things.getValue().getPartType().equals("ASSEMBLY")) {
				finals.add(things.getValue());
			}
		}
		
		//sort in ascending order based on part number
		Collections.sort(finals, (a, b) -> a.getPartNumber().compareTo(b.getPartNumber()));
		
		return finals;
	}

	@Override
	public List<Part> getPurchasePartsByPrice() {
		List<Part> purchases = new ArrayList<>();
		
		for(Map.Entry<String, Part> things : this.partMap.entrySet()) {
			if(things.getValue().getPartType().equals("PURCHASE")) {
				purchases.add(things.getValue());
			}
		}
		
		//create comparator in order to easily use the Collections reversed() member function
		Comparator<Part> sortByPrice = (a, b) -> new Float(a.getPrice()).compareTo(new Float(b.getPrice()));
		
		Collections.sort(purchases, sortByPrice.reversed());
		
		return purchases;
	}

	/**
	* Returns the supplied value rounded for money.
	* @param value the value to round.
	* @return the rounded value.
	*/
	private float roundForMoney(float value) {
	 return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).floatValue();
	}
}
