/*
Nathan Engle
8/25/2021
*/

package bom;

import java.util.List;

public class Part implements Comparable<Part> {
	private String partNumber;
	private String name;
	private String partType; //"ASSEMBLY", "PURCHASE", or "COMPONENT"
	private float price;
	private List<BomEntry> billOfMaterial;
	/**
	 * @return the partNumber
	 */
	public String getPartNumber() {
		return partNumber;
	}
	/**
	 * @param partNumber the partNumber to set
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the partType
	 */
	public String getPartType() {
		return partType;
	}
	/**
	 * @param partType the partType to set
	 */
	public void setPartType(String partType) {
		this.partType = partType;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * @return the billOfMaterial
	 */
	public List<BomEntry> getBillOfMaterial() {
		return billOfMaterial;
	}
	/**
	 * @param billOfMaterial the billOfMaterial to set
	 */
	public void setBillOfMaterial(List<BomEntry> billOfMaterial) {
		this.billOfMaterial = billOfMaterial;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Part other = (Part) obj;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		return true;
	}
	@Override
    public int compareTo(Part o) {
        return this.getPartNumber().compareTo(o.getPartNumber());
    }
}
