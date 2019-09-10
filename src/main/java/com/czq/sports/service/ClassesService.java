package com.czq.sports.service;

import com.czq.sports.pojo.Classes;

public interface ClassesService {
    Classes getClassesByName(String name);

    int insertClasses(Classes classes);

    int updateClasses(Classes classes);
}
