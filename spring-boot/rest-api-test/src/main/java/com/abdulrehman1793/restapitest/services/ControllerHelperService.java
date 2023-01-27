package com.abdulrehman1793.restapitest.services;

import org.springframework.data.domain.Sort;

public interface ControllerHelperService {
    Sort sortRequestParameterToSort(String[] domainFields, String[] sortParameters);
}
