//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.21 at 02:16:52 AM EDT 
//


package com.example.demo.sockets.client.shared.binding;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.demo.sockets.client.shared.binding package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.demo.sockets.client.shared.binding
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShipOrder }
     * 
     */
    public ShipOrder createShipOrder() {
        return new ShipOrder();
    }

    /**
     * Create an instance of {@link ShipOrder.ShipTo }
     * 
     */
    public ShipOrder.ShipTo createShipOrderShipTo() {
        return new ShipOrder.ShipTo();
    }

    /**
     * Create an instance of {@link ShipOrder.Item }
     * 
     */
    public ShipOrder.Item createShipOrderItem() {
        return new ShipOrder.Item();
    }

}