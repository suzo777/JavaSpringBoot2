package com.example.crud;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface DolgRepo extends CrudRepository<Dolgozo, Integer> {
}
