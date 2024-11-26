package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Category;
import com.fotaleza.fortalezaapi.model.ECategory;

public interface ICategoryService {

    Category getCategoryByName(ECategory categoryName);

}
