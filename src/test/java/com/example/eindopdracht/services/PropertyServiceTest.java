package com.example.eindopdracht.services;

import com.example.eindopdracht.dto.PropertyDto;
import com.example.eindopdracht.dto.ViewingDto;
import com.example.eindopdracht.exceptions.IdNotFoundException;
import com.example.eindopdracht.models.Property;
import com.example.eindopdracht.models.Viewing;
import com.example.eindopdracht.repositories.PropertyRepository;
import com.example.eindopdracht.repositories.ViewingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    @Test
    void getProperty() {
        // Arrange - creating/adding a new property
        Long propertyId = 1L;
        Property property = new Property();
        property.setStreetname("Winkelstraat");
        property.setHousenumber("123");
        property.setPrice(100000.0);
        property.setDescription("This is a test description for test getProperties");

        Mockito.when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        // Act
        PropertyDto propertyDto = propertyService.getProperty(propertyId);

        // Assert
        assertEquals("Winkelstraat", propertyDto.getStreetname());
        assertEquals("123", propertyDto.getHousenumber());
        assertEquals(100000.0, propertyDto.getPrice());
        assertEquals("This is a test description for test getProperties", propertyDto.getDescription());
    }

    @Test
    void testGetPropertyNotFound() {
        // Arrange
        Long nonExistingPropertyId = 2L;
        Mockito.when(propertyRepository.findById(nonExistingPropertyId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IdNotFoundException.class, () -> propertyService.getProperty(nonExistingPropertyId));
    }

    @Test
    void getAllProperties() {
        // arrange
        Property property1 = new Property();
        property1.setStreetname("Teststraat");
        property1.setHousenumber("1");
        property1.setPrice(200000.0);
        property1.setDescription("This is a test description for test getAllProperties");

        Property property2 = new Property();
        property2.setStreetname("Hoofdstraat");
        property2.setHousenumber("5");
        property2.setPrice(500000.0);
        property2.setDescription("This is another test description for test getAllProperties");

//        List<Viewing> viewings = Arrays.asList(viewing1, viewing2);
        List<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);

        Mockito.when(propertyRepository.findAll()).thenReturn(properties);

        // act
        List<PropertyDto> result = propertyService.getAllProperties();

        // assert
        assertEquals(2, result.size());
    }

    @Test
    void createProperty() {
        // arrange
        Property newProperty = new Property();
        newProperty.setStreetname("Teststraat");
        newProperty.setHousenumber("1");
        newProperty.setPrice(200000.0);
        newProperty.setDescription("This is a test description for test createProperty");

        Mockito.when(propertyRepository.save(Mockito.any(Property.class))).thenReturn(newProperty);

        // act
        PropertyDto savedPropertyDto = propertyService.createProperty(new PropertyDto());

        // assert
        assertEquals("Teststraat", savedPropertyDto.getStreetname());
        assertEquals("1", savedPropertyDto.getHousenumber());
        assertEquals(200000.0, savedPropertyDto.getPrice());
        assertEquals("This is a test description for test createProperty", savedPropertyDto.getDescription());
    }

    @Test
    void deleteProperty() {
        // Arrange
        Long propertyId = 1L;

        Mockito.doNothing().when(propertyRepository).deleteById(propertyId);

        // Act
        String result = propertyService.deleteProperty(propertyId);

        // Assert
        // Verify that the deleteById method of viewingRepository is called with the correct ID
        Mockito.verify(propertyRepository).deleteById(propertyId);

        // Verify the returned message
        assertEquals("Property successfully deleted", result);
    }
}