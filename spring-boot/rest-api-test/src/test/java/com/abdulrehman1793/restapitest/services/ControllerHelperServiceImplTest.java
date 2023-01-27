package com.abdulrehman1793.restapitest.services;

import com.abdulrehman1793.restapitest.exception.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ControllerHelperServiceImplTest {

    ControllerHelperServiceImpl controllerHelperService;

    String[] fields = new String[]{"id", "firstName", "lastName", "lastModifiedDate"};

    @BeforeEach
    void setUp() {
        controllerHelperService = new ControllerHelperServiceImpl();
    }

    @Test
    void sortRequestParameterToSortValidReturnUnsorted() {
        Sort sort = controllerHelperService.sortRequestParameterToSort(fields,
                new String[]{});

        assertNotNull(sort);
        assertTrue(sort.isUnsorted());
    }

    @Test
    void sortRequestParameterToSortValid() {
        Sort sort = controllerHelperService.sortRequestParameterToSort(fields,
                new String[]{"firstName:ASC"});

        assertNotNull(sort);
        assertTrue(sort.isSorted());
        assertEquals(Objects.requireNonNull(sort.getOrderFor("firstName")).getDirection(), Sort.Direction.ASC);
    }

    @Test
    void sortRequestParameterToSortValidMultiple() {
        Sort sort = controllerHelperService.sortRequestParameterToSort(fields,
                new String[]{"firstName:ASC", "id:Desc", "lastName:Descending"});

        assertNotNull(sort);
        assertTrue(sort.isSorted());
        assertEquals(Objects.requireNonNull(sort.getOrderFor("firstName")).getDirection(), Sort.Direction.ASC);
        assertEquals(Objects.requireNonNull(sort.getOrderFor("id")).getDirection(), Sort.Direction.DESC);
        assertEquals(Objects.requireNonNull(sort.getOrderFor("lastName")).getDirection(), Sort.Direction.DESC);
    }

    @Test
    void sortRequestParameterToSortFailedParameter() {
        assertThrows(BadRequestException.class, () -> {
            controllerHelperService.sortRequestParameterToSort(fields,
                    new String[]{"firstame:ASC"});
        });
    }

    @Test
    void sortRequestParameterToSortFailedInvalidSortField() {
        assertThrows(BadRequestException.class, () -> {
            controllerHelperService.sortRequestParameterToSort(fields,
                    new String[]{"email:ASC"});
        });
    }

    @Test
    void sortRequestParameterToSortFailedParameterWithoutDirection() {
        assertThrows(BadRequestException.class, () -> {
            controllerHelperService.sortRequestParameterToSort(fields,
                    new String[]{"firstame"});
        });
    }

    @Test
    void sortRequestParameterToSortFailedParameterWithInvalidDirection() {
        assertThrows(BadRequestException.class, () -> {
            controllerHelperService.sortRequestParameterToSort(fields,
                    new String[]{"firstName:Acs"});
        });
    }
}