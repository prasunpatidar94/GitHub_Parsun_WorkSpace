package com.iteanz.srl.service;

import java.util.Comparator;
import java.util.HashMap;

public class HashMapComparator2  implements Comparator {
	boolean flag = false ;
	
	//@Override
	 public int compare ( Object object1 , Object object2 )
    {
        if ( flag == false )
        {
        	Double obj1Value = ( Double ) ( ( HashMap ) object1 ).get ( "preis" ) ;
        	Double obj2Value = ( Double ) ( ( HashMap ) object2 ).get ( "preis" ) ;
            
            return obj1Value.compareTo ( obj2Value ) ;
        }
        else
        {
        	Double obj1Value = ( Double ) ( ( HashMap ) object1 ).get ( "preis" ) ;
        	Double obj2Value = ( Double ) ( ( HashMap ) object2 ).get ( "preis" ) ;
            
            return obj2Value.compareTo ( obj1Value ) ;
        }
    }

}


