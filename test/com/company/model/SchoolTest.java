package com.company.model;

import com.company.enums.AccreditationLevel;
import com.company.service.school.SchoolFullPrintable;
import com.company.Assert;

// Test class for School

public class SchoolTest {

    public void testSchoolCreation() {
        School school = new School();
        Assert.assertNotNull(school);
    }

    public void testSchoolWithAddress() {
        School school = new School(50);
        Assert.assertEquals(50, school.getAddress());
    }

    public void testSchoolWithAccreditationLevel() {
        School school = new School(new SchoolFullPrintable());
        school.setAccreditationLevel(AccreditationLevel.PRE_SCHOOL);
        
        Assert.assertEquals(AccreditationLevel.PRE_SCHOOL, school.getAccreditationLevel());
    }

    public void testSchoolStudentsFromAccreditation() {
        School school = new School(new SchoolFullPrintable());
        school.setAccreditationLevel(AccreditationLevel.ELEMENTARY);
        
        AccreditationLevel level = school.getAccreditationLevel();
        int students = school.getNumberOfStudents();
        
        Assert.assertTrue("Students should be within accreditation range",
                  students >= level.getMinStudents() &&
                  students <= level.getMaxStudents());
        // Capacity should be automatically set to maxStudents
        Assert.assertEquals("Capacity should be set to maxStudents", 
                  level.getMaxStudents(), school.getCapacity());
    }

    public void testSchoolInitializeFromString() {
        School school = new School(new SchoolFullPrintable());
        school.initializeFromString("300,ELEMENTARY");
        
        Assert.assertEquals(300, school.getAddress());
        Assert.assertEquals(AccreditationLevel.ELEMENTARY, school.getAccreditationLevel());
        // Students should be calculated automatically from accreditation level
        AccreditationLevel level = school.getAccreditationLevel();
        Assert.assertTrue("Students should be within accreditation range",
                  school.getNumberOfStudents() >= level.getMinStudents() &&
                  school.getNumberOfStudents() <= level.getMaxStudents());
        // Capacity should be automatically set to maxStudents
        Assert.assertEquals("Capacity should be set to maxStudents", 
                  level.getMaxStudents(), school.getCapacity());
    }

    public void testSchoolInitializeFromStringWithIndex() {
        // Test initialization with numeric index (0-based)
        // Index 1 should be ELEMENTARY (0=PRE_SCHOOL, 1=ELEMENTARY, 2=MIDDLE, etc.)
        School school = new School(new SchoolFullPrintable());
        school.initializeFromString("173,1");
        
        Assert.assertEquals(173, school.getAddress());
        AccreditationLevel[] levels = AccreditationLevel.values();
        Assert.assertEquals("Index 1 should be ELEMENTARY", AccreditationLevel.ELEMENTARY, school.getAccreditationLevel());
        // Students should be calculated automatically
        Assert.assertTrue("Students should be within accreditation range",
                  school.getNumberOfStudents() >= AccreditationLevel.ELEMENTARY.getMinStudents() &&
                  school.getNumberOfStudents() <= AccreditationLevel.ELEMENTARY.getMaxStudents());
        // Capacity should be automatically set to maxStudents
        Assert.assertEquals("Capacity should be set to maxStudents", 
                  AccreditationLevel.ELEMENTARY.getMaxStudents(), school.getCapacity());
    }

    public void testSchoolInitializeFromStringWithDisplayName() {
        // Test initialization with display name
        School school = new School(new SchoolFullPrintable());
        school.initializeFromString("200,Preschool");
        
        Assert.assertEquals(200, school.getAddress());
        Assert.assertEquals(AccreditationLevel.PRE_SCHOOL, school.getAccreditationLevel());
        // Students should be calculated automatically
        Assert.assertTrue("Students should be within accreditation range",
                  school.getNumberOfStudents() >= AccreditationLevel.PRE_SCHOOL.getMinStudents() &&
                  school.getNumberOfStudents() <= AccreditationLevel.PRE_SCHOOL.getMaxStudents());
        // Capacity should be automatically set to maxStudents
        Assert.assertEquals("Capacity should be set to maxStudents", 
                  AccreditationLevel.PRE_SCHOOL.getMaxStudents(), school.getCapacity());
    }

    public void testSchoolCapacity() {
        School school = new School(new SchoolFullPrintable());
        school.setAccreditationLevel(AccreditationLevel.HIGH);
        
        AccreditationLevel level = school.getAccreditationLevel();
        // Capacity should be automatically set to maxStudents when accreditation level is set
        Assert.assertEquals("Capacity should be automatically set to maxStudents", 
                    level.getMaxStudents(), school.getCapacity());
    }
}

