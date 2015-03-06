/**
 * DISCLAIMER
 * 
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 * 
 * @author jouni@vaadin.com
 * 
 */

package com.cloudyle.paasplus.petclinic.ui.data;

import java.util.Date;
import java.util.List;

import com.cloudyle.paasplus.petclinic.persistence.entities.nosql.Pet;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;


public class PetsContainer extends IndexedContainer
{


  private static final long serialVersionUID = 1L;



  public PetsContainer()
  {
    addContainerProperty("Name", String.class, "");
    addContainerProperty("TypeCode", String.class, "");
    addContainerProperty("Date of Birth", Date.class, new Date());

    addContainerProperty("Owner Name", String.class, "");

    addContainerProperty("Owner Address", String.class, "");

    addContainerProperty("Illness", String.class, "");
    addContainerProperty("pet", Pet.class, "");

  }



  private void addPet(final Pet pet)
  {
    final Object id = addItem();
    final Item item = getItem(id);
    if (item != null)
    {
      item.getItemProperty("Name").setValue(pet.getName());
      item.getItemProperty("Date of Birth").setValue(pet.getBirthDate());
      item.getItemProperty("Owner Name").setValue(pet.getOwner().getLastName() + " " + pet.getOwner().getFirstName());
      item.getItemProperty("Owner Address").setValue(pet.getOwner().getAddress());
      final String typeCode = pet.getType();
      item.getItemProperty("TypeCode").setValue(typeCode);

      item.getItemProperty("Illness").setValue(pet.getVisits().get(0).getDescription());
      item.getItemProperty("pet").setValue(pet);

    }
  }



  public void refresh(final List<Pet> patients)
  {
    removeAllItems();
    for (final Pet pat : patients)
    {
      addPet(pat);
    }
  }

}