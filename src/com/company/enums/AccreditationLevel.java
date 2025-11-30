package com.company.enums;

public enum AccreditationLevel {
    PRE_SCHOOL("Preschool", 20, 50, "Early childhood education for children typically aged 3-5 years, focusing on basic social skills, play-based learning, and preparation for formal schooling."),
    ELEMENTARY("Elementary-school", 100, 300, "Primary education covering fundamental subjects like reading, writing, mathematics, and basic sciences for children typically aged 6-11 years."),
    MIDDLE("Middle-school", 300, 600, "Intermediate education bridging elementary and high school, typically for students aged 11-14 years, with more specialized subject instruction."),
    HIGH("High-school", 500, 1000, "Secondary education preparing students for higher education or vocational training, typically for students aged 14-18 years."),
    GYMNASIUM("Gymazium", 400, 800, "A type of secondary school with a strong academic focus, preparing students for university education with emphasis on humanities and sciences."),
    LYCEUM("Lyceum", 300, 700, "An educational institution focused on classical studies and liberal arts, providing advanced academic preparation for higher education."),
    GENERAL_EDUCATION("General education", 200, 500, "A comprehensive educational program providing a broad range of subjects and skills for general knowledge and personal development.");

    private final String name;
    private final int minStudents;
    private final int maxStudents;
    private final String description;

    AccreditationLevel(String name, int minStudents, int maxStudents, String description) {
        this.name = name;
        this.minStudents = minStudents;
        this.maxStudents = maxStudents;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getMinStudents() {
        return minStudents;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public String getDescription() {
        return description;
    }
}

