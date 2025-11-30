package com.company.service.hospital;
import com.company.model.IBuilding;
import com.company.model.Hospital;

public class HospitalBriefPrintable implements HospitalPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof Hospital) {
            Hospital hospital = (Hospital) building;
            System.out.println("Hospital: " + (hospital.getName() != null ? hospital.getName() : "Not specified") + 
                             ", Address: " + hospital.getAddress() + 
                             ", Patients: " + hospital.getNumberOfPatients());
        }
    }
}
