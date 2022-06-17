package com.exchange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	
	public static void main(String[] args) {
		
		
		List<Hardware> list = new ArrayList<Hardware>();
		list.add(new Hardware("Ram", 2));
		list.add(new Hardware("Keyboard", 3));
		list.add(new Hardware("Mouse", 5));
		list.add(new Hardware("Keyboard", 5));
		list.add(new Hardware("Mouse", 1));
		
		System.out.println("list: " +list);

		Map<String, Hardware> hardwareMap = new HashMap<>();
		 for(Hardware h : list){
		    Hardware current = hardwareMap.get(h.getName());
		    if(current == null){
		        hardwareMap.put(h.getName(), h);
		    }else{
		        current.setQuantity(current.getQuantity() + h.getQuantity());
		    }
		 }
		 Collection<Hardware> list1 = hardwareMap.values();
		 System.out.println("list1: "+list1);
	}

}
