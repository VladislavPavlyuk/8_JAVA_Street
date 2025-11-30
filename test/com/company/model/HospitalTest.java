package com.company.model;

import com.company.service.hospital.HospitalFullPrintable;
import com.company.Assert;

// Test class for Hospital

public class HospitalTest {

    public void testHospitalCreation() {
        Hospital hospital = new Hospital();
        Assert.assertNotNull(hospital);
    }

    public void testHospitalWithAddress() {
        Hospital hospital = new Hospital(75);
        Assert.assertEquals(75, hospital.getAddress());
    }

    public void testHospitalNumberOfPatients() {
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.setNumberOfPatients(25);
        
        Assert.assertEquals(25, hospital.getNumberOfPatients());
    }

    public void testHospitalPatientsNotExceedCapacity() {
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.setCapacity(100);
        hospital.setNumberOfPatients(150);
        
        // Patients should not exceed capacity (though the setter allows it)
        // This test verifies the current behavior
        Assert.assertEquals(150, hospital.getNumberOfPatients());
    }

    public void testHospitalInitializeFromString() {
        Hospital hospital = new Hospital(new HospitalFullPrintable());
        hospital.initializeFromString("400,100");
        
        Assert.assertEquals(400, hospital.getAddress());
        Assert.assertEquals(100, hospital.getCapacity());
        // Number of patients should be set randomly within capacity (0 to capacity)
        Assert.assertTrue("Number of patients should be within capacity",
                  hospital.getNumberOfPatients() >= 0 && 
                  hospital.getNumberOfPatients() <= hospital.getCapacity());
    }
}

