
package com.company.legacy.soap;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

public class SoapConfig {

    public SoapConfig(Bus bus) {
        try (EndpointImpl endpoint = new EndpointImpl(bus, new EmployeeService())) {
            endpoint.publish("/EmployeeService");
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }
}
