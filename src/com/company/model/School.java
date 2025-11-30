package com.company.model;

import com.company.enums.AccreditationLevel;
import com.company.service.school.SchoolPrintable;
import java.util.Random;

// School
public class School extends Building {

    private SchoolPrintable schoolPrintable;
    private int numberOfStudents;
    private AccreditationLevel accreditationLevel;

    public School() {
        super();
    }

    public School(int address) {
        super(address);
    }

    public School(int address, String name, int capacity, String description) {
        super(address, name, capacity, description);
    }

    public School(SchoolPrintable schoolPrintable) {
        super();
        this.schoolPrintable = schoolPrintable;
    }

    public School(int address, AccreditationLevel accreditationLevel, SchoolPrintable schoolPrintable) {
        super(address);
        this.accreditationLevel = accreditationLevel;
        this.schoolPrintable = schoolPrintable;
        initializeStudentsFromAccreditation();
    }

    private void initializeStudentsFromAccreditation() {
        if (accreditationLevel != null) {
            Random rand = new Random();
            int min = accreditationLevel.getMinStudents();
            int max = accreditationLevel.getMaxStudents();
            this.numberOfStudents = rand.nextInt((max - min) + 1) + min;
            // Set capacity to maxStudents as per requirement
            setCapacity(max);
        }
    }

    public void setSchoolPrintable(SchoolPrintable schoolPrintable) {
        this.schoolPrintable = schoolPrintable;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public AccreditationLevel getAccreditationLevel() {
        return accreditationLevel;
    }

    public void setAccreditationLevel(AccreditationLevel accreditationLevel) {
        this.accreditationLevel = accreditationLevel;
        initializeStudentsFromAccreditation();
    }

    @Override
    public void initializeFromString(String data) {
        try {
            // Format: "address,accreditationLevel"
            // accreditationLevel can be: enum name (ELEMENTARY), display name (Elementary-school), or index (0-6)
            String[] parts = data.split(",");
            if (parts.length >= 1) {
                setAddress(Integer.parseInt(parts[0].trim()));
            }
            if (parts.length >= 2) {
                String accreditationInput = parts[1].trim();
                // Always treat second value as accreditation level
                setAccreditationLevelFromString(accreditationInput);
            }
        } catch (Exception e) {
            System.err.println("Error initializing school from string: " + e.getMessage());
        }
    }

    private void setAccreditationLevelFromString(String input) {
        // First, try to parse as index (0-based)
        try {
            int index = Integer.parseInt(input);
            AccreditationLevel[] levels = AccreditationLevel.values();
            if (index >= 0 && index < levels.length) {
                this.accreditationLevel = levels[index];
                initializeStudentsFromAccreditation();
                return;
            }
        } catch (NumberFormatException e) {
            // Not a number, continue to try as name
        }
        
        // Try to find by enum name or display name
        for (AccreditationLevel level : AccreditationLevel.values()) {
            if (level.getName().equalsIgnoreCase(input) || level.name().equalsIgnoreCase(input)) {
                this.accreditationLevel = level;
                initializeStudentsFromAccreditation();
                return;
            }
        }
        
        // If not found, accreditation level remains null
        // Students will be 0 (default)
    }

    @Override
    public void printInfo() {
        if (schoolPrintable != null) {
            schoolPrintable.printInfo(this);
        } else {
            String levelInfo = accreditationLevel != null ? accreditationLevel.getName() : "not specified";
            String description = accreditationLevel != null && accreditationLevel.getDescription() != null ?
                " - " + accreditationLevel.getDescription() : "";
            System.out.println("School: " + getName() + ", Address: " + address + 
                             ", Students: " + numberOfStudents + 
                             ", Accreditation level: " + levelInfo + description);
        }
    }
}
