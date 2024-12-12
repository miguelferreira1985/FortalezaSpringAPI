package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.EPresentation;
import com.fotaleza.fortalezaapi.model.Presentation;

public interface IPresentationService {

    Presentation getPresentationByName (EPresentation presentationName);

}
