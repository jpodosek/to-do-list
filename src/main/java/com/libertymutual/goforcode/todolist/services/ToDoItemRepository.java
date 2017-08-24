// ToDoItemRepository.java
package com.libertymutual.goforcode.todolist.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

	private int nextId = 1;

	/**
	 * 
	 * Get all the items from the file.
	 * 
	 * @return A list of the items. If no items exist, returns an empty list.
	 */

	public List<ToDoItem> getAll() {
		List<ToDoItem> toDoList = new ArrayList<ToDoItem>();
		// store to-do-list in a reader
		try (Reader in = new FileReader("to-do-list.csv"); CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT)) // pass
																												// CSV
																													// parser
		{

			// get all the items from the csv file and store into CSVRecord list
			List<CSVRecord> list = parser.getRecords();

			// iterate through list, and for each record, store column value in variable
			for (CSVRecord record : list) {
				String idColumn = record.get(0);
				String textColumn = record.get(1);
				String isComplete = record.get(2);

				// add each of these variable values to to-do-list
				ToDoItem item = new ToDoItem();
				item.setId(Integer.parseInt(idColumn));
				item.setText(textColumn);
				item.setComplete(Boolean.parseBoolean(isComplete));
				System.out.println(item);
				// Add item to toDoList
				toDoList.add(item);
				nextId += 1;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toDoList;
		// return Collections.emptyList();
	}

	/**
     * Assigns a new id to the ToDoItem and saves it to the file.
     * @param item The to-do item to save to the file.
     */
    public void create(ToDoItem item) {
	
	//Create the csv file 
    try (FileWriter writer = new
	  FileWriter("to-do-list.csv", true); CSVPrinter printer =
	  CSVFormat.DEFAULT.print(writer)) {
	  
	  //Assign new id to ToDoItem 
      item.setId(nextId); 
      nextId += 1;
	  item.setComplete(false); String[] record = {Integer.toString(item.getId()),
	  item.getText(), Boolean.toString(item.isComplete())};
	  printer.printRecord(record);
	  
	  } catch (IOException e) { System.out.println("error thing on create"); }
	  
	  System.out.println("did the create work?");  
	  }
	 
	/**
	 * Gets a specific ToDoItem by its id.
	 * 
	 * @param id
	 *            The id of the ToDoItem.
	 * @return The ToDoItem with the specified id or null if none is found.
	 */
	public ToDoItem getById(int id) {

		List<ToDoItem> list = getAll();

		for (ToDoItem item : list) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	/**
	 * Updates the given to-do item in the file.
	 * 
	 * @param item
	 *            The item to update.
	 */
	public void update(ToDoItem item) {
		// grab the current ToDoList
		List<ToDoItem> list = getAll();
		for (ToDoItem i : list) {
			if (item.getId() == i.getId()) {
				i.setText(item.getText());
				i.setComplete(item.isComplete());
		
			}
		}
		
		// store to-do-list in a reader
		try (FileWriter writer = new FileWriter("to-do-list.csv");
				CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);) {

			for (ToDoItem i : list) {
				// Create string to store values from the item
				// if i.get() = item.getby
				String[] record = { Integer.toString(i.getId()), i.getText(), Boolean.toString(i.isComplete()) };
				printer.printRecord(record);
			}
		} catch (IOException e) {
			System.out.println("Input output exception.");

		}
	}
}
