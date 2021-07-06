package com.terminplaner;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.terminplaner.entity.Department;
import com.terminplaner.repository.DepartmentRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DepartmentRepositoryTests {

	@Autowired
	private DepartmentRepository repo;
	
	@Test
	public void testCreateDepartments() {
		Department guitars = new Department("Guitars", 25);
		Department drums = new Department("Drums & Percussions", 20);
		Department studio = new Department("Studio Equipment", 10);
		Department brass = new Department("Brass & Wind Instruments", 15);
		Department strings = new Department("Classical String Instruments", 8);
		
		Department savedGuitars = repo.save(guitars);
		Department savedDrums = repo.save(drums);
		Department savedStudio = repo.save(studio);
		Department savedBrass = repo.save(brass);
		Department savedStrings = repo.save(strings);
		
		assertThat(savedGuitars.getId()).isGreaterThan(0);
		assertThat(savedGuitars.getName()).isEqualTo(guitars.getName());
		
		assertThat(savedStudio.getId()).isGreaterThan(0);
		assertThat(savedStudio.getName()).isEqualTo(studio.getName());
		
		assertThat(savedBrass.getId()).isGreaterThan(0);
		assertThat(savedBrass.getName()).isEqualTo(brass.getName());
		
		assertThat(savedDrums.getId()).isGreaterThan(0);
		assertThat(savedDrums.getName()).isEqualTo(drums.getName());
		
		assertThat(savedStrings.getId()).isGreaterThan(0);
		assertThat(savedStrings.getName()).isEqualTo(strings.getName());
	}
}
