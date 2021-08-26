/*
Nathan Engle
8/25/2021
*/

package bom;

import java.util.List;

public interface PartManager {
	//imports part list from external file
	//accepts file path
	//returns part count
	int importPartStore(String filePath);
	
	//computes the cost to manufacture the part associated with supplied part number
	Part costPart(String partNumber);
	
	//retrieves part with associated part number
	//returns part instance of associated part number
	//or null if not found
	Part retrievePart(String partNumber);
	
	//returns all final assembly parts sorted alphabetically by their part number
	List<Part> getFinalAssemblies();
	
	//returns all purchased parts sorted by their price, highest price to lowest
	List<Part> getPurchasePartsByPrice();
}
