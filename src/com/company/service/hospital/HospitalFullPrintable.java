package com.company.service.hospital;
import com.company.model.IBuilding;
import com.company.model.Hospital;

public class HospitalFullPrintable implements HospitalPrintable {
    @Override
    public void printInfo(IBuilding building) {
        if (building instanceof Hospital) {
            Hospital hospital = (Hospital) building;
            System.out.println("HOSPITAL");
            System.out.println("Name: " + (hospital.getName() != null ? hospital.getName() : "Not specified"));
            System.out.println("Address: " + hospital.getAddress());
            System.out.println("Capacity: " + hospital.getCapacity());
            System.out.println("Number of patients: " + hospital.getNumberOfPatients());
            if (hospital.getDescription() != null && hospital.getDescription().length() > 0) {
                System.out.println("Description: " + hospital.getDescription());
            }
        }
    }
}
