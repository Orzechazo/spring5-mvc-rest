package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class CategoryMapperTest {

    public static final long ID = 1L;
    public static final String NAME = "Test";
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO(){
        //given
        Category category = new Category();
        category.setName(NAME);
        category.setId(ID);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        //then
        assertEquals(ID,(long) categoryDTO.getId());
        assertEquals(NAME,categoryDTO.getName());
    }
}