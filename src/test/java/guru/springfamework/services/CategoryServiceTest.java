package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    CategoryMapper categoryMapper;
    public static final long ID = 1L;
    public static final String NAME = "Test";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAllCategoriesEmptyList() {
        //given
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();
        //then
        assertEquals(0,categoryDTOS.size());
    }
    @Test
    public void getAllCategories() {
        //given
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        //then
        assertEquals(2,categoryDTOS.size());
    }

    @Test
    public void getCategoryByName() {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(NAME);
        when(categoryRepository.findByName(anyString())).thenReturn(new Category());
        when(categoryMapper.categoryToCategoryDTO(any())).thenReturn(categoryDTO);
        //when
        CategoryDTO returnedCategoryDTO = categoryService.getCategoryByName("test");
        //then
        assertEquals(ID,(long) returnedCategoryDTO.getId());
        assertEquals(NAME,returnedCategoryDTO.getName());
    }
}